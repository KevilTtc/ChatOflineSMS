package com.example.demochatsms16.viewchat.viewmodelchat;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.demochatsms16.database.DataBaseMsg;
import com.example.demochatsms16.model.ObDataMess;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class GetListChatRepository {
    private static DataBaseMsg dataBaseMsg;
    private static MutableLiveData<List<ObDataMess>> listMutableLiveData;

    @NotNull
    public static final MutableLiveData getList(@NotNull String number, @NotNull Context context) {
        dataBaseMsg = new DataBaseMsg(context);
        listMutableLiveData = dataBaseMsg.setDataNumberSMSList(number);
        return listMutableLiveData;
    }

}

