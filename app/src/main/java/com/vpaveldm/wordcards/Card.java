package com.vpaveldm.wordcards;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.vpaveldm.wordcards.adapter.BaseAdapter;
import com.vpaveldm.wordcards.helper.AnimatorHelper;
import com.vpaveldm.wordcards.listener.CardListener;


public class Card extends CardView implements View.OnTouchListener , CardListener<Word> {

    private Context mContext;

    private final static float RIGHT_DISABLE_COEFFICIENT = 3f / 5;
    private final static float LEFT_DISABLE_COEFFICIENT = 2f / 5;
    private final static float NOT_MOVE_TIME_VALUE = 100f;
    private final static float NOT_MOVE_DISTANCE_VALUE = 1f;

    private float xCurrent;
    private float yCurrent;
    private float xStartCard;
    private float yStartCard;
    private Point sizeWindow;

    private Card cardView;

    private TextView wordTextView;
    private TextView transcriptionTextView;

    private boolean isEnglish = true;
    private boolean DefaultLang = true; //if english is default lang
    private Word current_word;
    private BaseAdapter<Word> adapter;
    private int backgroundColor;
    private int primaryColor;
    private int back_color;

    public BaseAdapter<Word> getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseAdapter<Word> adapter) {
        this.adapter = adapter;
        initViews();
        setupAttrs();
        current_word = adapter.getRandomItem();
        showWordOnCard(current_word);
    }

    private void setupAttrs() {
        wordTextView.setTextColor(primaryColor);
        cardView.setCardBackgroundColor(backgroundColor);
    }

    private void initViews() {
        wordTextView = (TextView) cardView.findViewById(R.id.word_text_view);
        transcriptionTextView = (TextView) cardView.findViewById(R.id.transcription_text_view);
    }

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Card,
                0, 0);
        try {
            backgroundColor = a.getColor(R.styleable.Card_card_background, 0);
            primaryColor = a.getColor(R.styleable.Card_primary_color, 0);
            back_color = a.getColor(R.styleable.Card_back_color, 0);

        } finally {
            a.recycle();
        }
        mContext = context;
        cardView = this;
        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        sizeWindow = new Point();
        display.getSize(sizeWindow);
        cardView.setOnTouchListener(this);
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
//                    cardView.startAnimation(shrink_animation);// when touch on card
                    onCardClicked(current_word);
                } else if ((cardView.getX() + cardView.getWidth()) < RIGHT_DISABLE_COEFFICIENT * sizeWindow.x ||
                        cardView.getX() > LEFT_DISABLE_COEFFICIENT * sizeWindow.x) {
                    //cardView.startAnimation(fadeout_animation);// when swipe card
                    onSwiped(); //
                }
                cardView.setX(xStartCard);
                cardView.setY(yStartCard);
                break;
        }
        return true;
    }

    public void onSwiped() {
        if (DefaultLang) {
            isEnglish = true;
        } else {
            isEnglish = false;
        }
        AnimatorHelper.changeView(mContext, cardView);
        current_word = adapter.getRandomItem();
        showWordOnCard(current_word);
    }

    public void setEnglishWord(Word word) {
        wordTextView.setText(word.getEnglishWord());
        transcriptionTextView.setText("[" + word.getTranscription() + "]");
    }

    public void setRussianWord(Word word) {
        wordTextView.setText(word.getRussianWord());
        transcriptionTextView.setText("");
    }

    public void showWordOnCard(Word word) {
        if (DefaultLang) setEnglishWord(word);
        else
            setRussianWord(word);
    }

    @Override
    public void onSwipedLeft() {

    }

    @Override
    public void onSwipedRight() {
        
    }

    @Override
    public void SwipeRight() {

    }

    @Override
    public void SwipeLeft() {

    }

    @Override
    public void onCardClicked(Word cur_item) {
        AnimatorHelper.flipView(mContext, cardView, isEnglish, current_word);
        isEnglish = isEnglish ? false : true;

    }
}