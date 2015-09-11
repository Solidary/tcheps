package com.tcheps.restful.tasks;

import android.os.AsyncTask;

import com.tcheps.TsApplication;
import com.tcheps.models.Problem;
import com.tcheps.restful.adapters.ProblemsRestAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by mael-fosso on 9/11/15.
 */
public class ProblemsListTask extends AsyncTask<Void, Void, List<Problem>> {

    @Override
    protected List<Problem> doInBackground(Void... voids) {
        ProblemsRestAdapter problemsRestAdapter = new ProblemsRestAdapter();
        List<Problem> problems = Collections.emptyList();

        try {
            problems = problemsRestAdapter.list();
        } catch (Exception e) {

        }

        return problems;
    }

    @Override
    protected void onPostExecute(List<Problem> problems) {
        super.onPostExecute(problems);

        TsApplication.getTsEventBus().post(problems);
    }
}
