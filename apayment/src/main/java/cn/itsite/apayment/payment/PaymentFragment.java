package cn.itsite.apayment.payment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import cn.itsite.apayment.R;

public class PaymentFragment extends AppCompatDialogFragment {
    private static final String TAG = PaymentFragment.class.getName();
    private WebView webView;
    private TextView tvLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_payment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
        webView = view.findViewById(R.id.wv_payment_dialog);
        tvLoading = view.findViewById(R.id.tv_loading);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}