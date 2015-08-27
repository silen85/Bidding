package com.lessomall.bidding.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.lessomall.bidding.R;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by meisl on 2015/8/15.
 */
public class GetWebImageTask extends AsyncTask<String, Void, Bitmap> {

    private String TAG = "com.lessomall.bidding.common.GetWebImageTask";

    private Context context;

    private ImageView vw;

    private WebImageCacheI webImageCacheI;

    public GetWebImageTask(Context context, ImageView vw) {
        this.context = context;
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
            if (webImageCacheI != null) {
                webImageCacheI.putCache(bitmap);
            }
        } else {
            vw.setImageResource(R.mipmap.pic_default);
            if (webImageCacheI != null) {
                webImageCacheI.putCache(BitmapFactory.decodeResource(context.getResources(), R.mipmap.pic_default));
            }
        }
        vw.invalidate();

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

    public void setWebImageCacheI(WebImageCacheI webImageCacheI) {
        this.webImageCacheI = webImageCacheI;
    }

    public interface WebImageCacheI {
        void putCache(Bitmap bitmap);
    }
}