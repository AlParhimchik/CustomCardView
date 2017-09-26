package com.vpaveldm.wordcards.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "wordDb";
    public static final String DATABASE_WORDS = "words";

    public static final String KEY_ENGLISH_WORD = "english_word";
    public static final String KEY_ID = "_id";
    public static final String KEY_RUSSIAN_WORD = "russian_word";
    public static final String KEY_TRANSCRIPTION = "transcription";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DATABASE_WORDS + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
        KEY_ENGLISH_WORD + " TEXT," + KEY_RUSSIAN_WORD + " TEXT," + KEY_TRANSCRIPTION + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table " + DATABASE_WORDS);
        onCreate(sqLiteDatabase);
    }
}
