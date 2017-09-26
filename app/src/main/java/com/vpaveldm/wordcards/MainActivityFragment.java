package com.vpaveldm.wordcards;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.vpaveldm.wordcards.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment  {

    private Card cardView;
    private DBHelper mDBHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        cardView = (Card) view.findViewById(R.id.card_view);

        List<String> englishWords = new ArrayList<>();
        List<String> transcriptionWords = new ArrayList<>();
        List<String> russianWords = new ArrayList<>();



        cardView.init();

        return view;
    }

    public void init(){
        cardView.init();
    }
}