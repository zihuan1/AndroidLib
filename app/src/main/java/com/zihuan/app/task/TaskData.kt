package com.zihuan.app.task

import io.reactivex.Observable

class TaskData<T> {

    var what = -1
    var data: T? = null
    var observable: Observable<T>? = null
}