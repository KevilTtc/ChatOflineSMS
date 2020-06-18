package com.example.demochatsms16.viewchat.viewmodelchat;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.demochatsms16.model.ObDataMess;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ViewModelChatSMS extends ViewModel {
    @NotNull
    public  MutableLiveData<List<ObDataMess>> listMutableLiveData;

    public final void getChat(@NotNull String numberSMS, @NotNull Context context, @NotNull ListChat ListChat) {
        listMutableLiveData = (GetListChatRepository.getList(numberSMS, context));
        ListChat.listChat((LiveData) listMutableLiveData);
    }
}
