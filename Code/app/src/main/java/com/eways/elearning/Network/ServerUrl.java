package com.eways.elearning.Network;

/**
 * Created by zzzzz on 10/4/2017.
 *
 * Description: Lưu các link api của server.
 */

public class ServerUrl {
    //Link server
    public static final String ServerUrl = "https://ewayseducation.000webhostapp.com/";
    public static final String ServerAPIURL = ServerUrl + "API/";

    /** User */
    public static final String UserAPIRoot = ServerAPIURL + "User/";
    public static final String LOGIN_URL = UserAPIRoot + "Tutor/TutorLogin.php";
    public static final String SIGN_UP_URL = UserAPIRoot + "AddNewUser.php";
    public static final String CHECK_PHONE_NUMBER_URL = UserAPIRoot + "UserIsExisted.php";

    /** Tutors */
    public static final String TOP_TUTORS = ServerAPIURL + "Home/TopTutor.php";

    /** Subjects */
    public static final String TRENDING_SUBJECTS = ServerAPIURL + "Home/TrendingSubject.php";
    public static final String USER_FAVORITE_SUBJECTS = ServerAPIURL + "Home/TopTutor.php";

    /** Search */
    public static final String SEARCH_URL = ServerAPIURL + "Search/AppStudentSearchResults.php";
    public static final String SEARCH_TUTOR_SUGGESTIONS_URL = ServerAPIURL + "Search/AppStudentSearchSuggestion.php";

    /** Banner */
    public static final String BANNER_URL = ServerAPIURL + "";


}
