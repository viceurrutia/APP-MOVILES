package com.example.gameforgamers
import android.content.Context
object Prefs {
 private const val PREFS="gfg_prefs"; private const val KEY_USERS="users"; private const val KEY_LOGGED="logged_email"
 private fun p(ctx:Context)=ctx.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
 fun ensureSeed(ctx:Context){ val u=p(ctx).getString(KEY_USERS,"")?:""; if(u.isEmpty()) p(ctx).edit().putString(KEY_USERS,"user1@mail.com|1234;user2@mail.com|abcd;").apply() }
 fun saveUser(ctx:Context, email:String, pass:String){ val u=p(ctx).getString(KEY_USERS,"")?:""; if(!u.contains("$email|")) p(ctx).edit().putString(KEY_USERS, u + "$email|$pass;").apply() }
 fun check(ctx:Context, email:String, pass:String):Boolean{ val u=p(ctx).getString(KEY_USERS,"")?:""; return u.split(";").filter{it.isNotBlank()}.any{ val s=it.split("|"); s.size==2 && s[0]==email && s[1]==pass } }
 fun setLogged(ctx:Context, email:String?){ p(ctx).edit().putString(KEY_LOGGED, email).apply() }
 fun getLogged(ctx:Context):String?=p(ctx).getString(KEY_LOGGED,null)
}
