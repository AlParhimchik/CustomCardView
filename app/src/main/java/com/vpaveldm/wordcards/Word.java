package com.vpaveldm.wordcards;

/**
 * Created by sashok on 28.9.17.
 */
public class Word {
    private String englishWord;
    private String russianWord;
    private String transcription;

    Word(String englishWord, String transcription, String russianWord) {
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
