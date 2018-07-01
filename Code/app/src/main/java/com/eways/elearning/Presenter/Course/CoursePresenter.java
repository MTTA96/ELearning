package com.eways.elearning.Presenter.Course;

import android.content.Context;

import com.eways.elearning.Interfaces.DataCallback.Course.CourseDetailsCallBack;
import com.eways.elearning.Interfaces.DataCallback.Course.CourseListCallBack;
import com.eways.elearning.Model.Course.Course;
import com.eways.elearning.Utils.SupportKeys;

import java.util.ArrayList;

/**
 * Created by zzzzz on 7/1/2018.
 */

public class CoursePresenter implements CourseListCallBack, CourseDetailsCallBack {

    private Context context;
    private CourseListCallBack courseListCallBack;
    private CourseDetailsCallBack courseDetailsCallBack;

    public CoursePresenter(Context context) {
        this.context = context;
    }

    /** Get course list  */
    public void getCourseList(String subjectId, CourseListCallBack courseListCallBack) {

        this.courseListCallBack = courseListCallBack;
        Course.getCourseList(subjectId, this);

    }

    public void getCourseDetails(String courseId, CourseDetailsCallBack courseDetailsCallBack) {

        this.courseDetailsCallBack = courseDetailsCallBack;
        Course.getCourseDetails(courseId, this);

    }

    /** ----- HANDLE RESULTS ----- */

    @Override
    public void courseCallBack(int errorCode, ArrayList result) {

        if (errorCode == SupportKeys.FAILED_CODE) {
            courseListCallBack.courseCallBack(errorCode, null);
            return;
        }

        courseListCallBack.courseCallBack(errorCode, result);

    }

    @Override
    public void courseDetailsCallBack(int error, Course course) {

        if (error == SupportKeys.FAILED_CODE) {
            courseDetailsCallBack.courseDetailsCallBack(SupportKeys.FAILED_CODE, null);
        }

        courseDetailsCallBack.courseDetailsCallBack(SupportKeys.SUCCESS_CODE, course);

    }

}
