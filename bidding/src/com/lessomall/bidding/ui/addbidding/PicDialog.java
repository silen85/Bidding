package com.lessomall.bidding.ui.addbidding;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lessomall.bidding.R;
import com.lessomall.bidding.activity.BaseActivity;
import com.lessomall.bidding.common.Tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by meisl on 2015/8/12.
 */
public class PicDialog extends Dialog {

    private String TAG = "com.lessomall.bidding.ui.addbidding.PicDialog";

    private Context context;

    private int type = 0;   //0:相机；1:相册

    private List<String> imagePathList = new ArrayList(5);

    public PicDialog(Context context) {

        super(context);

        this.context = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        init();
    }

    private void init() {

        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout dialog = (LinearLayout) inflater.inflate(R.layout.dialog_pic, null);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setContentView(dialog, params);
        getWindow().getAttributes().width = ViewGroup.LayoutParams.MATCH_PARENT;

        LinearLayout camera = (LinearLayout) findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                type = 0;

                dismiss();

                String sdStatus = Environment.getExternalStorageState();
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
                    Log.d(TAG, context.getString(R.string.SD_card_error));
                    Toast.makeText(context, context.getString(R.string.SD_card_error), Toast.LENGTH_SHORT).show();
                    return;
                }

                Uri uri = Uri.fromFile(getOutPutMediaFile());

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                ((BaseActivity) context).setImageUri(uri);

                ((BaseActivity) context).startActivityForResult(intent, BaseActivity.RESULT_CAPTURE_IMAGE);


            }
        });


        LinearLayout gallery = (LinearLayout) findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                type = 1;

                dismiss();

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                ((BaseActivity) context).startActivityForResult(intent, BaseActivity.RESULT_GALLERY_IMAGE);

            }
        });

    }

    public File getOutPutMediaFile() {

        if (!context.getExternalCacheDir().exists())
            context.getExternalCacheDir().mkdir();

        String strImgPath = context.getExternalCacheDir().getPath();      // 存放照片的文件夹

        String fileName = Tools.formatDate(new Date(), "yyyyMMddHHmmss") + ".jpg";// 照片命名

        return new File(strImgPath, fileName);
    }

    public int getType() {
        return type;
    }

    public List<String> getImagePathList() {
        return imagePathList;
    }
}
