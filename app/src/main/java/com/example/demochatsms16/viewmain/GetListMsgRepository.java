package com.example.demochatsms16.viewmain;

import android.content.Context;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.demochatsms16.database.DataBaseMsg;
import com.example.demochatsms16.model.ObDataMess;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class GetListMsgRepository {
    private static DataBaseMsg dataBase;
    private static MutableLiveData<List<ObDataMess>> list;

    @RequiresApi(24)
    @NotNull
    public static MutableLiveData getList(@NotNull Context context) {
        dataBase = new DataBaseMsg(context);
        DataBaseMsg dataBase = GetListMsgRepository.dataBase;

        MutableLiveData mutableLiveData = dataBase.getDataListChat();
        return mutableLiveData;
    }

    public static int delList(@NotNull Context context, @NotNull List list) {

        dataBase = new DataBaseMsg(context);
        int i = 0;

        for(int size = list.size(); i < size; ++i) {
            DataBaseMsg dataBase = GetListMsgRepository.dataBase;
            dataBase.deleteSms((String)list.get(i));
        }

        return 0;
    }

    private GetListMsgRepository() {
    }

}
