package com.lessomall.bidding.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by meisl on 2015/8/15.
 */
public class GetWebImageTask extends AsyncTask<String, Void, Bitmap> {

    private String TAG = "com.lessomall.bidding.common.GetWebImageTask";

    private ImageView vw;

    public GetWebImageTask(ImageView vw) {
        this.vw = vw;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            vw.setImageBitmap(bitmap);
            vw.invalidate();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        Bitmap bitMap = null;
        URL aryURI;
        try {
            aryURI = new URL(params[0]);
            URLConnection conn = aryURI.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            bitMap = BitmapFactory.decodeStream(is);
            is.close();

        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return bitMap;

    }
}