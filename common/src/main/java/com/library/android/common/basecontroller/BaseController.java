package com.library.android.common.basecontroller;

import android.content.Context;
import android.content.Intent;

import com.library.android.common.listeners.Callbacks;
import com.library.android.common.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import okhttp3.Dispatcher;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class BaseController {

    private static Dispatcher mDispatcher = new Dispatcher();
    private Context mContext;
    private int mPageNumber = 1;
    private String mApi;
    private Intent mIntent;
    private boolean mIsLoadMore;
    private Callbacks.ApiResponseDetail mApiResponseDetail;

    public BaseController(Context mContext, String mApi, Intent mIntent, Callbacks.ApiResponseDetail mApiResponseDetail) {
        this.mContext = mContext;
        this.mApi = mApi;
        if (mIntent == null) {
            mIntent = new Intent();
        }
        this.mIntent = mIntent;
        setControllerIntent(mIntent, mApi);
        this.mApiResponseDetail = mApiResponseDetail;
    }

    private void setControllerIntent(Intent mIntent, String mApi) {
        if (mIntent == null) {
            mIntent = new Intent();
        }
        mIntent = Utils.setApi(mIntent, mApi);
        setControllerIntent(mIntent);
    }

    public boolean isLoadMore() {
        return mIsLoadMore;
    }

    public void setLoadMore(boolean loadMore) {
        mIsLoadMore = loadMore;
    }

    public <T> void callApi(Call<T> call, int pageNumber, boolean isLoadMore, boolean isSearchApi, boolean cancelAll) {
        setPageNumber(pageNumber);
        setLoadMore(isLoadMore);
        if (isSearchApi) {
            // comment by srdpatel: 11/11/2019 We may want to cancel any previous running call
            // comment by srdpatel: 11/11/2019 https://futurestud.io/tutorials/retrofit-2-cancel-requests
            call.cancel();
        }
        if (cancelAll) {
            mDispatcher.cancelAll();
        }
        if (!isLoadMore) {
            resetPageNumber();
        }
        // comment by srdpatel: 11/11/2019 Asynchronous call
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
                if (getApiResponseDetail() != null) {
                    getApiResponseDetail().onSuccess(response.body(), getPageNumber(), getControllerIntent());
                } else {
                    onSuccess(response.body(), getPageNumber(), getControllerIntent());
                }
                increasePageNumber();
            }

            @Override
            public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
                if (getApiResponseDetail() != null) {
                    if (call.isCanceled()) {
                        getApiResponseDetail().onCancel(getPageNumber(), getControllerIntent());
                    } else {
                        getApiResponseDetail().onFail(getPageNumber(), getControllerIntent());
                    }
                } else {
                    if (call.isCanceled()) {
                        onCancel(getPageNumber(), getControllerIntent());
                    } else {
                        onFail(getPageNumber(), getControllerIntent());
                    }
                }
            }
        });
    }

    private void resetPageNumber() {
        mPageNumber = 1;
    }

    public Callbacks.ApiResponseDetail getApiResponseDetail() {
        return mApiResponseDetail;
    }

    public void setApiResponseDetail(Callbacks.ApiResponseDetail apiResponseDetail) {
        this.mApiResponseDetail = apiResponseDetail;
    }

    public int getPageNumber() {
        return mPageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.mPageNumber = pageNumber;
    }

    public Intent getControllerIntent() {
        return mIntent;
    }

    public void setControllerIntent(Intent intent) {
        this.mIntent = intent;
    }

    public abstract void onSuccess(Object object, int pageNumber, Intent intent);

    private void increasePageNumber() {
        mPageNumber = mPageNumber + 1;
    }

    public abstract void onCancel(int pageNumber, Intent intent);

    public abstract void onFail(int pageNumber, Intent intent);

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public String getApi() {
        return mApi;
    }

    public void setApi(String api) {
        this.mApi = api;
    }

    public abstract void onNoDataAtAll(int pageNumber, Intent intent);

    public abstract void onNoMoreData(int pageNumber, Intent intent);

    public abstract void onEmptySearchResult(int pageNumber, Intent intent);

    public abstract void onGetData(Object object, List list, int pageNumber, Intent intent);
}
