package cn.itsite.akeyboard;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;

/**
 * @author leguang
 * @version v0.0.0
 * @E-mail langmanleguang@qq.com
 * @time 2017/11/2 0002 17:07
 */
public class KeyboardHelper implements KeyboardView.OnKeyboardActionListener {
    private static final String TAG = KeyboardHelper.class.getSimpleName();
    private KeyboardView mKeyboardView;
    private Keyboard provincekeys; // 省、直辖市、特使车牌键盘
    private Keyboard keys; // 省、直辖市、特使车牌键盘
    private TextView mTextView;
    private int maxLength = 8;

    private KeyboardHelper() {
    }

    public KeyboardHelper(KeyboardView keyboardView, TextView textView) {
        mKeyboardView = keyboardView;
        mTextView = textView;
        provincekeys = new Keyboard(keyboardView.getContext(), R.xml.key_provice);
        mKeyboardView.setKeyboard(provincekeys);
        mKeyboardView.setEnabled(true);
        mKeyboardView.setPreviewEnabled(false);
        mKeyboardView.setOnKeyboardActionListener(this);
    }

    public KeyboardView getKeyboardView() {
        return mKeyboardView;
    }

    public KeyboardHelper setKeyboardView(KeyboardView keyboardView) {
        this.mKeyboardView = keyboardView;
        return this;
    }

    public Keyboard getkeys() {
        return mKeyboardView.getKeyboard();
    }

    public KeyboardHelper setkeys(Keyboard keys) {
        this.mKeyboardView.setKeyboard(keys);
        return this;
    }

    public TextView getTextView() {
        return mTextView;
    }

    public KeyboardHelper setTextView(TextView textView) {
        this.mTextView = textView;
        return this;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public KeyboardHelper setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        return this;
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        switch (primaryCode) {
            case Keyboard.KEYCODE_DELETE:
                delete();
                break;
            case Keyboard.KEYCODE_SHIFT:
                shift();
                break;
            case Keyboard.KEYCODE_ALT:
                alt();
                break;
            default:
        }
    }

    @Override
    public void onText(CharSequence text) {
        if (mTextView.length() >= maxLength) {
            hide();
            return;
        }
        mTextView.append(text);
        if (mTextView.length() == 1) {
            if (keys == null) {
                keys = new Keyboard(mKeyboardView.getContext(), R.xml.key_qwerty);
            }
            mKeyboardView.setKeyboard(keys);
        }
    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }

    public void hide() {
        if (mKeyboardView == null) {
            return;
        }
        if (mKeyboardView.getVisibility() == View.VISIBLE) {
            mKeyboardView.setVisibility(View.GONE);
        }
    }

    public void show() {
        if (mKeyboardView == null) {
            return;
        }
        if (mKeyboardView.getVisibility() != View.VISIBLE) {
            mKeyboardView.setVisibility(View.VISIBLE);
        }
    }

    private void delete() {
        Editable editable = mTextView.getEditableText();
        if (editable != null) {
            int start = editable.length();
            if (start > 0) {
                editable.delete(start - 1, start);
                if (editable.length() == 0) {
                    if (provincekeys == null) {
                        provincekeys = new Keyboard(mKeyboardView.getContext(), R.xml.key_provice);
                    }
                    mKeyboardView.setKeyboard(provincekeys);
                }
            }
        }
    }

    private void shift() {
        if (keys == null) {
            keys = new Keyboard(mKeyboardView.getContext(), R.xml.key_qwerty);
        }
        mKeyboardView.setKeyboard(keys);
    }

    private void alt() {
        if (provincekeys == null) {
            provincekeys = new Keyboard(mKeyboardView.getContext(), R.xml.key_provice);
        }
        mKeyboardView.setKeyboard(provincekeys);
    }
}
