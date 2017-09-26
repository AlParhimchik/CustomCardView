package com.vpaveldm.wordcards;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment {

    private Card cardView;
    private Button okButton;
    private Button xButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        cardView = (Card) view.findViewById(R.id.card_view);

        List<String> englishWords = new ArrayList<>();
        List<String> transcriptionWords = new ArrayList<>();
        List<String> russianWords = new ArrayList<>();

        englishWords.add("Mother");
        englishWords.add("Father");
        englishWords.add("Sister");
        englishWords.add("Brother");
        englishWords.add("Cousin");

        transcriptionWords.add("Transcription");
        transcriptionWords.add("Transcription");
        transcriptionWords.add("Transcription");
        transcriptionWords.add("Transcription");
        transcriptionWords.add("Transcription");

        russianWords.add("Мама");
        russianWords.add("Отец");
        russianWords.add("Сестра");
        russianWords.add("Брат");
        russianWords.add("2я сестра");

        cardView.init(englishWords, transcriptionWords, russianWords);

        return view;
    }
}