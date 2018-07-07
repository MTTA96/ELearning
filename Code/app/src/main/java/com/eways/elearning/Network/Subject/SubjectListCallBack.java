package com.eways.elearning.Network.Subject;

import com.eways.elearning.Model.Subject.Subject;

import java.util.ArrayList;

/**
 * Created by zzzzz on 7/7/2018.
 */

public interface SubjectListCallBack {

    void subjectListCallBack(int errorCode, ArrayList<Subject> subjects);

}
