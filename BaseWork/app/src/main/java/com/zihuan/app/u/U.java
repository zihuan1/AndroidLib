package com.zihuan.app.u;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tripsdiy.app.u.Logger;
import com.zihuan.app.MainApplication;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kotlin.Unit;
import kotlin.io.CloseableKt;
import kotlin.jvm.internal.Intrinsics;


public class U {
    private static Toast mToast;

    // 隐藏键盘
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public final void stringMerge(@NotNull List files) {
        String outPath = Environment.getExternalStorageDirectory().toString() + "/amergecode/mergecode1.txt";
        FileOutputStream outOs = null;
        try {
            outOs = new FileOutputStream(new File(outPath));
        } catch (FileNotFoundException e) {


        }
        Closeable var4 = (Closeable)outOs;
        boolean var5 = false;
        Throwable var6 = (Throwable)null;

        try {
            FileOutputStream it = (FileOutputStream)var4;
            Closeable var9 = (Closeable)it.getChannel();
            boolean var10 = false;
            Throwable var11 = (Throwable)null;

            try {
                FileChannel fileChannel = (FileChannel)var9;
                Iterable $this$forEach$iv = (Iterable)files;
                Iterator var16 = $this$forEach$iv.iterator();

                while(var16.hasNext()) {
                    Object element$iv = var16.next();
                    File it1 = (File)element$iv;
                    Closeable var20 = (Closeable)(new FileInputStream(it1.getAbsoluteFile())).getChannel();
                    boolean var21 = false;
                    Throwable var22 = (Throwable)null;

                    try {
                        FileChannel it2 = (FileChannel)var20;
                        long var50 = it2.transferTo(0L, it2.size(), (WritableByteChannel)fileChannel);
                    } catch (Throwable var44) {
                        var22 = var44;
                        throw var44;
                    } finally {
                    }
                }

                Unit var52 = Unit.INSTANCE;
            } catch (Throwable var46) {
                var11 = var46;
                throw var46;
            } finally {
            }

            Unit var51 = Unit.INSTANCE;
        } catch (Throwable var48) {
            var6 = var48;
        } finally {
        }

    }


    //把dp转换成px
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //把px转换成dp
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    public static void savePreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    //    存储集合
    public static boolean putHashMap(String key, HashMap<String, Integer> hashmap) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
        Editor editor = settings.edit();
        try {
            String liststr = SceneList2String(hashmap);
            editor.putString(key, liststr);
        } catch (IOException e) {

        }
        return editor.commit();
    }

    public static HashMap<String, Integer> getHashMap(String key) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainApplication.getInstance());
        String liststr = settings.getString(key, "");
        try {
            return String2SceneList(liststr);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * map转为字符串
     *
     * @param map
     * @return
     */
    public static String transMap2String(Map map) {
        Map.Entry entry;
        StringBuffer sb = new StringBuffer();
        for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); ) {
            entry = (Map.Entry) iterator.next();
            sb.append(entry.getKey().toString()).append("=").append(null == entry.getValue() ? "" :
                    entry.getValue().toString()).append(iterator.hasNext() ? "&" : "");
        }
        return sb.toString();
    }

    public static String SceneList2String(HashMap<String, Integer> hashmap)
            throws IOException {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                byteArrayOutputStream);
        // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
        objectOutputStream.writeObject(hashmap);
        // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
        String SceneListString = new String(Base64.encode(
                byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
        // 关闭objectOutputStream
        objectOutputStream.close();
        return SceneListString;
    }

    public static HashMap<String, Integer> String2SceneList(
            String SceneListString) throws StreamCorruptedException,
            IOException, ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        HashMap<String, Integer> SceneList = (HashMap<String, Integer>) objectInputStream
                .readObject();
        objectInputStream.close();
        return SceneList;
    }

    public static void savePreferences(String key, String value) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }


    public static void savePreferences(String key, int value) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();

    }

    public static void savePreferences(String key, float value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();

    }

    public static void saveBoolenPreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }

    public static void savePreferences(String key, long value) {

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();

    }

    //    清空
    public static void clearPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        Editor editor = sharedPreferences.edit();
