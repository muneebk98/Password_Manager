package com.example.assignment3;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDB extends SQLiteOpenHelper {
    private static final String Database_name = "User";
    private static final int Database_ver = 1;
    private static final String User_Table_name = "User_detail";
    private static final String Passwords_Table_name = "Passwords";
    private static final String KEY_id = "id";
    private static final String KEY_username = "username";
    private static final String KEY_password = "password";
    private static final String KEY_user_id = "user_id";
    private static final String KEY_site_name = "site_name";
    private static final String KEY_site_url = "site_url";
    private static final String KEY_login = "login";
    private static final String KEY_site_password = "site_password";
    private static final String RecycleBin_Table_name = "Recycle_bin";

    public MyDB(Context context) {
        super(context, Database_name, null, Database_ver);
    }


    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + User_Table_name + "("
                + KEY_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_username + " TEXT NOT NULL, "
                + KEY_password + " TEXT NOT NULL);";

        String CREATE_PASSWORDS_TABLE = "CREATE TABLE " + Passwords_Table_name + "("
                + KEY_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_user_id + " INTEGER, "
                + KEY_site_name + " TEXT, "
                + KEY_site_url + " TEXT, "
                + KEY_login + " TEXT, "
                + KEY_site_password + " TEXT, "
                + "FOREIGN KEY(" + KEY_user_id + ") REFERENCES " + User_Table_name + "(" + KEY_id + "));";

        String CREATE_RECYCLE_BIN_TABLE = "CREATE TABLE " + RecycleBin_Table_name + "("
                + KEY_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_user_id + " INTEGER, "
                + KEY_site_name + " TEXT, "
                + KEY_site_url + " TEXT, "
                + KEY_login + " TEXT, "
                + KEY_site_password + " TEXT, "
                + "deleted_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                + "FOREIGN KEY(" + KEY_user_id + ") REFERENCES " + User_Table_name + "(" + KEY_id + "));";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PASSWORDS_TABLE);
        db.execSQL(CREATE_RECYCLE_BIN_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + User_Table_name);
        db.execSQL("DROP TABLE IF EXISTS " + Passwords_Table_name);
        db.execSQL("DROP TABLE IF EXISTS " + RecycleBin_Table_name);
        onCreate(db);
    }


    public void addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_username, username);
        values.put(KEY_password, password);
        db.insert(User_Table_name, null, values);
        db.close();
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + User_Table_name + " WHERE " + KEY_username + "=? AND " + KEY_password + "=?", new String[]{username, password});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return result;
    }

    public void addPassword(int userId, String siteName, String siteUrl, String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_user_id, userId);
        values.put(KEY_site_name, siteName);
        values.put(KEY_site_url, siteUrl);
        values.put(KEY_login, login);
        values.put(KEY_site_password, password);
        db.insert(Passwords_Table_name, null, values);
        db.close();
    }

    public List<Password> getPasswords(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Passwords_Table_name + " WHERE " + KEY_user_id + "=?", new String[]{String.valueOf(userId)});
        List<Password> passwordList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(KEY_id);
                int siteNameIndex = cursor.getColumnIndex(KEY_site_name);
                int siteUrlIndex = cursor.getColumnIndex(KEY_site_url);
                int loginIndex = cursor.getColumnIndex(KEY_login);
                int sitePasswordIndex = cursor.getColumnIndex(KEY_site_password);

                if (idIndex != -1 && siteNameIndex != -1 && siteUrlIndex != -1 && loginIndex != -1 && sitePasswordIndex != -1) {
                    @SuppressLint("Range") int id = cursor.getInt(idIndex);
                    @SuppressLint("Range") String siteName = cursor.getString(siteNameIndex);
                    @SuppressLint("Range") String siteUrl = cursor.getString(siteUrlIndex);
                    @SuppressLint("Range") String login = cursor.getString(loginIndex);
                    @SuppressLint("Range") String sitePassword = cursor.getString(sitePasswordIndex);
                    passwordList.add(new Password(id, siteName, siteUrl, login, sitePassword));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return passwordList;
    }




    @SuppressLint("Range")
    public int getUserId(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + User_Table_name + " WHERE " + KEY_username + "=? AND " + KEY_password + "=?", new String[]{username, password});
        int userId = -1;
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(KEY_id));
        }
        cursor.close();
        db.close();
        return userId;
    }

    @SuppressLint("Range")
    public void deletePassword(int id) {
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.query(Passwords_Table_name, new String[]{KEY_user_id, KEY_site_name, KEY_site_url, KEY_login, KEY_site_password}, KEY_id + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(KEY_user_id, cursor.getInt(cursor.getColumnIndex(KEY_user_id)));
            values.put(KEY_site_name, cursor.getString(cursor.getColumnIndex(KEY_site_name)));
            values.put(KEY_site_url, cursor.getString(cursor.getColumnIndex(KEY_site_url)));
            values.put(KEY_login, cursor.getString(cursor.getColumnIndex(KEY_login)));
            values.put(KEY_site_password, cursor.getString(cursor.getColumnIndex(KEY_site_password)));


            db.insert(RecycleBin_Table_name, null, values);
            cursor.close();
        }


        db.delete(Passwords_Table_name, KEY_id + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void updatePassword(int id, String siteName, String siteUrl, String login, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_site_name, siteName);
        values.put(KEY_site_url, siteUrl);
        values.put(KEY_login, login);
        values.put(KEY_site_password, password);

        db.update(Passwords_Table_name, values, KEY_id + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<RecycleBinItem> getRecycleBinItems() {
        List<RecycleBinItem> recycleBinItems = new ArrayList<>();
        SQLiteDatabase db= this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + RecycleBin_Table_name, null);
        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(KEY_id);
                int userIdIndex = cursor.getColumnIndex(KEY_user_id);
                int siteNameIndex = cursor.getColumnIndex(KEY_site_name);
                int siteUrlIndex = cursor.getColumnIndex(KEY_site_url);
                int loginIndex = cursor.getColumnIndex(KEY_login);
                int sitePasswordIndex = cursor.getColumnIndex(KEY_site_password);
                int deletedTimeIndex = cursor.getColumnIndex("deleted_time");

                if (idIndex != -1 && userIdIndex != -1 && siteNameIndex != -1 && siteUrlIndex != -1 && loginIndex != -1 && sitePasswordIndex != -1 && deletedTimeIndex != -1) {
                    @SuppressLint("Range") int id = cursor.getInt(idIndex);
                    @SuppressLint("Range") int userId = cursor.getInt(userIdIndex);
                    @SuppressLint("Range") String siteName = cursor.getString(siteNameIndex);
                    @SuppressLint("Range") String siteUrl = cursor.getString(siteUrlIndex);
                    @SuppressLint("Range") String login = cursor.getString(loginIndex);
                    @SuppressLint("Range") String sitePassword = cursor.getString(sitePasswordIndex);
                    @SuppressLint("Range") String deletedTime = cursor.getString(deletedTimeIndex);
                    recycleBinItems.add(new RecycleBinItem(id, userId, siteName, siteUrl, login, sitePassword, deletedTime));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recycleBinItems;
    }
    @SuppressLint("Range")
    public void restorePassword(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(RecycleBin_Table_name, new String[]{KEY_user_id, KEY_site_name, KEY_site_url, KEY_login, KEY_site_password}, KEY_id + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(KEY_user_id, cursor.getInt(cursor.getColumnIndex(KEY_user_id)));
            values.put(KEY_site_name, cursor.getString(cursor.getColumnIndex(KEY_site_name)));
            values.put(KEY_site_url, cursor.getString(cursor.getColumnIndex(KEY_site_url)));
            values.put(KEY_login, cursor.getString(cursor.getColumnIndex(KEY_login)));
            values.put(KEY_site_password, cursor.getString(cursor.getColumnIndex(KEY_site_password)));

            db.insert(Passwords_Table_name, null, values);
            db.delete(RecycleBin_Table_name, KEY_id + " = ?", new String[]{String.valueOf(id)});
            cursor.close();



        }

        db.close();
    }

}
