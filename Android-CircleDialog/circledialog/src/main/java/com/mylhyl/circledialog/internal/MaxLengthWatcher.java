package com.mylhyl.circledialog.internal;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.mylhyl.circledialog.view.listener.OnInputCounterChangeListener;

/**
 * 中文2字符
 * Created by hupei on 2018/11/1 11:19.
 */
public class MaxLengthWatcher implements TextWatcher {
    private int mMaxLen;
    private EditText mEditText;
    private TextView mTvCounter;
    private OnInputCounterChangeListener mOnInputCounterChangeListener;

    public MaxLengthWatcher(int maxLen, EditText editText, TextView textView, OnInputCounterChangeListener listener) {
        this.mMaxLen = maxLen;
        this.mEditText = editText;
        this.mTvCounter = textView;
        this.mOnInputCounterChangeListener = listener;
        if (mEditText == null) {
            return;
        }
        String defText = mEditText.getText().toString();
        int currentLen = maxLen - chineseLength(defText);
        if (mOnInputCounterChangeListener != null) {
            String counterText = mOnInputCounterChangeListener.onCounterChange(maxLen, currentLen);
            mTvCounter.setText(counterText == null ? "" : counterText);
        } else {
            mTvCounter.setText(String.valueOf(currentLen));
        }
    }

    private static int chineseLength(String str) {
        int valueLength = 0;
        if (TextUtils.isEmpty(str)) {
            return valueLength;
        }
        for (int i = 0; i < str.length(); i++) {
            String temp = str.substring(i, i + 1);
            if (isChinese(temp)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    private static boolean isChinese(String str) {
        boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            String temp = str.substring(i, i + 1);
            if (temp.matches(chinese)) {
            } else {
                isChinese = false;
            }
        }
        return isChinese;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        int editStart = mEditText.getSelectionStart();
        int editEnd = mEditText.getSelectionEnd();
        mEditText.removeTextChangedListener(this);
        if (!TextUtils.isEmpty(editable)) {
            while (chineseLength(editable.toString()) > mMaxLen) {
                editable.delete(editStart - 1, editEnd);
                editStart--;
                editEnd--;
            }
        }
        int currentLen = mMaxLen - chineseLength(editable.toString());
        if (mOnInputCounterChangeListener != null) {
            String counterText = mOnInputCounterChangeListener.onCounterChange(mMaxLen, currentLen);
            mTvCounter.setText(counterText == null ? "" : counterText);
        } else {
            mTvCounter.setText(String.valueOf(currentLen));
        }

        mEditText.setSelection(editStart);
        mEditText.addTextChangedListener(this);
    }
}
