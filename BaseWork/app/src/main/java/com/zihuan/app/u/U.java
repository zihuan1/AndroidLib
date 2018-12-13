package com.zihuan.app.u;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.zihuan.app.MainApplication;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class U {
    private static Toast mToast;

    // 隐藏键盘
    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager)
                context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    @SuppressWarnings("unchecked")
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

    public static boolean getPreferences(String key, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        boolean pValue = sharedPreferences.getBoolean(key, value);
        return pValue;
    }

    public static String getPreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        String pValue = sharedPreferences.getString(key, value);
        return pValue;
    }

    public static int getPreferences(String key, int value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        int pValue = sharedPreferences.getInt(key, value);
        return pValue;
    }

    public static float getPreferences(String key, float value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MainApplication.getInstance());
        float pValue = sharedPreferences.getFloat(key, value);
        return pValue;
    }

    public static long getPreferences(String key, long value) {
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
     * 旋转图片
     *
     * @param bm                原图
     * @param orientationDegree 旋转角度 90;
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bm, final int orientationDegree) {

        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        float targetX, targetY;
        if (orientationDegree == 90) {
            targetX = bm.getHeight();
            targetY = 0;
        } else {
            targetX = bm.getHeight();
            targetY = bm.getWidth();
        }

        final float[] values = new float[9];
        m.getValues(values);

        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];

        m.postTranslate(targetX - x1, targetY - y1);

        Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(bm1);
        canvas.drawBitmap(bm, m, paint);
        canvas.save();
        return bm1;

    }

    /**
     * 对图片进行 高斯模糊
     *
     * @param bmp
     * @return
     */
    public static Drawable BoxBlurFilter(Bitmap bmp) {
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
        Drawable drawable = new BitmapDrawable(bitmap);
        return drawable;
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
                newBitmap = bitmap;
            } else {
                newBitmap = U.compressImage(bitmap, fScale);
            }
            newBitmap.compress(Bitmap.CompressFormat.JPEG, quality, stream);
            url = pFilePath.getAbsolutePath();
            out.close();
            stream.flush();//输出
            stream.close();//关闭
            newBitmap.recycle();
            newBitmap = null;
            Logger.tag("保存成功" + pFilePath.getAbsolutePath());
//            检测图片是否被旋转
            int arg = readPictureDegree(url);
            if (arg == 0) {
                bitmap = null;
                return url;
            } else {
//                修复旋转重新保存
                saveBitmapToSD(rotaingImageView(arg, bitmap), path, argFileName, quality, fScale);
            }

        } catch (Exception e) {
            Logger.tag("保存失败" + e.toString());
//            e.printStackTrace();
        }
        return url;
    }

    public static String saveBitmapToSD2(Bitmap bitmap, String path, String argFileName) {
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
            newBitmap = compressImage(bitmap);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            url = pFilePath.getAbsolutePath();
            out.close();
            stream.flush();//输出
            stream.close();//关闭
            newBitmap.recycle();
            newBitmap = null;
            Logger.tag("保存成功" + pFilePath.getAbsolutePath());
            bitmap = null;
            return url;
        } catch (Exception e) {
            Logger.tag("保存失败" + e.toString());
//            e.printStackTrace();
        }
        return url;
    }

    /**
     * 压缩图片
     *
     * @param bitmap
     * @return
     */
    public static Bitmap compressImage(Bitmap bitmap) {
        //图片宽高
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        //宽高缩放比例
        double sw = w * 1.0 / 416;
        double sh = h * 1.0 / 416;
        Matrix matrix = new Matrix();
        if (Math.max(sw, sh) < 1) {
            matrix.postScale(416f / w, 416f / h);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        } else if (Math.min(sw, sh) >= 1) {
            matrix.postScale(416f / w, 416f / h);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        } else if (Math.min(sw, sh) < 1 && Math.max(sw, sh) >= 1) {
            if (sw > sh) {
                matrix.postScale(1, 416f / h);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, 416, h, matrix, true);
            } else {
                matrix.postScale(416f / w, 1);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, w, 416, matrix, true);
            }
        }
        return bitmap;
    }


    /**
     * 从相册拿到没有经过旋转的图片
     *
     * @param mImageCaptureUri URI  data.getData();
     *                         不管是拍照还是选择图片每张图片都有在数据中存储也存储有对应旋转角度orientation值
     *                         所以我们在取出图片是把角度值取出以便能正确的显示图片,没有旋转时的效果观看
     * @return bitmap
     */
    public static Bitmap getNoRotateBitmapFromAlbum(Uri mImageCaptureUri) {

        // 不管是拍照还是选择图片每张图片都有在数据中存储也存储有对应旋转角度orientation值
        // 所以我们在取出图片是把角度值取出以便能正确的显示图片,没有旋转时的效果观看
        ContentResolver cr = MainApplication.getInstance().getContentResolver();
        Cursor cursor = cr.query(mImageCaptureUri, null, null, null, null);// 根据Uri从数据库中找
        if (cursor != null) {
            Log.e("----", "11111111" + mImageCaptureUri);
            cursor.moveToFirst();// 把游标移动到首位，因为这里的Uri是包含ID的所以是唯一的不需要循环找指向第一个就是了
            int iFileColumnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);

            if (iFileColumnIndex != -1) {
                Log.e("----", "2222222");
                String filePath = cursor.getString(iFileColumnIndex);// 获取图片路
                String orientation;
                int iColumnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION);
                if (iColumnIndex == -1) {
                    Log.e("----", "33333333");
                    orientation = "-1";
                } else {
                    Log.e("----", "4444444444");
                    orientation = cursor.getString(iColumnIndex);// 获取旋转的角度
                }
                cursor.close();
                if (filePath != null) {
                    // Bitmap bitmap = BitmapFactory.decodeFile(filePath);//根据Path读取资源图片
                    Bitmap bitmap = U.getBitmapFromFile(new File(filePath), 1200, 1600);
                    if (!orientation.equals("-1")) {
                        int angle = 0;
                        if (orientation != null && !"".equals(orientation)) {
                            angle = Integer.parseInt(orientation);
                        }
                        if (angle != 0) {
                            // 下面的方法主要作用是把图片转一个角度，也可以放大缩小等
                            Matrix m = new Matrix();
                            int width = bitmap.getWidth();
                            int height = bitmap.getHeight();
                            m.setRotate(angle); // 旋转angle度
                            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                                    m, true);// 从新生成图片
                        }
                    }

                    return bitmap;
                }
            } else {

                Log.e("----", "555555555");
                Bitmap bitmap = null;

                //外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口
                ContentResolver resolver = MainApplication.getInstance().getContentResolver();

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(resolver, mImageCaptureUri);        //显得到bitmap图片
                } catch (IOException e) {

                    e.printStackTrace();
                }

                Log.e("----", "666666666" + bitmap);

                return bitmap;
            }

        }
        return null;
    }


    /*
     * 获取Uri的实际路径
     * pragma contentUri, 文件的uri
     * return 文件存放的真实path
     * */
    public static String getRealPathFromURI(Uri contentUri, Activity argActivity) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = argActivity.managedQuery(contentUri, proj, null, null, null);
        int column_index
                = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
                true);

