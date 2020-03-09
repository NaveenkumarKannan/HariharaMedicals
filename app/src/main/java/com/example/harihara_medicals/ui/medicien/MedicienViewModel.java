package com.example.harihara_medicals.ui.medicien;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MedicienViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mText;

    public MedicienViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Medical Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
