package com.zihuan.app


import com.zihuan.app.model.UserEntity
import com.zihuan.utils.cmhlibrary.savePreference

object UserManager {

    var userData = UserEntity()

    fun save(user: UserEntity) {
        userData = user
        userData.uid?.savePreference("uid")
        userData.token?.savePreference("token")
    }


}