//        if (bm!=newbm)
//        {
//            bm.recycle();
//            bm = null;
//        }
        return newbm;
    }

    private static final int OPTIONS_NONE = 0x0;

    /**
     * 从相册获取一张封面照片
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


    /**
     * 从 文件里读取图片 不会内存溢出
     *
     * @param dst
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getBitmapFromFile(File dst, int width, int height) {
        if (null != dst && dst.exists()) {
            BitmapFactory.Options opts = null;
            if (width > 0 && height > 0) {
                opts = new BitmapFactory.Options();           //设置inJustDecodeBounds为true后，decodeFile并不分配空间，此时计算原始图片的长度和宽度
                opts.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(dst.getPath(), opts);
                // 计算图片缩放比例
                final int minSideLength = Math.min(width, height);
                opts.inSampleSize = computeSampleSize(opts, minSideLength,
                        width * height);            //这里一定要将其设置回false，因为之前我们将其设置成了true
                opts.inJustDecodeBounds = false;
                opts.inInputShareable = true;
                opts.inPurgeable = true;
            }
            try {
                return BitmapFactory.decodeFile(dst.getPath(), opts);
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;

        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math
                .floor(w / minSideLength), Math.floor(h / minSideLength));

        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }


    /**
     * 修改图片大小
     *
     * @param source 原图
     * @param width  需要的宽
     * @param height 需要的高
     * @return
     */
    public static Bitmap extractThumbnail(
            Bitmap source, int width, int height) {
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
    private static Bitmap transform(Matrix scaler,
                                    Bitmap source,
                                    int targetWidth,
                                    int targetHeight,
                                    int options) {
        boolean scaleUp = (options & 1) != 0;
        boolean recycle = (options & 1) != 0;

        int deltaX = source.getWidth() - targetWidth;
        int deltaY = source.getHeight() - targetHeight;
        if (!scaleUp && (deltaX < 0 || deltaY < 0)) {
            /*
             * In this case the bitmap is smaller, at least in one dimension,
             * than the target.  Transform it by placing as much of the image
             * as possible into the target and leaving the top/bottom or
             * left/right (or both) black.
             */
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

    public static String uRLDecoder(String str) {

        try {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            return URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return "";
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


    //    压缩图片
    public static Bitmap ScaledBitmap(String filePath, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        Logger.tag("长度" + options.outHeight);
        Logger.tag("宽度" + options.outWidth);
//        options.outHeight = reqHeight;
//        options.outWidth = reqWidth;
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    // 截取字符串最后一位
    public static String getStr(String tid) {
        return tid.substring(0, tid.length() - 1);
    }


    public static String strMi(int distance) {
        String distanceStr = distance < 1000 ? distance + "m" : (distance / 1000) + "km";
        return distanceStr;
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
     * 模拟点击，点击该View的xy坐标(5,5)
     *
     * @param view 模拟点击的View
     */
    public static void simulateTouch(View view) {
        final long downTime = SystemClock.uptimeMillis();
        int[] ints = new int[2];
        view.getLocationOnScreen(ints);
        view.dispatchTouchEvent(MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_DOWN,
                ints[0] + 5, ints[1] + 5, 0));
        view.dispatchTouchEvent(MotionEvent.obtain(downTime, downTime, MotionEvent.ACTION_UP,
                ints[0] + 5, ints[1] + 5, 0));

    }

    /**
     * 处理旋转后的图片
     *
     * @param originpath 原图路径
     * @return 返回修复完毕后的图片路径
     */
    public static Bitmap amendRotatePhoto(String originpath, Bitmap bitmap) {

        // 取得图片旋转角度
        int angle = readPictureDegree(originpath);

        // 把原图压缩后得到Bitmap对象
//        Bitmap bmp = getCompressPhoto(originpath);

        // 修复图片被旋转的角度
        Bitmap bit = rotaingImageView(angle, bitmap);

        // 保存修复后的图片并返回保存后的图片路径
        return bit;
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
     * 获取单个文件的MD5值！
     *
     * @param file
     * @return
     */
    public static String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }
}
