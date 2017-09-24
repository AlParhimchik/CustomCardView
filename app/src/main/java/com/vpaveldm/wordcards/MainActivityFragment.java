package com.vpaveldm.wordcards;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

public class MainActivityFragment extends Fragment implements View.OnTouchListener, View.OnClickListener {

    private final static float NOT_MOVE_VALUE = 1f;
    private final static float FADEOUT_WORD_CARD = 50;
    private CardView cardView;
    private float xCurrent;
    private float yCurrent;
    private float xStart;
    private float yStart;
    private float xLast;
    private float yLast;
    private float xMoved;
    private float yMoved;
    private boolean isOnScreen = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        cardView = (CardView) view.findViewById(R.id.card_view);

        cardView.setOnClickListener(this);
        cardView.setOnTouchListener(this);

        xStart = cardView.getX();
        yStart = cardView.getY();


        return view;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xCurrent = motionEvent.getX();
                yCurrent = motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                xLast = motionEvent.getX();
                yLast = motionEvent.getY();
                cardView.setX(cardView.getX() + (motionEvent.getX() - xCurrent));
                cardView.setY(cardView.getY() + (motionEvent.getY() - yCurrent));
                break;
            case MotionEvent.ACTION_UP:
                xMoved = Math.abs(xLast - xCurrent);
                yMoved = Math.abs(yLast - yCurrent);
                if (xMoved < NOT_MOVE_VALUE && yMoved < NOT_MOVE_VALUE) {
                    Animation shrink = AnimationUtils.loadAnimation(getActivity(), R.anim.shrink_word_card_animation);
                    final Animation grow = AnimationUtils.loadAnimation(getActivity(), R.anim.grow_word_card_animation);

                    Animation.AnimationListener listener = new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            cardView.startAnimation(grow);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    };

                    shrink.setAnimationListener(listener);
                    cardView.startAnimation(shrink);
                }
                xMoved = xLast - xCurrent;
                yMoved = yLast - yCurrent;
                if ((xMoved > FADEOUT_WORD_CARD || xMoved < -FADEOUT_WORD_CARD) ||
                        (yMoved > FADEOUT_WORD_CARD || yMoved <-FADEOUT_WORD_CARD)) {
                    Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout_word_card_animation);
                    cardView.startAnimation(animation);
                }
                cardView.setX(xStart);
                cardView.setY(yStart);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {

    }
}
