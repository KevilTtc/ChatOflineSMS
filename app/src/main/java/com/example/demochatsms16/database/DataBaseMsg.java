package com.example.demochatsms16.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;


import com.example.demochatsms16.model.ObDataMess;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;

public class DataBaseMsg extends SQLiteOpenHelper {

    private static String database_name = "MSG";
    private Context context;
    private SQLiteDatabase sqLiteDatabase;


    public String Chats = "Chats";
    private String number = "number";
    private String msg = "msg";
    private String time = "time";
    private String isSent = "isSent";

    public DataBaseMsg(Context context) {
        super(context, database_name, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + Chats + "(" + "id" +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + number +
                " TEXT," + msg + " TEXT," + time + " TEXT," +
                isSent + " TEXT" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Chats);
    }

    public void insertSMG(ObDataMess obDataMess) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(number, obDataMess.getNumber());
        contentValues.put(msg, obDataMess.getSmg());
        contentValues.put(time, obDataMess.getTime());
        contentValues.put(isSent, String.valueOf(obDataMess.isSent()));
        sqLiteDatabase.insert(Chats, null, contentValues);
    }

    public MutableLiveData<List<ObDataMess>> setDataNumberSMSList(String number) {
        MutableLiveData<List<ObDataMess>> dataMessList1 = new MutableLiveData<>();
        List<ObDataMess> obDataMessList = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT * FROM " + Chats + " WHERE number = \"" + number + "\"";

        try {
            cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst();
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        ObDataMess obDataMess = new ObDataMess(0, "", "", "", 0);
                        obDataMess.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                        obDataMess.setNumber(cursor.getString(cursor.getColumnIndex("number")));
                        obDataMess.setSmg(cursor.getString(cursor.getColumnIndex(msg)));
                        obDataMess.setTime(cursor.getString(cursor.getColumnIndex(time)));
                        obDataMess.setTime(cursor.getString(cursor.getColumnIndex(time)));
                        obDataMess.setSent(Integer.parseInt(cursor.getString(cursor.getColumnIndex(isSent))));

                        obDataMessList.add(obDataMess);

                        cursor.moveToNext();
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        dataMessList1.setValue(obDataMessList);
        return dataMessList1;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public MutableLiveData<List<ObDataMess>> getDataListChat() {
        MutableLiveData<List<ObDataMess>> dataMessList1 = new MutableLiveData<>();
        List<ObDataMess> obDataMessList = new ArrayList<>();
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = null;
        String query = "SELECT * FROM " + Chats;

        try {
            cursor = sqLiteDatabase.rawQuery(query, null);
            cursor.moveToFirst();
            if (cursor != null) {
                if (cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) {
                        ObDataMess obDataMess = new ObDataMess(0, "", "", "", 0);
                        obDataMess.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                        obDataMess.setNumber(cursor.getString(cursor.getColumnIndex("number")));
                        obDataMess.setSmg(cursor.getString(cursor.getColumnIndex(msg)));
                        obDataMess.setTime(cursor.getString(cursor.getColumnIndex(time)));
                        obDataMess.setSent(Integer.parseInt(cursor.getString(cursor.getColumnIndex(isSent))));
                        cursor.moveToNext();
                        obDataMessList.add(obDataMess);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        Collections.reverse(obDataMessList);

        List<ObDataMess> employeeList = new ArrayList<>();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            employeeList = obDataMessList.stream()
                    .reduce(new ArrayList<ObDataMess>(), new BiFunction<List<ObDataMess>, ObDataMess, List<ObDataMess>>() {
                        @Override
                        public List<ObDataMess> apply(List<ObDataMess> accumulator, final ObDataMess employee) {
                            if (accumulator.stream().noneMatch(new Predicate<ObDataMess>() {
                                @Override
                                public boolean test(ObDataMess emp) {

                                    if (emp != null && employee != null){
                                        if (emp.getNumber() != null && employee.getNumber() != null){
                                            if (emp.getNumber().equals(employee.getNumber())){
                                                return true;
                                            }
                                        }

                                    }
                                    return false;
                                }
                            })) {
                                accumulator.add(employee);
                            }
                            return accumulator;
                        }
                    }, new BinaryOperator<List<ObDataMess>>() {
                        @Override
                        public List<ObDataMess> apply(List<ObDataMess> acc1, List<ObDataMess> acc2) {
                            acc1.addAll(acc2);
                            return acc1;
                        }
                    });
        } else {
            Log.e("error", "error");
        }


        dataMessList1.setValue(employeeList);
        return dataMessList1;
    }


    public void deleteSms(String id) {
        sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(Chats, number + " = ?",
                new String[]{String.valueOf(id)});
        sqLiteDatabase.close();

    }
}
