package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.example.trivia.data.Repository;
import com.example.trivia.databinding.ActivityMainBinding;
import com.example.trivia.model.Question;
import com.example.trivia.util.Preferences;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int currentQuestionIndex;
    private String currentAnswer;
    List<Question> questionList;
    private int score;
    private Preferences preferences;
    private int questionsAnswered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        preferences = new Preferences(this);

        currentQuestionIndex = preferences.getCurrentQuestionIndex();
        score = preferences.getScore();
        questionsAnswered = preferences.getQuestionsAnswered();

        questionList = new Repository().getQuestions(questionArrayList -> {
            MainActivity.this.updateQuestion();
            MainActivity.this.updateScore();
        });

        binding.buttonOptionA.setOnClickListener(view -> answerButtonPress("A"));
        binding.buttonOptionB.setOnClickListener(view -> answerButtonPress("B"));
        binding.buttonOptionC.setOnClickListener(view -> answerButtonPress("C"));
        binding.buttonOptionD.setOnClickListener(view -> answerButtonPress("D"));

        binding.buttonReset.setOnClickListener(view -> clearData());

        binding.buttonPrev.setOnClickListener(view -> {
            if (currentQuestionIndex > 0) {
                currentQuestionIndex--;
            }
            else {
                currentQuestionIndex = questionList.size() - 1;
            }

            updateQuestion();
            preferences.saveCurrentQuestionIndex(currentQuestionIndex);
        });

        binding.buttonNext.setOnClickListener(view -> {
            currentQuestionIndex = (currentQuestionIndex + 1) % (questionList.size());

            updateQuestion();
            preferences.saveCurrentQuestionIndex(currentQuestionIndex);
        });
    }

    private void updateQuestion() {
        Question q = questionList.get(currentQuestionIndex);

        String questionContent = q.getQuestion() +
                "\n\nA: " + q.getOptionA() +
                "\nB: " + q.getOptionB() +
                "\nC: " + q.getOptionC() +
                "\nD: " + q.getOptionD();

        String questionNumberText = String.format(getString(R.string.out_of_text),
                (currentQuestionIndex + 1), questionList.size());

        currentAnswer = q.getAnswer();

        binding.textViewContent.setText(questionContent);
        binding.textViewQuestionNumber.setText(questionNumberText);
    }

    private void answerButtonPress(String ans) {
        int snackMessageId;

        if (ans.equals(currentAnswer)) {
            Log.d("Answer", "TRUE");
            snackMessageId = R.string.correct_answer;
            score++;
            preferences.saveScore(score);
        }
        else {
            Log.d("Answer", "FALSE");
            snackMessageId = R.string.incorrect_answer;
        }

        questionsAnswered++;
        preferences.saveQuestionsAnswered(questionsAnswered);

        updateScore();

        final Snackbar snackBar = Snackbar.make(binding.cardView,
                snackMessageId, Snackbar.LENGTH_SHORT);

        snackBar.setAction("Dismiss", view -> snackBar.dismiss());
        snackBar.show();
    }

    private void updateScore() {
        String newScore = String.format(getString(R.string.score_text),
                score, questionsAnswered);

        binding.textViewScore.setText(newScore);
    }

    private void clearData() {
        currentQuestionIndex = 0;
        score = 0;
        questionsAnswered = 0;

        preferences.clearData();

        updateQuestion();
        updateScore();
    }
}