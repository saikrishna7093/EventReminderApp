package eventreminder.by.s3302092saikrishnapannela

import android.content.Context


object EventReminderAppData {

    fun writeLS(context: Context, value: Boolean) {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putBoolean("LOGIN_STATUS", value).apply()
    }

    fun readLS(context: Context): Boolean {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        return userLogin.getBoolean("LOGIN_STATUS", false)
    }

    fun writeUserName(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("USERNAME", value).apply()
    }

    fun readUserName(context: Context): String {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        return userLogin.getString("USERNAME", "")!!
    }

    fun writeMail(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("USERMAIL", value).apply()
    }

    fun readMail(context: Context): String {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        return userLogin.getString("USERMAIL", "")!!
    }

    fun writeUserPhoto(context: Context, value: String) {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        val editor = userLogin.edit()
        editor.putString("USER_PHOTO", value).apply()
    }

    fun readUserPhoto(context: Context): String {
        val userLogin = context.getSharedPreferences("USER_DETAILS", Context.MODE_PRIVATE)
        return userLogin.getString("USER_PHOTO", "")!!
    }
}