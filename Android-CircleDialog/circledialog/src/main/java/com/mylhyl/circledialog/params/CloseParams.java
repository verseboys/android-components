package com.mylhyl.circledialog.params;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by hupei on 2019/1/16 14:14.
 */
public class CloseParams implements Parcelable {

    /**
     * 关闭按钮在上左
     */
    public static final int CLOSE_TOP_LEFT = 351;

    /**
     * 关闭按钮在上右
     */
    public static final int CLOSE_TOP_RIGHT = 353;

    /**
     * 关闭按钮在上中
     */
    public static final int CLOSE_TOP_CENTER = 349;

    /**
     * 关闭按钮在下左
     */
    public static final int CLOSE_BOTTOM_LEFT = 783;

    /**
     * 关闭按钮在下右
     */
    public static final int CLOSE_BOTTOM_RIGHT = 785;

    /**
     * 关闭按钮在下中
     */
    public static final int CLOSE_BOTTOM_CENTER = 781;

    public static final Parcelable.Creator<CloseParams> CREATOR = new Parcelable.Creator<CloseParams>() {
        @Override
        public CloseParams createFromParcel(Parcel source) {
            return new CloseParams(source);
        }

        @Override
        public CloseParams[] newArray(int size) {
            return new CloseParams[size];
        }
    };
    public int closeResId;

    /**
     * 关闭图标的大小 dp
     */
    public int closeSize;

    /**
     * 关闭按钮的内间距 dp
     * int left, int top, int right, int bottom
     */
    public int[] closePadding;

    /**
     * 关闭按钮位置
     */
    public @CloseGravity
    int closeGravity = CLOSE_TOP_RIGHT;

    /**
     * 与边框的连接线宽度，默认0，只有大于0才显示 dp
     */
    public int connectorWidth;

    /**
     * 与边框的连接线高度 dp
     */
    public int connectorHeight;

    /**
     * 与边框的连接线颜色值 RGB
     */
    public int connectorColor = 0xFFFFFFFF;

    public CloseParams() {
    }

    protected CloseParams(Parcel in) {
        this.closeResId = in.readInt();
        this.closeSize = in.readInt();
        this.closePadding = in.createIntArray();
        this.closeGravity = in.readInt();
        this.connectorWidth = in.readInt();
        this.connectorHeight = in.readInt();
        this.connectorColor = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.closeResId);
        dest.writeInt(this.closeSize);
        dest.writeIntArray(this.closePadding);
        dest.writeInt(this.closeGravity);
        dest.writeInt(this.connectorWidth);
        dest.writeInt(this.connectorHeight);
        dest.writeInt(this.connectorColor);
    }

    @IntDef({CLOSE_TOP_LEFT, CLOSE_TOP_RIGHT, CLOSE_TOP_CENTER
            , CLOSE_BOTTOM_LEFT, CLOSE_BOTTOM_RIGHT, CLOSE_BOTTOM_CENTER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface CloseGravity {
    }
}
