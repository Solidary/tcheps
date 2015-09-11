package com.tcheps.restful.adapters;

import com.tcheps.models.Problem;
import com.tcheps.restful.TsServiceGenerator;
import com.tcheps.restful.api.ProblemsAPI;

import java.util.List;

/**
 * Created by mael-fosso on 9/11/15.
 */
public class ProblemsRestAdapter {
    public static final String TAG = "ProblemsRestAdapter";

    ProblemsAPI problemsAPI;

    public ProblemsRestAdapter() {
        problemsAPI = TsServiceGenerator.create(ProblemsAPI.class);
    }

    public List<Problem> list() {
        return problemsAPI.list();
    }
}
