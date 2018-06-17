package com.eways.elearning.Presenter;

import android.content.Context;

import com.eways.elearning.Interfaces.DataCallback.Subject.CourseCallBack;
import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.Utils.SupportKeys;

import java.util.ArrayList;

/**
 * Created by zzzzz on 6/17/2018.
 */

public class CourseListPresenter implements CourseCallBack {

    private Context context;
    private CourseCallBack courseCallBack;

    public CourseListPresenter(Context context) {
        this.context = context;
    }

    /** Get course list  */
    public void getCourseList(String subjectId, CourseCallBack courseCallBack) {

        this.courseCallBack = courseCallBack;
        Course.getCourseList(subjectId, this);

    }

    @Override
    public void courseCallBack(int errorCode, ArrayList result) {
        if (errorCode == SupportKeys.FAILED_CODE) {
            courseCallBack.courseCallBack(errorCode, null);
            return;
        }

        courseCallBack.courseCallBack(errorCode, result);
    }
}
