package com.skysearch.itm.skysearch.view.main.presenter;


import android.content.Context;


/**
 * Created by tae-hwan on 12/22/16.
 */

public interface MainContract {

    interface View {

        void showToast(String title);
    }

    interface Presenter {

        void attachView(View view);

        //void setImageAdapterModel(ImageAdapterContract.Model adapterModel);

        // void setImageAdapterView(ImageAdapterContract.View adapterView);

        void detachView();

        // void setSampleImageData(SampleImageRepository sampleImageData);

        void loadItems(Context context, boolean isClear);
    }
}
