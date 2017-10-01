package com.eways.elearning;

/**
 * Created by Quang Tri on 30/08/2017.
 */

public class TestFireBase_Comment {
    String UserComment;
    String NoiDungComment;

    public TestFireBase_Comment() {
    }

    public TestFireBase_Comment(String userComment, String noiDungComment) {
        UserComment = userComment;
        NoiDungComment = noiDungComment;
    }

    public String getUserComment() {
        return UserComment;
    }

    public void setUserComment(String userComment) {
        UserComment = userComment;
    }

    public String getNoiDungComment() {
        return NoiDungComment;
    }

    public void setNoiDungComment(String noiDungComment) {
        NoiDungComment = noiDungComment;
    }
}
