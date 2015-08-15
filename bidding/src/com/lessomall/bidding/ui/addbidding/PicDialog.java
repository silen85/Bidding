package com.lessomall.bidding.ui.addbidding;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lessomall.bidding.R;
import com.lessomall.bidding.common.Constant;
import com.lessomall.bidding.common.Tools;

import java.io.File;
import java.util.Date;

/**
 * Created by meisl on 2015/8/12.
 */
public class PicDialog extends Dialog {

    private String TAG = "com.lessomall.bidding.ui.addbidding.PicDialog";

    private static final int RESULT_CAPTURE_IMAGE = 1;// 照相的requestCode
    private static final int RESULT_CROP_IMAGE = 2; // 裁剪的requestCode

    private Context context;

    private int type = 0;       //0:拍照  1:从相册中选择

    private Uri imageUri; // 拍照时的uri
    private File imageFile;// 相册或拍照的file

    private ClickListenerInterface clickListenerInterface;

    public interface ClickListenerInterface {
        void doFinish();
    }

    public PicDialog(Context context, int type) {

        super(context);

        this.context = context;

        this.type = type;

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

                imageUri = Uri.fromFile(getOutPutMediaFile());

                if (imageUri == null) {
                    Toast.makeText(context, "暂不能拍照，请见谅！", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra("return-data", false);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                ((Activity) context).startActivityForResult(intent, RESULT_CAPTURE_IMAGE);

            }
        });


        LinearLayout gallery = (LinearLayout) findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private File getOutPutMediaFile() {

        String strImgPath = Constant.BASE_DIR;      // 存放照片的文件夹

        String fileName = Tools.formatDate(new Date(), "yyyyMMddHHmmss") + ".jpg";// 照片命名

        File out = new File(strImgPath);

        if (!out.exists())
            out.mkdirs();

        imageFile = new File(strImgPath, fileName);
        return imageFile;
    }

    public void setClickListenerInterface(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

}
