package com.vpaveldm.wordcards.adapter;

import com.vpaveldm.wordcards.listener.BaseCardAdapterListener;

import java.util.Random;

/**
 * Created by sashok on 28.9.17.
 */

public abstract class BaseAdapter<T>  implements BaseCardAdapterListener<T> {

    public T getRandomItem(){
        int position=new Random().nextInt(getItemCount());
        return getItem(position);
    }

}
