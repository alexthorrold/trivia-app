package com.example.trivia.data;

import android.util.Log;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.trivia.controller.AppController;
import com.example.trivia.model.Question;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    ArrayList<Question> questionArrayList = new ArrayList<>();

    String url = "https://pastebin.com/raw/QRGzxxEy";

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, response -> {
            for (int i = 0; i < response.length(); i++) {

                try {

                    JSONObject jsonObject = response.getJSONObject(i);

                    String qstn = jsonObject.getString("question");
                    String optionA = jsonObject.getString("A");
                    String optionB = jsonObject.getString("B");
                    String optionC = jsonObject.getString("C");
                    String optionD = jsonObject.getString("D");
                    String answer = jsonObject.getString("answer");

                    Question question = new Question(qstn, optionA, optionB, optionC, optionD, answer);

                    questionArrayList.add(question);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if (callBack != null) {
                callBack.processFinished(questionArrayList);
            }

        }, error -> {
            Log.d("jsonObject", "Failed");
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;
    }
}
