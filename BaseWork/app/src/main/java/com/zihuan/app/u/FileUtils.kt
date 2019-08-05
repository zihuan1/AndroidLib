package com.tripsdiy.app.u

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import org.jetbrains.annotations.NotNull
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.math.BigInteger
import java.security.MessageDigest


/***
 * 文件工具类
 */
object FileUtils {

    /**获取文件的md5**/
    fun getFileMD5(@NotNull file: File): String? {
        var digest: MessageDigest = MessageDigest.getInstance("MD5")
        var fin = FileInputStream(file)
        val buffer = ByteArray(1024)
        var len: Int = -1
        fin.use {
            while ((fin.read(buffer, 0, 1024).also { len = it }) != -1) {
                digest.update(buffer, 0, len)
            }
        }
        val bigInt = BigInteger(1, digest!!.digest())
        return bigInt.toString(16)
    }

    /** 读取文件夹下的所有的文件路径**/
    fun getFilesPath(@NotNull path: String, @NotNull files: ArrayList<File>): List<File> {
        var realFile = File(path)
        if (realFile.isDirectory) {
            var subFiles = realFile.listFiles()
            subFiles.forEach {
                if (it.isDirectory) {
                    getFilesPath(it.absolutePath, files)
                } else {
                    files.add(it)
                }
            }
        }
        return files
    }

    /**
     *  将多个文件夹的内容合并到一个新文件
     * 与 getFilesPath()方法结合使用
     *      Demo
     *      var list = ArrayList<File>()
     *      FileUtils.getFilesPath(Environment.getExternalStorageDirectory().toString() + "/src/", list)
     *      FileUtils.stringMerge(list)
     * **/
    fun stringMerge(files: List<File>) {
        var outPath = Environment.getExternalStorageDirectory().toString() + "/amergecode/mergecode1.txt"
        var outOs = FileOutputStream(File(outPath))
        outOs.use {
            it.channel.use { fileChannel ->
                files.forEach { it ->
                    FileInputStream(it.absoluteFile).channel.use { it -> it.transferTo(0, it.size(), fileChannel) }
                }
            }
        }
    }

    val FILENAME = "tripsTemp"

    /**
     * 创建文件夹
     * @param fileName 文件夹名字
     * @return 文件夹路径
     */
    fun createNewFile(fileName: String = FILENAME): String {
        val file = File(Environment.getExternalStorageDirectory().toString() + File.separator + fileName)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.toString()
    }


    /**
     * 获取图片路径
     * @return 图片路径
     */
    fun getImgPath(name: String): String {
        return createNewFile(FILENAME) + File.separator + name
    }

    /***
     * 获取图片的真实路径
     */
    fun getRealFilePath(context: Context, uri: Uri?): String? {
        if (null == uri) return null
        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null)
            data = uri.path
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.contentResolver.query(uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }

    /**
     * 得到资源文件中图片的Uri
     *
     * @param context 上下文对象
     * @param id      资源id
     * @return Uri
     */
    fun getUriFromDrawableRes(context: Context, id: Int): Uri {
        val resources = context.resources
        val path = (ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                + resources.getResourcePackageName(id) + "/"
                + resources.getResourceTypeName(id) + "/"
                + resources.getResourceEntryName(id))
        return Uri.parse(path)
    }

    //读取指定格式的文件
    fun getSpecificTypeOfFile(context: Context, extension: Array<String>): ArrayList<String> {
        var uriList = ArrayList<String>()
        //从外存中获取
        val fileUri = MediaStore.Files.getContentUri("external")
        //筛选列，这里只筛选了：文件路径和不含后缀的文件名
        val projection = arrayOf(MediaStore.Files.FileColumns.DATA, MediaStore.Files.FileColumns.TITLE)
        //构造筛选语句
        var selection = ""
        for (i in extension.indices) {
            if (i != 0) {
                selection = "$selection OR "
            }
            selection = selection + MediaStore.Files.FileColumns.DATA + " LIKE '%" + extension[i] + "'"
        }
        //按时间递增顺序对结果进行排序;待会从后往前移动游标就可实现时间递减
        val sortOrder = MediaStore.Files.FileColumns.DATE_MODIFIED
        //获取内容解析器对象
        val resolver = context.contentResolver
        //获取游标
        val cursor = resolver.query(fileUri, projection, selection, null, sortOrder)
                ?: return uriList
        //游标从最后开始往前递减，以此实现时间递减顺序（最近访问的文件，优先显示）
        if (cursor.moveToLast()) {
            do {
                //输出文件的完整路径
                val data = cursor.getString(0)
                Log.e("本地文件tag", data)
                uriList.add(data)
            } while (cursor.moveToPrevious())
        }
        cursor.close()
        return uriList
    }

    // 判断SD卡是否存在
    fun isExistSDCard(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param delFile 要删除的文件夹或文件名
     * @return 删除成功返回true，否则返回false
     */
    fun delete(delFile: String): Boolean {
        val file = File(delFile)
        return if (!file.exists()) {
            Logger.tag("删除文件失败:" + delFile + "不存在！")
            false
        } else {
            if (file.isFile)
                deleteSingleFile(delFile)
            else
                deleteDirectory(delFile)
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePathName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    fun deleteSingleFile(`filePath$Name`: String): Boolean {
        val file = File(`filePath$Name`)
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        return if (file.exists() && file.isFile) {
            if (file.delete()) {
                Logger.e("--Method--", "Copy_Delete.deleteSingleFile: 删除单个文件" + `filePath$Name` + "成功！")
                true
            } else {
                Logger.tag("删除单个文件" + `filePath$Name` + "失败！")
                false
            }
        } else {
            Logger.tag("删除单个文件失败：" + `filePath$Name` + "不存在！")
            false
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    fun deleteDirectory(filePath: String): Boolean {
        var filePath = filePath
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator))
            filePath += File.separator
        val dirFile = File(filePath)
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory) {
            Logger.tag("删除目录失败：" + filePath + "不存在！")
            return false
        }
        var flag = true
        // 删除文件夹中的所有文件包括子目录
        val files = dirFile.listFiles()
        for (file in files!!) {
            // 删除子文件
            if (file.isFile) {
                flag = deleteSingleFile(file.absolutePath)
                if (!flag)
                    break
            } else if (file.isDirectory) {
                flag = deleteDirectory(file
                        .absolutePath)
                if (!flag)
                    break
            }// 删除子目录
        }
        if (!flag) {
            Logger.tag("删除目录失败！")
            return false
        }
        // 删除当前目录
        return if (dirFile.delete()) {
            Logger.tag("Copy_Delete.deleteDirectory: 删除目录" + filePath + "成功！")
            true
        } else {
            Logger.tag("删除目录：" + filePath + "失败！")
            false
        }
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    fun getPath(context: Context, uri: Uri): String? {
        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {

                val id = DocumentsContract.getDocumentId(uri)
                val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id))

                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                if ("image" == type) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                } else if ("video" == type) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                } else if ("audio" == type) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                return getDataColumn(context, contentUri, selection, selectionArgs)
            }// MediaProvider
            // DownloadsProvider
        } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {
            return getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
            return uri.path
        }// File
        // MediaStore (and general)
        return null
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                      selectionArgs: Array<String>?): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(column_index)
            }
        } catch (ignored: Exception) {
        } finally {
            cursor?.close()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

}