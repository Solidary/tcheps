package com.tcheps.restful.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.tcheps.TsApplication;
import com.tcheps.models.Problem;
import com.tcheps.restful.adapters.ProblemsRestAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by mael-fosso on 9/11/15.
 */
public class ProblemsListTask extends AsyncTask<Void, Void, ArrayList<Problem>> {

    String authToken;

    public ProblemsListTask(String token) {
        this.authToken = token;
    }

    @Override
    protected ArrayList<Problem> doInBackground(Void... voids) {
        ProblemsRestAdapter problemsRestAdapter = new ProblemsRestAdapter(authToken);
        ArrayList<Problem> problems = new ArrayList<Problem>(); //Collections.emptyList();

        try {
            problems = problemsRestAdapter.list();
            Log.d("ProblemsListTask", "Problems Rest Adapter --- List --- " + problems.size());
        } catch (Exception e) {
            Log.e("ProblemsListTask", "Problems Rest Adapter --- List --- Error --- " + e.getMessage());
        }

        return problems;
    }

    @Override
    protected void onPostExecute(ArrayList<Problem> problems) {
        super.onPostExecute(problems);

        TsApplication.getTsEventBus().post(problems);
    }
}
