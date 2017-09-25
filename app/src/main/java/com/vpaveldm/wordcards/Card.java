package com.vpaveldm.wordcards;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;


public class Card extends CardView implements View.OnTouchListener {

    private Animation fadeout_animation;
    private Animation shrink_animation;
    private Animation grow_animation;
    private Context mContext;

    private final static float RIGHT_DISABLE_COEFFICIENT = 3f / 5;
    private final static float LEFT_DISABLE_COEFFICIENT = 2f / 5;
    private final static float NOT_MOVE_VALUE = 100f;
    private Card cardView;
    private float xCurrent;
    private float yCurrent;
    private float xStartCard;
    private float yStartCard;
    private Point sizeWindow;

    public Card(Context context, AttributeSet attrs) {
        super(context, attrs);

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
                break;
            case MotionEvent.ACTION_UP:
                if (motionEvent.getEventTime() - motionEvent.getDownTime() < NOT_MOVE_VALUE
                        && motionEvent.getEventTime() - motionEvent.getDownTime() < NOT_MOVE_VALUE) {
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

    private class CardListener implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            cardView.setOnTouchListener(null);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            cardView.setOnTouchListener(Card.this);
            if (animation.equals(shrink_animation))
                cardView.startAnimation(grow_animation);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
