package com.eways.elearning.Network;

/**
 * Created by zzzzz on 10/4/2017.
 *
 * Description: Lưu các link api của server.
 */

public class ServerUrl {
    //Link server
    public static final String ServerUrl = "https://ewayseducation.000webhostapp.com/";
    public static final String ServerAPIURL = ServerUrl + "api/";

    /** ACCOUNT */
    public static final String UserAPIRoot = ServerAPIURL + "user/";
    public static final String LOGIN_URL = UserAPIRoot + "student/studentlogin.php";
    public static final String SIGN_UP_URL = UserAPIRoot + "addnewuser.php";
    public static final String GET_USER_INFO_URL = UserAPIRoot + "getuserbyid.php";
    public static final String CHECK_PHONE_NUMBER_URL = UserAPIRoot + "isuserexisted.php";
    //public static final String ADD_USER_FAVORITE_URL = UserAPIRoot + "FavoriteSubject/AddListFavoriteSubject.php";
    //public static final String GET_USER_FAVORITE_SUBJECTS = ServerAPIURL + "FavoriteSubject/GetFavoriteSubjectByUid.php";
    public static final String SEND_REQUEST_URL = ServerAPIURL + "requisitioncourse/addrequisitioncourse.php";

    /** Tutors */
    public static final String TOP_TUTORS = ServerAPIURL + "home/toptutor.php";

    /** Subjects */
    public static final String GET_SUBJECT_LIST_URL = ServerAPIURL + "subject/getsubject.php";
    public static final String TRENDING_SUBJECTS = ServerAPIURL + "home/trendingsubject.php";

    /** Search */
    public static final String SEARCH_URL = ServerAPIURL + "search/appstudentsearchresults.php";
    public static final String SEARCH_TUTOR_SUGGESTIONS_URL = ServerAPIURL + "search/appstudentsearchsuggestion.php";

    /** Banner */
    public static final String BANNER_URL = ServerAPIURL + "banner/getbanners.php";

    /** Course */
    public static final String CREATE_COURSE_URL = ServerAPIURL + "Course/AddNewCourse.php";
    public static final String GET_COURSE_LIST_BY_SUBJECT_URL = ServerAPIURL + "Course/GetCoursesBySubject.php";
    public static final String GET_COURSE_BY_ID_URL = ServerAPIURL + "Course/GetCourseByIdCourse.php";

}
