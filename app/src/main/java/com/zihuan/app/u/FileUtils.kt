package com.zihuan.app.u

import android.os.Environment
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

    /** 将多个文件夹的内容合并到一个新文件 **/
    fun stringMerge(files: List<File>) {
        var outPath = Environment.getExternalStorageDirectory().toString() + "/amergecode/mergecode1.txt"
        var outOs = FileOutputStream(File(outPath))
        outOs.use {
            it.channel.use { fileChannel ->
                files.forEach {
                    FileInputStream(it.absoluteFile).channel.use { it.transferTo(0, it.size(), fileChannel) }
                }
            }
        }
    }


}