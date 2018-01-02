package com.example.ai.jlam.pomdotai;

import android.content.Context;


/*
This class stores and retrieves the current's user info into the app's database.
*/
public class UserInfoDatabase
{
    Context context;
    InAppDatabase inAppDatabase_userInfo;

    /*
    first_time
    user_ID
    socero_ID
    email
    profile_image_URL
    first_name
    last_name
     */

    public UserInfoDatabase(Context c)
    {
        context = c;
        inAppDatabase_userInfo = new InAppDatabase(context, "ULnfJbcMdjZfoh8GbxMshJ");
    }


    public String getUser_ID()
    {
        return inAppDatabase_userInfo.getData("user_ID");
    }

    public void setUser_ID(String user_ID)
    {
        inAppDatabase_userInfo.storeData("user_ID", user_ID);
    }

    public String getSocero_ID()
    {
        return inAppDatabase_userInfo.getData("socero_ID");
    }

    public void setSocero_ID(String socero_ID)
    {
        inAppDatabase_userInfo.storeData("socero_ID", socero_ID);
    }

    public String getEmail()
    {
        return inAppDatabase_userInfo.getData("email");
    }

    public void setEmail(String email)
    {
        inAppDatabase_userInfo.storeData("email", email);
    }

    public String getFirst_name()
    {
        return inAppDatabase_userInfo.getData("first_name");
    }

    public void setFirst_name(String first_name)
    {
        inAppDatabase_userInfo.storeData("first_name", first_name);
    }

    public String getLast_name()
    {
        return inAppDatabase_userInfo.getData("last_name");
    }

    public void setLast_name(String last_name)
    {
        inAppDatabase_userInfo.storeData("last_name", last_name);
    }

    public void setProfileImageURL(String profileImageURL)
    {
        inAppDatabase_userInfo.storeData("profile_image_URL", profileImageURL);
    }

    public String getProfileImageURL()
    {
        return inAppDatabase_userInfo.getData("profile_image_URL");
    }


}