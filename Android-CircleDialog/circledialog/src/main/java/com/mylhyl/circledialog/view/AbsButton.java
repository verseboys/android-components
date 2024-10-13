package com.mylhyl.circledialog.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mylhyl.circledialog.internal.CircleParams;
import com.mylhyl.circledialog.internal.Controller;
import com.mylhyl.circledialog.internal.CountDownTimer;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.mylhyl.circledialog.view.listener.ButtonView;
import com.mylhyl.circledialog.view.listener.CountDownTimerObserver;
import com.mylhyl.circledialog.view.listener.OnCreateButtonListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 对话框确定按钮与取消的视图
 * Created by hupei on 2017/3/30.
 */
abstract class AbsButton extends LinearLayout implements ButtonView {

    private final List<CountDownTimerObserver> mCountDownTimerObservers = new ArrayList<>();
    protected ButtonParams mNegativeParams;
    protected ButtonParams mPositiveParams;
    protected ButtonParams mNeutralParams;
    private DialogParams mDialogParams;
    private OnCreateButtonListener mOnCreateButtonListener;
    private TextView mNegativeButton;
    private TextView mPositiveButton;
    private TextView mNeutralButton;
    private CountDownTimer mCountDownTimer;

    public AbsButton(Context context, CircleParams circleParams) {
        super(context);
        init(circleParams);
    }

    public void addCountDownTimerObserver(CountDownTimerObserver observer) {
        if (observer == null || mCountDownTimerObservers.contains(observer)) {
            return;
        }
        this.mCountDownTimerObservers.add(observer);
    }

    @Override
    public final void regNegativeListener(OnClickListener onClickListener) {
        if (mNegativeButton != null) {
            mNegativeButton.setOnClickListener(onClickListener);
        }
    }

    @Override
    public final void regPositiveListener(OnClickListener onClickListener) {
        if (mPositiveButton != null) {
            timerRestart();
            mPositiveButton.setOnClickListener(onClickListener);
        }
    }

    @Override
    public final void regNeutralListener(OnClickListener onClickListener) {
        if (mNeutralButton != null) {
            mNeutralButton.setOnClickListener(onClickListener);
        }
    }

    @Override
    public final void refreshText() {
        if (mNegativeParams != null && mNegativeButton != null) {
            handleNegativeStyle();
        }

        if (mPositiveParams != null && mPositiveButton != null) {
            handlePositiveStyle();
        }

        if (mNeutralParams != null && mNeutralButton != null) {
            handleNeutralStyle();
        }
    }

    @Override
    public final View getView() {
        return this;
    }

    @Override
    public final boolean isEmpty() {
        return mNegativeParams == null && mPositiveParams == null && mNeutralParams == null;
    }

    @Override
    public void timerRestart() {
        if (mCountDownTimer != null) {
            mCountDownTimer.restart();
        }
    }

