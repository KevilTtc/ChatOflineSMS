package com.example.demochatsms16.model;

import org.jetbrains.annotations.NotNull;

public final class ObDataMess {
    private int id;
    @NotNull
    private String number;
    @NotNull
    private String smg;
    @NotNull
    private String time;
    private int isSent;

    public ObDataMess() {
    }

    public ObDataMess(int id, @NotNull String number, @NotNull String smg,
                      @NotNull String time, int isSent) {
        this.id = id;
        this.number = number;
        this.smg = smg;
        this.time = time;
        this.isSent = isSent;
    }


    public final int getId() {
        return this.id;
    }

    public final void setId(int var1) {
        this.id = var1;
    }

    @NotNull
    public final String getNumber() {
        return this.number;
    }

    public final void setNumber(@NotNull String var1) {
        this.number = var1;
    }

    @NotNull
    public final String getSmg() {
        return this.smg;
    }

    public final void setSmg(@NotNull String var1) {
        this.smg = var1;
    }

    @NotNull
    public final String getTime() {
        return this.time;
    }

    public final void setTime(@NotNull String var1) {
        this.time = var1;
    }

    public final int isSent() {
        return this.isSent;
    }

    public final void setSent(int var1) {
        this.isSent = var1;
    }
}
