package com.example.apppizzeria2.ui.History;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel{

        private final MutableLiveData<String> mText;

        public HistoryViewModel() {
            mText = new MutableLiveData<>();
            mText.setValue("Aca esta el historial de tus pedidos");
        }

        public LiveData<String> getText() {
            return mText;
        }
}
