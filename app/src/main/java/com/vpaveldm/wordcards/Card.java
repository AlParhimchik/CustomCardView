package com.vpaveldm.wordcards;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.vpaveldm.wordcards.database.DBHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Card extends CardView implements View.OnTouchListener {

    private Animation fadeout_animation;
    private Animation shrink_animation;
    private Animation grow_animation;
    private Context mContext;

    private final static float RIGHT_DISABLE_COEFFICIENT = 3f / 5;
    private final static float LEFT_DISABLE_COEFFICIENT = 2f / 5;
    private final static float NOT_MOVE_TIME_VALUE = 100f;
    private final static float NOT_MOVE_DISTANCE_VALUE = 1f;
    private Card cardView;
    private float xCurrent;
    private float yCurrent;
    private float xStartCard;
    private float yStartCard;
    private Point sizeWindow;
    private TextView wordTextView;
    private TextView transcriptionTextView;
    private List<Word> mWords;
    private Word currentWord;
    private boolean isEnglish = true;

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);

        mWords = new ArrayList<>();

        mContext = context;
        cardView = this;

        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        sizeWindow = new Point();
        display.getSize(sizeWindow);

        cardView.setOnTouchListener(this);

        CardListener listener = new CardListener();
        fadeout_animation = AnimationUtils.loadAnimation(mContext, R.anim.fadeout_word_card_animation);
        fadeout_animation.setAnimationListener(listener);
        shrink_animation = AnimationUtils.loadAnimation(mContext, R.anim.shrink_word_card_animation);
        shrink_animation.setAnimationListener(listener);
        grow_animation = AnimationUtils.loadAnimation(mContext, R.anim.grow_word_card_animation);
        grow_animation.setAnimationListener(listener);
    }

    public void init() {
        DBHelper DBHelper = new DBHelper(mContext);
        SQLiteDatabase database = DBHelper.getWritableDatabase();


        if (mWords.size() == 1)
            mWords.remove(new Word("", "", ""));

        Cursor cursor = database.query(DBHelper.DATABASE_WORDS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int enIndex = cursor.getColumnIndex(DBHelper.KEY_ENGLISH_WORD);
            int ruIndex = cursor.getColumnIndex(DBHelper.KEY_RUSSIAN_WORD);
            int trIndex = cursor.getColumnIndex(DBHelper.KEY_TRANSCRIPTION);
            do {
                mWords.add(new Word(cursor.getString(enIndex), cursor.getString(trIndex), cursor.getString(ruIndex)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        wordTextView = (TextView) cardView.findViewById(R.id.word_text_view);
        transcriptionTextView = (TextView) cardView.findViewById(R.id.transcription_text_view);
        if (mWords.size() == 0)
            mWords.add(new Word("", "", ""));
        currentWord = mWords.get(new Random().nextInt(mWords.size()));
        setEnglishWord(currentWord);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xStartCard = cardView.getX();
                yStartCard = cardView.getY();
                xCurrent = motionEvent.getX();
                yCurrent = motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                cardView.setX(cardView.getX() + (motionEvent.getX() - xCurrent));
                cardView.setY(cardView.getY() + (motionEvent.getY() - yCurrent));
//                Toast.makeText(getContext(), String.valueOf(motionEvent.getX()), Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_UP:
                if ((motionEvent.getEventTime() - motionEvent.getDownTime() < NOT_MOVE_TIME_VALUE
                        && motionEvent.getEventTime() - motionEvent.getDownTime() < NOT_MOVE_TIME_VALUE)
                        || (Math.abs(xStartCard - cardView.getX()) < NOT_MOVE_DISTANCE_VALUE &&
                        Math.abs(yStartCard - cardView.getY()) < NOT_MOVE_DISTANCE_VALUE)) {
                    cardView.startAnimation(shrink_animation);
                } else if ((cardView.getX() + cardView.getWidth()) < RIGHT_DISABLE_COEFFICIENT * sizeWindow.x ||
                        cardView.getX() > LEFT_DISABLE_COEFFICIENT * sizeWindow.x) {
                    cardView.startAnimation(fadeout_animation);
                }
                cardView.setX(xStartCard);
                cardView.setY(yStartCard);
                break;
        }
        return true;
    }

    private void setRussianWord(Word word) {
        wordTextView.setText(word.getRussianWord());
        transcriptionTextView.setText("");
    }

    private void setEnglishWord(Word word) {
        wordTextView.setText(word.getEnglishWord());
        transcriptionTextView.setText(word.getTranscription());
    }

    private class CardListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
//            cardView.setOnTouchListener(null);
            if (animation.equals(fadeout_animation)) {
                Random random = new Random();
                int index = Math.abs(random.nextInt(mWords.size()));
                currentWord = mWords.get(index);
                setEnglishWord(currentWord);
            }
        }

        @Override
        public void onAnimationEnd(Animation animation) {
//            cardView.setOnTouchListener(Card.this);
            if (animation.equals(shrink_animation)) {
                if (isEnglish) {
                    setRussianWord(currentWord);
                    isEnglish = false;
                } else {
                    setEnglishWord(currentWord);
                    isEnglish = true;
                }
                cardView.startAnimation(grow_animation);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

    private class Word {
        private String englishWord;
        private String russianWord;
        private String transcription;

        private Word(String englishWord, String transcription, String russianWord) {
            this.englishWord = englishWord;
            this.russianWord = russianWord;
            this.transcription = transcription;
        }

        public String getEnglishWord() {
            return englishWord;
        }

        public String getRussianWord() {
            return russianWord;
        }

        public String getTranscription() {
            return transcription;
        }

        @Override
        public boolean equals(Object obj) {
            Word word = (Word) obj;
            if (word.englishWord.equals(this.englishWord) &&
                    word.russianWord.equals(this.russianWord) &&
                    word.transcription.equals(this.transcription))
                return true;
            else return false;
        }
    }
}