package com.system.bhouse.utils;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018-08-25.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.utils
 */

public class ParcelHelper {

    public static <T> T copy(Parcelable input) {
        Parcel parcel = null;

        try {
            parcel = Parcel.obtain();
            parcel.writeParcelable(input, 0);

            parcel.setDataPosition(0);
            return parcel.readParcelable(input.getClass().getClassLoader());
        } finally {
            parcel.recycle();
        }
    }

}