//        editor.putLong(key, value);
        editor.clear();
        editor.commit();
    }

    public static String getPreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        String pValue = sharedPreferences.getString(key, value);
        return pValue;
    }

    public static boolean getBooleanPreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        boolean pValue = sharedPreferences.getBoolean(key, value);
        return pValue;
    }

    public static int getIntPreferences(String key, int value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        int pValue = sharedPreferences.getInt(key, value);
        return pValue;
    }

    public static float getFloatPreferences(String key, float value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        float pValue = sharedPreferences.getFloat(key, value);
        return pValue;
    }

    public static long getLongPreferences(String key, long value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        long pValue = sharedPreferences.getLong(key, value);
        return pValue;
    }

    //sha1 加密
    public static String getSha1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }


    /**
     * 去除特殊字符或将所有中文标号替换为英文标号
     *
     * @param str
     * @return
     */
    public static String stringFilter(String str) {
        str = str.replaceAll("【", "[").replaceAll("】", "]")
                .replaceAll("！", "!").replaceAll("：", ":");// 替换中文标号
        String regEx = "[『』]"; // 清除掉特殊字符
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 对图片进行 高斯模糊
     *
     * @param bmp
     * @return
     */
    public static Bitmap BoxBlurFilter(Bitmap bmp) {
        /** 水平方向模糊度 */
        float hRadius = 10;
        /** 竖直方向模糊度 */
        float vRadius = 10;
        /** 模糊迭代度 */
        int iterations = 7;
        int width = bmp.getWidth();
        int height = bmp.getHeight();
        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmp.getPixels(inPixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < iterations; i++) {
            blur(inPixels, outPixels, width, height, hRadius);
            blur(outPixels, inPixels, height, width, vRadius);
        }
//        blurFractional(inPixels, outPixels, width, height, hRadius);
//        blurFractional(outPixels, inPixels, height, width, vRadius);
        bitmap.setPixels(inPixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    public static void blur(int[] in, int[] out, int width, int height, float radius) {
        int widthMinus1 = width - 1;
        int r = (int) radius;
        int tableSize = 2 * r + 1;
        int divide[] = new int[256 * tableSize];
        for (int i = 0; i < 256 * tableSize; i++) {
            divide[i] = i / tableSize;
        }
        int inIndex = 0;
        for (int y = 0; y < height; y++) {
            int outIndex = y;
            int ta = 0, tr = 0, tg = 0, tb = 0;
            for (int i = -r; i <= r; i++) {
                int rgb = in[inIndex + clamp(i, 0,
                        width - 1)];
                ta += (rgb >> 24) & 0xff;
                tr += (rgb >> 16) & 0xff;
                tg += (rgb >> 8) & 0xff;
                tb += rgb & 0xff;
            }
            for (int x = 0; x < width; x++) {
                out[outIndex] = (divide[ta] << 24) | (divide[tr] << 16)
                        | (divide[tg] << 8) | divide[tb];
                int i1 = x + r + 1;
                if (i1 > widthMinus1)
                    i1 = widthMinus1;
                int i2 = x - r;
                if (i2 < 0)
                    i2 = 0;
                int rgb1 = in[inIndex + i1];
                int rgb2 = in[inIndex + i2];
                ta += ((rgb1 >> 24) & 0xff) - ((rgb2 >> 24) & 0xff);
                tr += ((rgb1 & 0xff0000) - (rgb2 & 0xff0000)) >> 16;
                tg += ((rgb1 & 0xff00) - (rgb2 & 0xff00)) >> 8;
                tb += (rgb1 & 0xff) - (rgb2 & 0xff);
                outIndex += height;
            }
            inIndex += width;
        }
    }


    public static int clamp(int x, int a, int b) {
        return (x < a) ? a : (x > b) ? b : x;
    }

    /**
     * Toast
     *
     * @param argText
     */
    public static void ShowToast(final String argText) {
        U.ShowToast(MainApplication.getInstance(), argText);
    }

    /**
     * Toast
     *
     * @param argText
     */
    public static void ShowToast(final Context argContext, final String argText) {

        Handler mainHandler = new Handler(argContext.getMainLooper());

        Runnable myRunnable = new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }

                mToast = Toast.makeText(argContext, argText, Toast.LENGTH_SHORT);
                mToast.setGravity(Gravity.CENTER, 0, 0);
                mToast.show();
            }
        }; // This is your code
        mainHandler.post(myRunnable);
    }

    /**
     * 保存图片到SD卡
     *
     * @param bitmap      保存的图片
     * @param argFileName
     * @param quality     保存的质量（1-100）
     * @param fScale      压缩比率 1为原图
     */
    public static String saveBitmapToSD(Bitmap bitmap, String path, String argFileName, int quality, float fScale) {
        File pFileDir = new File(Environment.getExternalStorageDirectory(), path);
        String url = "";
        File pFilePath = new File(pFileDir, argFileName);
        if (!pFileDir.exists()) {
            pFileDir.mkdirs();//如果路径不存在就先创建路径
        }
        try {
            FileOutputStream out = new FileOutputStream(pFilePath);
            BufferedOutputStream stream = new BufferedOutputStream(out);
            Bitmap newBitmap;
            if (fScale == 1) {
                newBitmap = Bitmap.createBitmap(bitmap);
            } else {
                newBitmap = compressImage(bitmap, fScale);
            }
            newBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
            url = pFilePath.getAbsolutePath();
            out.close();
            stream.flush();//输出
            stream.close();//关闭
            newBitmap.recycle();
            newBitmap = null;
            Logger.INSTANCE.tag("保存成功" + pFilePath.getAbsolutePath());
//            检测图片是否被旋转
            int arg = readPictureDegree(url);
            if (arg == 0) {
                return url;
            } else {
//                修复旋转重新保存
                saveBitmapToSD(rotaingImageView(arg, bitmap), path, argFileName, quality, fScale);
            }
        } catch (Exception e) {
            Logger.INSTANCE.tag("保存失败" + e.toString());
//            e.printStackTrace();
        }
        return url;
    }

    /*
     * 将字符串进行md5加密
     * pragma argString, 明文
     * return 经过md5加密后的密文
     * */
    public final static String MD5(String argString) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = argString.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public final static String MD5_16(String argString) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = argString.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }

            String strResult = new String(str);
            return strResult.substring(8, 24);
            //return str.toString();
        } catch (Exception e) {
            Log.i("----", e.toString());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 剪切1:1图片
     */
    public static Bitmap cropImage1to1(Bitmap bitmap) {
        if (bitmap == null) {
            return bitmap;
        }
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int retX = 0;
        int retY = 0;
        int newWidth = w;
        int newHeight = w;
        if (w >= h) {
            retX = (int) ((w - h) / 2);
            retY = 0;
            newWidth = h;
            newHeight = h;
        }
        //下面这句是关键
        Bitmap pResultBitmap = Bitmap.createBitmap(bitmap, retX, retY, newWidth, newHeight, null, false);
        return pResultBitmap;
    }

    /*
     * 压缩图片到原大小的scale倍 (100)
     * @param argBitmap 原图片
     * @return 压缩后的图片
     * */
    public static Bitmap compressImage(Bitmap argBitmap, float fScale) {
        /// 图片源
        Bitmap bm = argBitmap;
        /// 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        /// 设置想要的大小
        int newWidth = (int) (width * fScale);
        int newHeight = (int) (height * fScale);
        /// 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        /// 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        /// 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        if (bm != newbm) {
            bm.recycle();
            bm = null;
        }
        return newbm;
    }

    private static final int OPTIONS_NONE = 0x0;

    /**
     * 从相册获取一张照片
     */
    public static Bitmap getBitmapFromAlbum() {
        List<Integer> albumList = getPhotoAlbum();
        if (albumList != null && albumList.size() > 0) {
            /** 通过ID 获取缩略图*/
            Bitmap thumbBitmap = MediaStore.Images.Thumbnails.getThumbnail(MainApplication.getInstance().getContentResolver(), albumList.get(0), MediaStore.Images.Thumbnails.MICRO_KIND, null);
            return thumbBitmap;
        }
        return null;
    }

    /**
     * 方法描述：按相册获取图片信息
     */
    // 设置获取图片的字段信息
    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media.DISPLAY_NAME, // 显示的名称
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.LONGITUDE, // 经度
            MediaStore.Images.Media._ID, // id
            MediaStore.Images.Media.BUCKET_ID, // dir id 目录
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME // dir name 目录名字

    };

    /**
     * 获取相册集合
     *
     * @return
     */
    public static List<Integer> getPhotoAlbum() {
        List<Integer> aibumList = new ArrayList<Integer>();
        Cursor cursor = MediaStore.Images.Media.query(MainApplication.getInstance().getContentResolver(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, STORE_IMAGES);
        while (cursor.moveToNext()) {
            String id = cursor.getString(3);
            aibumList.add(Integer.parseInt(id));
        }
        cursor.close();
        return aibumList;
    }

    //打开系统相册
    public Intent selectPicture() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return intent;
    }

    /**
     * 修改图片大小
     *
     * @param source 原图
     * @param width  需要的宽
     * @param height 需要的高
     * @return
     */
    public static Bitmap extractThumbnail(Bitmap source, int width, int height) {
        return extractThumbnail(source, width, height, OPTIONS_NONE);
    }

    /**
     * Creates a centered bitmap of the desired size.
     *
     * @param source  original bitmap source
     * @param width   targeted width
     * @param height  targeted height
     * @param options options used during thumbnail extraction
     */
    public static Bitmap extractThumbnail(
            Bitmap source, int width, int height, int options) {
        if (source == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(1, 1);
        Bitmap thumbnail = transform(matrix, source, width, height,
                1 | options);
        return thumbnail;
    }

    /**
     * Transform source Bitmap to targeted width and height.
     */
    private static Bitmap transform(Matrix scaler, Bitmap source, int targetWidth, int targetHeight, int options) {
        boolean scaleUp = (options & 1) != 0;
        boolean recycle = (options & 1) != 0;
        int deltaX = source.getWidth() - targetWidth;
        int deltaY = source.getHeight() - targetHeight;
        if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
            Bitmap b2 = Bitmap.createBitmap(targetWidth, targetHeight,
                    Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b2);

            int deltaXHalf = Math.max(0, deltaX / 2);
            int deltaYHalf = Math.max(0, deltaY / 2);
            Rect src = new Rect(
                    deltaXHalf,
                    deltaYHalf,
                    deltaXHalf + Math.min(targetWidth, source.getWidth()),
                    deltaYHalf + Math.min(targetHeight, source.getHeight()));
            int dstX = (targetWidth - src.width()) / 2;
            int dstY = (targetHeight - src.height()) / 2;
            Rect dst = new Rect(
                    dstX,
                    dstY,
                    targetWidth - dstX,
                    targetHeight - dstY);
            c.drawBitmap(source, src, dst, null);
            if (recycle) {
                source.recycle();
            }
            c.setBitmap(null);
            return b2;
        }
        float bitmapWidthF = source.getWidth();
        float bitmapHeightF = source.getHeight();

        float bitmapAspect = bitmapWidthF / bitmapHeightF;
        float viewAspect = (float) targetWidth / targetHeight;

        if (bitmapAspect > viewAspect) {
            float scale = targetHeight / bitmapHeightF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        } else {
            float scale = targetWidth / bitmapWidthF;
            if (scale < .9F || scale > 1F) {
                scaler.setScale(scale, scale);
            } else {
                scaler = null;
            }
        }

        Bitmap b1;
        if (scaler != null) {
            // this is used for minithumb and crop, so we want to filter here.
            b1 = Bitmap.createBitmap(source, 0, 0,
                    source.getWidth(), source.getHeight(), scaler, true);
        } else {
            b1 = source;
        }

        if (recycle && b1 != source) {
            source.recycle();
        }

        int dx1 = Math.max(0, b1.getWidth() - targetWidth);
        int dy1 = Math.max(0, b1.getHeight() - targetHeight);

        Bitmap b2 = Bitmap.createBitmap(
                b1,
                dx1 / 2,
                dy1 / 2,
                targetWidth,
                targetHeight);

        if (b2 != b1) {
            if (recycle || b1 != source) {
                b1.recycle();
            }
        }

        return b2;
    }

    /**
     * 判断网络状态
     *
     * @return
     */
    public static boolean CheckNetworkConnected() {
        if (MainApplication.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) MainApplication.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 防止快速点击
     */
    private static long lastClickTime;

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    // 截取字符串最后一位
    public static String getEndStr(String tid) {
        return tid.substring(0, tid.length() - 1);
    }

    //    集合转字符串分割数组
    public static String listToString(String str) {
        str = str.toString().replace("[", "");
        str = str.replace("]", "");
        return str.replace(" ", "");
    }

    //解密
    public static String base64(String m) {
        return new String(Base64.decode(m, Base64.DEFAULT));
    }

    //    加密
    public static String base64jm(String m) {
        return new String(Base64.encode(m.getBytes(), Base64.DEFAULT));
    }

    /**
     * 旋转图片
     *
     * @param angle  被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        Bitmap returnBm = null;
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }

    /**
     * 状态栏高度
     *
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 显示键盘
     */
    public static void showKeyBoard(EditText view) {
        InputMethodManager imm = (InputMethodManager) MainApplication.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        imm.showSoftInput(view, 0);
    }


    public static void sysPhone(Context context, String phone) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone));
//开启系统拨号器
        context.startActivity(intent);
    }

}
