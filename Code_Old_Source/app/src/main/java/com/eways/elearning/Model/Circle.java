package com.eways.elearning.Model;

/**
 * Created by ADMIN on 5/5/2018.
 */

public class Circle {
    int active;
    int notActive;

    public Circle(int active, int notActive) {
        this.active = active;
        this.notActive = notActive;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getNotActive() {
        return notActive;
    }

    public void setNotActive(int notActive) {
        this.notActive = notActive;
    }
}
