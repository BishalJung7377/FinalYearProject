//package com.bishaljung.vetementsfashionnepal.ui
//
//import android.content.Context
//import android.content.Intent
//import android.content.SharedPreferences
//
//
//class SessionManager(  // Context
//    var _context: Context
//) {
//
//    var pref: SharedPreferences
//
//    var editor: SharedPreferences.Editor
//
//
//    var PRIVATE_MODE = 0
//
//    fun createLoginSession(username: String?, password: String?) {
//
//        editor.putBoolean(IS_LOGIN, true)
//
//        editor.putString(KEY_username, username)
//
//        editor.putString(KEY_password, password)
//
//        editor.commit()
//    }
//
//    fun checkLogin() {
//
//        if (!isLoggedIn) {
//
//            val intent = Intent(_context, LoginActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//            _context.startActivity(intent)
//        }
//    }
//
//    fun logoutUser() {
//        editor.clear()
//        editor.commit()
//
//        val intent = Intent(_context, LoginActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        _context.startActivity(intent)
//    }
//
//    val isLoggedIn: Boolean
//        get() = pref.getBoolean(IS_LOGIN, false)
//
//    companion object {
//        private const val PREF_NAME = "MyPrefer"
//        private const val IS_LOGIN = "IsLoggedIn"
//        const val KEY_username = "username"
//        const val KEY_password = "password"
//    }
//
//    // Constructor
//    init {
//        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
//        editor = pref.edit()
//    }
//}