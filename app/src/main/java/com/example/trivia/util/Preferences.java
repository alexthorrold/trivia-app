package com.example.trivia.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    public static final String CURRENT_QUESTION_INDEX = "current_question_index";
    public static final String SCORE = "score";
    public static final String QUESTIONS_ANSWERED = "questions_answered";

    private final SharedPreferences preferences;

    public Preferences(Activity context) {
        this.preferences = context.getPreferences(Context.MODE_PRIVATE);
    }

    public void saveCurrentQuestionIndex(int currentQuestionIndex) {
        preferences.edit().putInt(CURRENT_QUESTION_INDEX, currentQuestionIndex).apply();
    }

    public void saveScore(int score) {
        preferences.edit().putInt(SCORE, score).apply();
    }

    public void saveQuestionsAnswered(int questionsAnswered) {
        preferences.edit().putInt(QUESTIONS_ANSWERED, questionsAnswered).apply();
    }

    public int getCurrentQuestionIndex() {
        return preferences.getInt(CURRENT_QUESTION_INDEX, 0);
    }

    public int getScore() {
        return preferences.getInt(SCORE, 0);
    }

    public int getQuestionsAnswered() {
        return preferences.getInt(QUESTIONS_ANSWERED, 0);
    }

    public void clearData() {
        preferences.edit().clear().apply();
    }
}
