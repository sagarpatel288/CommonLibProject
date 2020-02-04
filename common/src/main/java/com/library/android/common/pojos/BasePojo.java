package com.library.android.common.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sagar on 7/31/2019 at 12:26 AM
 * <p>
 * Base pojo class which has selection field$
 * <p>
 * Being used in {@link }
 *
 * @see
 * @since 1.0$
 */
public class BasePojo implements Parcelable {

    private boolean isSelected;

    public BasePojo() {
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected BasePojo(Parcel in) {
        this.isSelected = in.readByte() != 0;
    }

    public static final Creator<BasePojo> CREATOR = new Creator<BasePojo>() {
        @Override
        public BasePojo createFromParcel(Parcel source) {
            return new BasePojo(source);
        }

        @Override
        public BasePojo[] newArray(int size) {
            return new BasePojo[size];
        }
    };
}
