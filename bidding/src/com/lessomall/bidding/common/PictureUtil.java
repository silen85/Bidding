package com.lessomall.bidding.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by meisl on 2015/9/4.
 */
public class PictureUtil {

    public static String compressImage(String path) throws Exception {

        Bitmap bitmap = getSmallBitmap(path);

        int degree = readPictureDegree(path);

        if (degree != 0) {
            bitmap = rotateBitmap(bitmap, degree);
        }

        FileOutputStream fileOutputStream = new FileOutputStream(path);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);

        fileOutputStream.flush();
        fileOutputStream.close();

        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }

        return path;

    }

    public static String compressImage(String srcPath, String dstPath) throws Exception {

        Bitmap bitmap = getSmallBitmap(srcPath);

        int degree = readPictureDegree(srcPath);

        if (degree != 0) {
            bitmap = rotateBitmap(bitmap, degree);
        }

        FileOutputStream fileOutputStream = new FileOutputStream(dstPath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);

        fileOutputStream.flush();
        fileOutputStream.close();

        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }

        return dstPath;

    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    public static Bitmap getSmallBitmap(String path) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
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

    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

}
