package com.example.harihara_medicals.ui.Book_Dr;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookdrViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public BookdrViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Under construction");
    }

    public LiveData<String> getText() {
        return mText;
    }
}