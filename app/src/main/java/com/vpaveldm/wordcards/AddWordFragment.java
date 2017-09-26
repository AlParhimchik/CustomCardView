package com.vpaveldm.wordcards;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.vpaveldm.wordcards.database.DBHelper;

/**
 * Created by User on 26.09.2017.
 */

public class AddWordFragment extends DialogFragment {

    private EditText englishWordEditText;
    private EditText russianWordEditText;
    private EditText transcriptionWordEditText;

    public interface AddWordDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    AddWordDialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (AddWordDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement AddWordListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DBHelper helper = new DBHelper(getActivity());
        final SQLiteDatabase database = helper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_word, null);
        englishWordEditText = (EditText) view.findViewById(R.id.english_edit_text);
        transcriptionWordEditText = (EditText) view.findViewById(R.id.transcription_edit_text);
        russianWordEditText = (EditText) view.findViewById(R.id.russian_edit_text);

        builder.setView(view)
                .setTitle(getString(R.string.word))
                .setPositiveButton(getString(R.string.add_word), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        contentValues.put(DBHelper.KEY_ENGLISH_WORD, englishWordEditText.getText().toString());
                        contentValues.put(DBHelper.KEY_RUSSIAN_WORD, russianWordEditText.getText().toString());
                        contentValues.put(DBHelper.KEY_TRANSCRIPTION, transcriptionWordEditText.getText().toString());
                        database.insert(DBHelper.DATABASE_WORDS, null, contentValues);
                        mListener.onDialogPositiveClick(AddWordFragment.this);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mListener.onDialogNegativeClick(AddWordFragment.this);
                    }
                });
        return builder.create();
    }
}
