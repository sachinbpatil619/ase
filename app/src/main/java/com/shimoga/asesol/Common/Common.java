package com.shimoga.asesol.Common;

import com.shimoga.asesol.Model.User;
import com.shimoga.asesol.Remote.APIService;
import com.shimoga.asesol.Remote.RetrofitClient;

public class Common {
    public static User currentUser;

    private static final String BASE_URL="https://fcm.googleapis.com/";
    public static final String DELETE="Delete";
    public static final String USR_KEY="User";
    public static final String PWD_KEY="Password";


    public static APIService getFCMService()
    {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    private String phone;
    public Common() {
    }

    public Common(String phone) {
        this.phone = phone;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Common.currentUser = currentUser;
    }

    public static String convertCodeToStatus(String code) {
        if (code.equals("0"))
            return "Placed";
        else if (code.equals("1"))
            return "On the Way";
        else
            return "Shipped";
    }

}
