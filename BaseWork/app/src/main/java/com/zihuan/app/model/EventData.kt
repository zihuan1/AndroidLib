package com.zihuan.app.model


class EventData {
    var what: Any? = null
    var data: Any? = null
    var data2: Any? = null
    var data3: Any? = null
    var data4: Any? = null

    constructor(what: Any) {
        this.what = what
    }

    constructor(what: Any, data: Any) {
        this.what = what
        this.data = data
    }

    constructor(what: Any, data: Any, data2: Any) {
        this.what = what
        this.data = data
        this.data2 = data2
    }

    constructor(what: Any, data: Any, data2: Any, data3: Any) {
        this.what = what
        this.data = data
        this.data2 = data2
        this.data3 = data3
    }

    constructor(what: Any, data: Any, data2: Any, data3: Any, data4: Any) {
        this.what = what
        this.data = data
        this.data2 = data2
        this.data3 = data3
        this.data4 = data4
    }
}