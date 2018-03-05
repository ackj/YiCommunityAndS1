package com.aglhz.s1.common.clip;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.itsite.abase.log.ALog;

import com.aglhz.s1.widget.ClipImageLayout;
import com.aglhz.s1.utils.ImageTools;
import com.aglhz.s1.utils.ImageUtils;
import com.aglhz.yicommunity.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ClipActivity extends Activity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_menu)
    TextView toolbarMenu;
    @BindView(R.id.id_clipImageLayout)
    ClipImageLayout clipImageLayout;

    private String path;
    private ProgressDialog loadingDialog;
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipimage);
        unbinder = ButterKnife.bind(this);
        //这步必须要加
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initToolbar();
        initData();


    }

    private void initToolbar() {
        toolbarTitle.setText("裁剪");
        toolbarMenu.setText("确定");
    }

    private void initData() {
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setTitle("请稍后...");
        path = getIntent().getStringExtra("path");
        Log.d("hehe", "path:" + path);
        if (TextUtils.isEmpty(path) || !(new File(path).exists())) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Bitmap bitmap = ImageTools.convertToBitmap(path, 600, 600);
        if (bitmap == null) {
            Toast.makeText(this, "图片加载失败", Toast.LENGTH_SHORT).show();
            return;
        }
        clipImageLayout.setBitmap(bitmap);
    }

    @OnClick(R.id.toolbar_menu)
    public void onClickView(View view) {
        loadingDialog.show();

        new Thread(() -> {
            Bitmap bitmap = ImageUtils.zoomImage(clipImageLayout.clip());

            final String path = getCacheDir().getPath() + "/" + System.currentTimeMillis() + ".png";
            ImageTools.savePhotoToSDCard(bitmap, path);
            ALog.e("@@ 压缩后--> width:" + bitmap.getWidth() + " height:" + bitmap.getHeight() + " bytes:" + bitmap.getByteCount());
            Intent intent = new Intent();
            intent.putExtra("path", path);
            setResult(RESULT_OK, intent);
            finish();
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}