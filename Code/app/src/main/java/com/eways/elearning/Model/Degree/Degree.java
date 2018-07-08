package com.eways.elearning.Model.Degree;

import java.util.ArrayList;

/**
 * Created by zzzzz on 7/8/2018.
 */

public class Degree {

    public static Degree instance = null;

    private ArrayList degreeList = new ArrayList();

    public static Degree getInstance() {

        if (instance == null) {
            instance = new Degree();
        }

        return instance;
    }

    public Degree() {
        this.degreeList.add("Other");
        this.degreeList.add("Cao đẳng");
        this.degreeList.add("Đại học");
        this.degreeList.add("Thạc sĩ");
        this.degreeList.add("Tiến sĩ");
    }

    public ArrayList getDegreeList() {
        return degreeList;
    }

    /** GET DEGREE ID BY NAME */
    public String getDegreeId(String degreeName) {

        switch (degreeName) {
            case "Other":
                return "0";
            case "Cao đẳng":
                return "1";
            case "Đại học":
                return "2";
            case "Thạc sĩ":
                return "3";
            case "Tiến sĩ":
                return "4";
            case "Kỹ sư":
                return "5";
            default:
                return "-1";
        }

    }

    /** Get degree name by id */

    public String getDegreeById(String degreeId) {

        switch (degreeId) {
            case "0":
                return "Other";
            case "1":
                return "Cao đẳng";
            case "2":
                return "Đại học";
            case "3":
                return "Thạc sĩ";
            case "4":
                return "Tiến sĩ";
            case "5":
                return "Kỹ sư";
            default:
                return "-1";
        }

    }
}
