package com.m1p9.kidz.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();

        mText.setValue("KIDZ Version 1.0. \n" +
                " KIDZ est une plateforme d'éducation pour enfant à partir de vidéo séléctionné par nos professionnels. ");
    }

    public LiveData<String> getText() {
        return mText;
    }
}