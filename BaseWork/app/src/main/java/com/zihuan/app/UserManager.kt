package com.zihuan.app


import com.zihuan.app.model.UserEntity
import com.zihuan.app.u.U

object UserManager  {

    var userData = UserEntity()

    fun save(user: UserEntity) {
        userData = user
        U.savePreferences("uid", userData.uid)
        U.savePreferences("token", userData.token)
    }



}
