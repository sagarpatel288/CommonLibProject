package com.library.android.common.baseconstants;

/**
 * Created by srdpatel on 2/25/2018. Contains static final strings of application.
 * <p>
 * All strings have been prepended by STR (Short form of string) to ease the access of variables with the help of Android Studio auto suggest tools
 * <p>
 *
 * @since 1.0
 */
public final class BaseConstants {
    // Note: 11/27/2018 by sagar  Default null value to be compared
    public static final int NULL = (int) -1L;

    // Note: 11/27/2018 by sagar  App tag to be used in logs
    public static final String TAG = " :commonLog: ";

    // Note: 11/27/2018 by sagar  Error message to print or show while trying to use reflection for typeface utility
    public static final String STR_MSG_ERROR_TYPEFACE_REFLECTION
            = "Use getInstance() method to get single instance of this class";

    // Note: 11/27/2018 by sagar  Delay in millisecond to perform continuous touch event for quantity modification
    public static final long DELAY = 100;

    public static final String STR_SHARED_PREF = "sharedPreferences";
    public static final String STR_MSG_ERROR_SHARED_PREF_REFLECTION
            = "Use getInstance() method to get single instance of this class. " +
            "\nUse getSharedPref(Context mContext) method to get the single SharedPreferences instance of this class. " +
            "\nUse getEditor(Context mContext) method to get the single SharedPreferences.Editor instance of this class. ";

    public static final String STR_PACKAGE = "package";
    //App Directory
    public static final String STR_DIR_APP_NAME = "Design Villa";
    public static final String STR_DIR_APP = "/Design Villa";
    public static final String STR_DIR_APP_IMAGES = "/Dv Images";

    public static final class ItemViewType {
        public static final int ITEM_VIEW_TYPE = 1;
        public static final int PROGRESS_BAR_CIRCULAR_LOADING = 2;
    }
}