    @Override
    public void timerCancel() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
    }

    protected abstract void initView();

    protected abstract void setNegativeButtonBackground(View view, int backgroundColor, CircleParams circleParams);

    protected abstract void setNeutralButtonBackground(View view, int backgroundColor, CircleParams circleParams);

    protected abstract void setPositiveButtonBackground(View view, int backgroundColor, CircleParams circleParams);

    private void init(CircleParams circleParams) {
        mDialogParams = circleParams.dialogParams;
        mNegativeParams = circleParams.negativeParams;
        mPositiveParams = circleParams.positiveParams;
        mNeutralParams = circleParams.neutralParams;
        mOnCreateButtonListener = circleParams.circleListeners.createButtonListener;

        addCountDownTimerObserver(circleParams.circleListeners.countDownTimerObserver);

        initView();

        if (mNegativeParams != null) {
            //取消按钮
            createNegative();
            //如果取消按钮没有背景色，则使用默认色
            int backgroundColor = mNegativeParams.backgroundColor != 0
                    ? mNegativeParams.backgroundColor : mDialogParams.backgroundColor;
            setNegativeButtonBackground(mNegativeButton, backgroundColor, circleParams);
        }

        if (mNeutralParams != null) {
            if (mNegativeButton != null) {
                //分隔线 当且仅当前面有按钮这个按钮不为空的时候才需要添加分割线
                createDivider();
            }
            createNeutral();
            //如果取消按钮没有背景色，则使用默认色
            int backgroundColor = mNeutralParams.backgroundColor != 0
                    ? mNeutralParams.backgroundColor : mDialogParams.backgroundColor;
            setNeutralButtonBackground(mNeutralButton, backgroundColor, circleParams);
        }

        if (mPositiveParams != null) {
            if (mNeutralButton != null || mNegativeButton != null) {
                //分隔线 当且仅当前面有按钮这个按钮不为空的时候才需要添加分割线
                createDivider();
            }
            //确定按钮
            createPositive();
            //如果取消按钮没有背景色，则使用默认色
            int backgroundColor = mPositiveParams.backgroundColor != 0
                    ? mPositiveParams.backgroundColor : mDialogParams.backgroundColor;
            setPositiveButtonBackground(mPositiveButton, backgroundColor, circleParams);
        }

        if (mOnCreateButtonListener != null) {
            mOnCreateButtonListener.onCreateButton(mNegativeButton, mPositiveButton, mNeutralButton);
        }
    }

    private void createNegative() {
        mNegativeButton = new TextView(getContext());
        mNegativeButton.setId(android.R.id.button1);
        mNegativeButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        handleNegativeStyle();
        addView(mNegativeButton);
    }

    private void createDivider() {
        DividerView dividerView = new DividerView(getContext());
        addView(dividerView);
    }

    private void createNeutral() {
        mNeutralButton = new TextView(getContext());
        mNeutralButton.setId(android.R.id.button2);
        mNeutralButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        handleNeutralStyle();
        addView(mNeutralButton);
    }

    private void createPositive() {
        mPositiveButton = new TextView(getContext());
        mPositiveButton.setId(android.R.id.button3);
        mPositiveButton.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        // add: 2021/1/21 hupei since 5.3.6 倒计时
        handlerCountDownTimer();
        handlePositiveStyle();
        addView(mPositiveButton);
    }

    private void handlerCountDownTimer() {
        if (mPositiveParams.countDownTime <= 0 || mPositiveParams.countDownInterval <= 0) {
            return;
        }
        mCountDownTimer = new CountDownTimer(mPositiveParams.countDownTime, mPositiveParams.countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                mPositiveParams.disable = true;
                handlePositiveEnabled();
                String textFormat = mPositiveParams.countDownText;
                if (TextUtils.isEmpty(textFormat)) {
                    textFormat = mPositiveParams.text.concat(ButtonParams.COUNT_DOWN_TEXT_FORMAT);
                }
                mPositiveButton.setText(String.format(textFormat, (millisUntilFinished / 1000) + 1));
                for (CountDownTimerObserver observer : mCountDownTimerObservers) {
                    observer.onTimerTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                mPositiveParams.disable = false;
                handlePositiveEnabled();
                mPositiveButton.setText(mPositiveParams.text);
                for (CountDownTimerObserver observer : mCountDownTimerObservers) {
                    observer.onTimerFinish();
                }
            }
        }.start();
    }

    private void handleNegativeStyle() {
        if (mDialogParams.typeface != null) {
            mNegativeButton.setTypeface(mDialogParams.typeface);
        }
        mNegativeButton.setGravity(Gravity.CENTER);
        mNegativeButton.setText(mNegativeParams.text);
        mNegativeButton.setEnabled(!mNegativeParams.disable);
        mNegativeButton.setTextColor(mNegativeParams.disable ?
                mNegativeParams.textColorDisable : mNegativeParams.textColor);
        mNegativeButton.setTextSize(mNegativeParams.textSize);
        mNegativeButton.setHeight(Controller.dp2px(getContext(), mNegativeParams.height));
        mNegativeButton.setTypeface(mNegativeButton.getTypeface(), mNegativeParams.styleText);
    }

    private void handleNeutralStyle() {
        if (mDialogParams.typeface != null) {
            mNeutralButton.setTypeface(mDialogParams.typeface);
        }
        mNeutralButton.setGravity(Gravity.CENTER);
        mNeutralButton.setText(mNeutralParams.text);
        mNeutralButton.setEnabled(!mNeutralParams.disable);
        mNeutralButton.setTextColor(mNeutralParams.disable ?
                mNeutralParams.textColorDisable : mNeutralParams.textColor);
        mNeutralButton.setTextSize(mNeutralParams.textSize);
        mNeutralButton.setHeight(Controller.dp2px(getContext(), mNeutralParams.height));
        mNeutralButton.setTypeface(mNeutralButton.getTypeface(), mNeutralParams.styleText);
    }

    private void handlePositiveStyle() {
        if (mDialogParams.typeface != null) {
            mPositiveButton.setTypeface(mDialogParams.typeface);
        }
        mPositiveButton.setGravity(Gravity.CENTER);
        mPositiveButton.setText(mPositiveParams.text);
        handlePositiveEnabled();
        mPositiveButton.setTextSize(mPositiveParams.textSize);
        mPositiveButton.setHeight(Controller.dp2px(getContext(), mPositiveParams.height));
        mPositiveButton.setTypeface(mPositiveButton.getTypeface(), mPositiveParams.styleText);
    }

    private void handlePositiveEnabled() {
        mPositiveButton.setEnabled(!mPositiveParams.disable);
        mPositiveButton.setTextColor(mPositiveParams.disable ?
                mPositiveParams.textColorDisable : mPositiveParams.textColor);
    }
}
