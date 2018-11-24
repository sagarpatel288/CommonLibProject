package com.library.android.common.data;

import android.provider.BaseColumns;

// Note: 11/23/2018 by sagar  Inspiration:
// store: https://gist.github.com/udacityandroid/ae83549fb0599bbdbb25ac179415b83c
// pets: https://github.com/udacity/ud845-Pets/blob/631efa27c1/app/src/main/java/com/example/android/pets/data/PetContract.java
// sunshine: https://gist.github.com/udacityandroid/7450345062a5aa7371e6c30dab785ce7
// calendar: https://android.googlesource.com/platform/frameworks/base/+/refs/heads/master/core/java/android/provider/CalendarContract.java#883
public final class PetContract implements BaseColumns {

    private PetContract() {
    }

    public static final class PetEntry implements BaseColumns {

        public static final String TABLE_NAME = "pets";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PET_NAME = "name";
        public static final String COLUMN_PET_BREED = "breed";
        public static final String COLUMN_PET_GENDER = "gender";
        public static final String COLUMN_PET_WEIGHT = "weight";
        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        private PetEntry() {
        }
    }
}
