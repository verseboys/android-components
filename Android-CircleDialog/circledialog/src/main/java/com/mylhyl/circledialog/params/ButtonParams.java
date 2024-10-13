package com.mylhyl.circledialog.params;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;

import com.mylhyl.circledialog.res.values.CircleColor;
import com.mylhyl.circledialog.res.values.CircleDimen;

/**
 * 按钮参数
 * Created by hupei on 2017/3/30.
 */
public class ButtonParams implements Parcelable {
    public static final String COUNT_DOWN_TEXT_FORMAT = "(%d)秒";
    public static final Creator<ButtonParams> CREATOR = new Creator<ButtonParams>() {
        @Override
        public ButtonParams createFromParcel(Parcel source) {
            return new ButtonParams(source);
        }

        @Override
        public ButtonParams[] newArray(int size) {
            return new ButtonParams[size];
        }
    };
    /**
     * 按钮框与顶部距离 dp
     */
    public int topMargin;
    /**
     * 按钮文本颜色
     */
    public int textColor = CircleColor.FOOTER_BUTTON_TEXT_POSITIVE;
    /**
     * 按钮文本大小 sp
     */
    public int textSize = CircleDimen.FOOTER_BUTTON_TEXT_SIZE;
    /**
     * 按钮高度 dp
     */
    public int height = CircleDimen.FOOTER_BUTTON_HEIGHT;
    /**
     * 按钮背景颜色
     */
    public int backgroundColor;
    /**
     * 按钮文本
     */
    public String text;

    /**
     * 是否禁用按钮，true禁用
     */
    public boolean disable;

    /**
     * 禁用后的按钮文本颜色
     */
    public int textColorDisable = CircleColor.FOOTER_BUTTON_DISABLE;

    /**
     * 按下颜色值
     */
    public int backgroundColorPress;
    /**
     * 字样式
     * {@linkplain Typeface#NORMAL NORMAL}
     * {@linkplain Typeface#BOLD BOLD}
     * {@linkplain Typeface#ITALIC ITALIC}
     * {@linkplain Typeface#BOLD_ITALIC BOLD_ITALIC}
     */
    public int styleText = Typeface.NORMAL;

    /**
     * 倒计时时长，毫秒，如果有效则默认禁用按钮 {@link #disable}
     */
    public long countDownTime;

    /**
     * 倒计时间隔，毫秒
     */
    public long countDownInterval;

    /**
     * 倒计时显示文本，也就是按钮的文本，支持占位符
     */
    public String countDownText;

    public ButtonParams() {
    }

    protected ButtonParams(Parcel in) {
        this.topMargin = in.readInt();
        this.textColor = in.readInt();
        this.textSize = in.readInt();
        this.height = in.readInt();
        this.backgroundColor = in.readInt();
        this.text = in.readString();
        this.disable = in.readByte() != 0;
        this.textColorDisable = in.readInt();
        this.backgroundColorPress = in.readInt();
        this.styleText = in.readInt();
        this.countDownTime = in.readLong();
        this.countDownInterval = in.readLong();
        this.countDownText = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.topMargin);
        dest.writeInt(this.textColor);
        dest.writeInt(this.textSize);
        dest.writeInt(this.height);
        dest.writeInt(this.backgroundColor);
        dest.writeString(this.text);
        dest.writeByte(this.disable ? (byte) 1 : (byte) 0);
        dest.writeInt(this.textColorDisable);
        dest.writeInt(this.backgroundColorPress);
        dest.writeInt(this.styleText);
        dest.writeLong(this.countDownTime);
        dest.writeLong(this.countDownInterval);
        dest.writeString(this.countDownText);
    }
}
