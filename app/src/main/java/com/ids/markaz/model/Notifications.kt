package com.ids.markaz.model

class Notifications {
    var id: Int? = null
    var title: String? = null
    var text: String? = null
    var date: String? = null


    constructor(id: Int?, title: String?, text: String?, date: String?) {
        this.id = id
        this.title = title
        this.text = text
        this.date = date
    }
}