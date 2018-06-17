package com.eways.elearning.Model.Search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by zzzzz on 5/27/2018.
 */

public class SearchSuggestions {
    @SerializedName("IdSubject")
    @Expose
    private String idSubject;
    @SerializedName("IdField")
    @Expose
    private String idField;
    @SerializedName("SubjectName")
    @Expose
    private String subjectName;

    public String getIdSubject() {
        return idSubject;
    }

    public void setIdSubject(String idSubject) {
        this.idSubject = idSubject;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
