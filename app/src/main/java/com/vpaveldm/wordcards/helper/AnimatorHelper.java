package com.vpaveldm.wordcards.helper;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.vpaveldm.wordcards.Card;
import com.vpaveldm.wordcards.R;
import com.vpaveldm.wordcards.Word;

/**
 * Created by sashok on 28.9.17.
 */

public class AnimatorHelper {
    private static Animation fadeout_animation;
    private static Animation shrink_animation;
    private static Animation grow_animation;

    public static void flipView(Context context, final View animate_view, final boolean isEnglish, final Word word) {
        final TextView wordTextView = (TextView) animate_view.findViewById(R.id.word_text_view);
        final TextView transcriptionTextView = (TextView) animate_view.findViewById(R.id.transcription_text_view);
        shrink_animation = AnimationUtils.loadAnimation(context, R.anim.shrink_word_card_animation);
        grow_animation = AnimationUtils.loadAnimation(context, R.anim.grow_word_card_animation);
        shrink_animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isEnglish) {
                    wordTextView.setText(word.getRussianWord());
                    transcriptionTextView.setText("");
                } else {
                    wordTextView.setText(word.getEnglishWord());
                    transcriptionTextView.setText(word.getTranscription());
//
                }
                animate_view.startAnimation(grow_animation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animate_view.startAnimation(shrink_animation);
    }

    public static void changeView(Context mContext, Card cardView) {
        fadeout_animation = AnimationUtils.loadAnimation(mContext, R.anim.fadeout_word_card_animation);
        cardView.startAnimation(fadeout_animation);

    }
}
