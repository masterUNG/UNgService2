package app.ewtc.masterung.ungservice.utility;

/**
 * Created by masterung on 29/10/2017 AD.
 */

public class MyConstant {

    private String urlAddUserString = "http://androidthai.in.th/29oct/addDataMaster.php";
    private String urlGetUserString = "http://androidthai.in.th/29oct/getAllDataMaster.php";
    private String[] columeUserStrings = new String[]{"id", "Name", "User", "Password"};

    public String[] getColumeUserStrings() {
        return columeUserStrings;
    }

    public String getUrlGetUserString() {
        return urlGetUserString;
    }

    public String getUrlAddUserString() {
        return urlAddUserString;
    }
}   // Main Class
