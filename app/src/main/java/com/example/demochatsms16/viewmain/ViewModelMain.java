package com.example.demochatsms16.viewmain;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModel;

import com.example.demochatsms16.Interface.EventResoultDelete;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ViewModelMain extends ViewModel {


    @RequiresApi(Build.VERSION_CODES.N)
    public final void getListMSG(@NotNull Context context, @NotNull EventListMsg EventListMsg) {
        EventListMsg.getlistMsg(GetListMsgRepository.getList(context));
    }

    public final void delete(@NotNull Context context, @NotNull List list, @NotNull EventResoultDelete eventResoultDelete) {
        eventResoultDelete.getResultDelete(GetListMsgRepository.delList(context, list));
    }
}