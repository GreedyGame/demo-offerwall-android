package com.pubscale.demo.android.yt

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import java.util.UUID

class User(private val context: Context) {

    private val sharePreferences: SharedPreferences =
        context.getSharedPreferences("ps_demo", Context.MODE_PRIVATE)

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