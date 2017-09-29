package com.aglhz.s1.net.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aglhz.abase.mvp.view.base.BaseFragment;
import com.aglhz.abase.utils.KeyBoardUtils;
import com.aglhz.s1.App;
import com.aglhz.yicommunity.R;
import com.aglhz.yicommunity.login.contract.LoginContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by leguang on 2017/5/24 0029.
 * Email：langmanleguang@qq.com
 */
public class HotPasswordFragment extends BaseFragment implements TextWatcher {
    private static final String TAG = HotPasswordFragment.class.getSimpleName();
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_password_hot_password_fragment)
    EditText etPassword;
    @BindView(R.id.et_confirm_hot_password_fragment)
    EditText etConfirm;
    @BindView(R.id.bt_save_hot_password_fragment)
    Button btSave;
    private Unbinder unbinder;

    public static HotPasswordFragment newInstance() {
        return new HotPasswordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        return attachToSwipeBack(view);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        initData();
    }

    private void initData() {
        etConfirm.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);
    }

    private void initToolbar() {
        initStateBar(toolbar);
        toolbarTitle.setText("修改热点密码");
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(v -> _mActivity.onBackPressedSupport());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyBoardUtils.hideKeybord(etPassword, App.mContext);
        unbinder.unbind();
    }

    @OnClick(R.id.bt_save_hot_password_fragment)
    public void onViewClicked() {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        boolean enabled = TextUtils.isEmpty(etPassword.getText().toString())
                || TextUtils.isEmpty(etConfirm.getText().toString());
        btSave.setEnabled(!enabled);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
