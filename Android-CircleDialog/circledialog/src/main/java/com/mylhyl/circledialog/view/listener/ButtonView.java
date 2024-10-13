package com.mylhyl.circledialog.view.listener;

import android.view.View;

/**
 * Created by hupei on 2018/5/21.
 */

public interface ButtonView {

    void regNegativeListener(View.OnClickListener onClickListener);

    void regPositiveListener(View.OnClickListener onClickListener);

    void regNeutralListener(View.OnClickListener onClickListener);

    void refreshText();

    View getView();

    boolean isEmpty();

    /**
     * 重启倒计时
     *
     * @since 5.3.6
     */
    void timerRestart();

    /**
     * 取消倒计时
     *
     * @since 5.3.6
     */
    void timerCancel();
}
