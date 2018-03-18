package com.eways.elearning.Data.DataModel.KhoaHoc;

/**
 * Created by Tien Phat on 02/11/2017.
 */

public class DiaDiem {
    private String DayDu;
    private String Quan;
    private String TP;

    public DiaDiem() {
    }

    public DiaDiem(String dayDu, String quan, String TP) {
        DayDu = dayDu;
        Quan = quan;
        this.TP = TP;
    }

    public String getDayDu() {
        return DayDu;
    }

    public void setDayDu(String DayDu) {
        this.DayDu = DayDu;
    }

    public String getQuan() {
        return Quan;
    }

    public void setQuan(String Quan) {
        this.Quan = Quan;
    }

    public String getTP() {
        return TP;
    }

    public void setTP(String TP) {
        this.TP = TP;
    }
}
