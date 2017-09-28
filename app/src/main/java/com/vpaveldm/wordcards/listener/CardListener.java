package com.vpaveldm.wordcards.listener;

/**
 * Created by sashok on 28.9.17.
 */

public interface CardListener<T> {
    public void onSwipedLeft();
    public void onSwipedRight();
    public void SwipeRight();
    public void SwipeLeft();
    public void onCardClicked(T cur_item);
}
