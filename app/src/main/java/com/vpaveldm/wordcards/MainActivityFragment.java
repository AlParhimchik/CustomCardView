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

import com.vpaveldm.wordcards.adapter.BaseAdapter;
import com.vpaveldm.wordcards.adapter.ExampleWordAdapter;
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
        List<Word> words=new ArrayList<>();
        Word word=new Word("hello","[helo]","privet");
        words.add(word);
        word=new Word("bye","bai","poka");
        words.add(word);
        BaseAdapter<Word> adapter=new ExampleWordAdapter(words,getContext());
        cardView.setAdapter(adapter);

        return view;
    }

}