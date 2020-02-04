package com.library.android.common.listeners;


import android.content.Intent;
import android.view.View;

import java.util.List;

public abstract class Callbacks {

    public interface OnFragmentLoad {
        void onFragmentVisible();

        void onFragmentHide();
    }

    public interface SimpleCallBack {
        void eventCallBack(Intent intent);
    }

    public interface ChildHostCommunication {
        void onChildHostCommunication(View view, Intent intent);
    }

    public interface RvAdapterCallback {
        void onRvAdapterCallback(View view, int position, Object object, List list, Intent intent, boolean isDeleteCallback, boolean isListUpdateCallback);
    }

    public interface NetworkConnectionListener {
        void onConnectionChanged(boolean isConnected);
    }

    public interface ApiResponse {
        void onSuccess(Object object, Intent intent);

        void onFail(Object object, Intent intent);
    }

    public interface ApiResponseDetail {
        void onSuccess(Object object, int pageNumber, Intent intent);

        void onCancel(int pageNumber, Intent intent);

        void onFail(int pageNumber, Intent intent);

        void onNoDataAtAll(int pageNumber, Intent intent);

        void onNoMoreData(int pageNumber, Intent intent);

        void onEmptySearchResult(int pageNumber, Intent intent);

        void onGetData(Object object, List list, int pageNumber, Intent intent);
    }
}
