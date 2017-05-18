package com.aglhz.yicommunity.publish.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.aglhz.abase.log.ALog;
import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.abase.utils.KeyBoardUtils;
import com.aglhz.abase.utils.ToastUtils;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.bean.BaseBean;
import com.aglhz.yicommunity.common.DialogHelper;
import com.aglhz.yicommunity.common.Params;
import com.aglhz.yicommunity.common.UserHelper;
import com.aglhz.yicommunity.publish.contract.PublishContract;
import com.aglhz.yicommunity.publish.presenter.PublishNeighbourPresenter;
import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing_impl.ui.BoxingActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Author: LiuJia on 2017/5/11 0011 20:36.
 * Email: liujia95me@126.com
 */

public class PublishNeighbourFragment extends BaseFragment<PublishNeighbourPresenter> implements PublishContract.View {
    private final String TAG = PublishNeighbourFragment.class.getSimpleName();

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.et_input_content)
    EditText etInputContent;

    private Unbinder unbinder;
    private PublishImageRVAdapter adapter;
    private Params params = Params.getInstance();
    private boolean requesting;
    BaseMedia addMedia = new BaseMedia() {
        @Override
        public TYPE getType() {
            return TYPE.IMAGE;
        }
    };


    private int which;
    private Dialog loadingDialog;

    public static PublishNeighbourFragment newInstance(int which) {
        PublishNeighbourFragment fragment = new PublishNeighbourFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("which", which);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    protected PublishNeighbourPresenter createPresenter() {
        return new PublishNeighbourPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish_neighbour, container, false);
        unbinder = ButterKnife.bind(this, view);
        which = getArguments().getInt("which");
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
        initListener();
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("发布");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    private void initData() {
        //因为params是单例，所以要将上次选择的清除
        params.files = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(_mActivity, 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        List<BaseMedia> datas = new ArrayList<>();
        addMedia.setPath("android.resource://" + _mActivity.getPackageName() + "/" + R.drawable.ic_image_add_tian_80px);
        datas.add(addMedia);
        adapter = new PublishImageRVAdapter(datas);
        recyclerView.setAdapter(adapter);
    }

    private void initListener() {
        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (position == adapter.getData().size() - 1) {
                selectPhoto();
            }
            return false;
        });

    }

    private void selectPhoto() {
        if (which == 0) {
            BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.MULTI_IMG); // Mode：Mode.SINGLE_IMG, Mode.MULTI_IMG, Mode.VIDEO
            config.needCamera(R.drawable.ic_boxing_camera_white).needGif().withMaxCount(3) // 支持gif，相机，设置最大选图数
                    .withMediaPlaceHolderRes(R.drawable.ic_boxing_default_image); // 设置默认图片占位图，默认无
            Boxing.of(config).withIntent(_mActivity, BoxingActivity.class).start(this, 100);
        } else {
            BoxingConfig config = new BoxingConfig(BoxingConfig.Mode.VIDEO).withVideoDurationRes(R.drawable.ic_boxing_play);
            Boxing.of(config).withIntent(_mActivity, BoxingActivity.class).start(this, 101);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ALog.d(TAG, "onActivityResult:" + requestCode + " --- :" + resultCode);
        if (resultCode == RESULT_OK) {
            if (requestCode == 100) {
                ArrayList<BaseMedia> medias = Boxing.getResult(data);
                for (int i = 0; i < medias.size(); i++) {
                    params.files.add(new File(medias.get(i).getPath()));
                }
                if (params.files.size() > 0) {
                    params.type = 1;
                }
                medias.add(addMedia);
                adapter.setNewData(medias);
            } else if (requestCode == 101) {
                ArrayList<BaseMedia> medias = Boxing.getResult(data);
                if (medias.size() > 0) {
                    File file = new File(medias.get(0).getPath());
                    if (file.length() >= 1024 * 1024 * 10) {
                        DialogHelper.warningSnackbar(getView(), "视频文件不能大于10M");
                    } else {
                        params.type = 2;
                        params.files.add(file);
                        medias.add(addMedia);
                        adapter.setNewData(medias);
                    }
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.hideKeybord(getView(), _mActivity);
        unbinder.unbind();
    }

    @Override
    public void start(Object response) {

    }

    @Override
    public void error(String errorMessage) {
        requesting = false;
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        DialogHelper.errorSnackbar(getView(), errorMessage);
    }

    @Override
    public void responseSuccess(BaseBean bean) {
        requesting = false;
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
        DialogHelper.successSnackbar(getView(), "提交成功!");
        pop();
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        String content = etInputContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            DialogHelper.errorSnackbar(getView(), "请输入内容");
            return;
        }
        submit(content);
    }

    private void submit(String content) {
        if (requesting) {
            ToastUtils.showToast(_mActivity, "正在提交当中，请勿重复操作");
            return;
        }
        params.cmnt_c = UserHelper.communityCode;
        params.content = content;
        mPresenter.post(params);
        if (loadingDialog == null) {
            loadingDialog = DialogHelper.loading(_mActivity);
        }
        loadingDialog.show();
        requesting = true;
    }
}
