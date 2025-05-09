package com.pubscale.demo.android.yt

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.UUID

//This is an helper class which will create an user id and persist it until the app data is cleared or the app is reinstalled
class User(private val context: Context) {

    private val sharePreferences: SharedPreferences =
        context.getSharedPreferences("ps_demo", Context.MODE_PRIVATE)

    //This method can be used to get an random auto generated user id which is persisted until the app data is cleared.
    //Use this only if your app does not have a user id.
    fun getUserId(): String {
        var userId = sharePreferences.getString(USER_ID, "")
        if (userId.isNullOrEmpty()) {
            userId = UUID.randomUUID().toString()
            sharePreferences.edit() { putString(USER_ID, userId) }
        }
        return userId
    }

    companion object {
        const val USER_ID = "user_id"
    }
}