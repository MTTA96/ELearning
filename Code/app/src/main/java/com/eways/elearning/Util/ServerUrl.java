package com.eways.elearning.Util;

/**
 * Created by zzzzz on 10/4/2017.
 *
 * Description: Lưu các link api của server.
 */

public class ServerUrl {
    //Link server
    public static final String ServerUrl = "https://ewayslearn.000webhostapp.com";

    /** User */
    public static final String LOGIN_URL = ServerUrl + "/API/User/Student/AddNewStudent.php";
    //Url api
    public static final String ApiHelloWorld = ServerUrl + "/helloWorld";
    public static final String ApiGetKhoaHocGS = "https://us-central1-elearning-da847.cloudfunctions.net/GetListAllTimGiaSu";
    public static final String ApiGetKhoaHocHV = "https://us-central1-elearning-da847.cloudfunctions.net/GetListAllTimHocVien";

    //Url điều khoản
    public static final String UrlDieuKhoanSuDung = "https://firebasestorage.googleapis.com/v0/b/elearning-da847.appspot.com/o/DieuKhoan%2FDieukhoansudung.pdf?alt=media&token=24d30565-f28f-4d2a-b1ba-4c703a664e05";
    public static final String UrlDieuKhoanGiaSu = "https://drive.google.com/open?id=1wy1Vt56RBwYqB8eoKVrkHtQ8WllkrZ_l";
}
