package com.eways.elearning.Model;

/**
 * Created by ADMIN on 5/15/2018.
 */

public class PhoneCode {

    private String image;
    private String country;

    public PhoneCode(String image, String country) {
        this.image = image;
        this.country = country;
    }

    public String getImage() {
        return image;
    }

    public String getCountry() {
        return country;
    }
}
