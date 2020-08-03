package com.ids.markaz.model


class Portfolio {
    var id: Int? = null
    var title: String? = null
    var value: String? = null
    var percentage: String? = null
    var type: Int? = null

    constructor(id: Int?, title: String?, value: String?, percentage: String?, type: Int?) {
        this.id = id
        this.title = title
        this.value = value
        this.percentage = percentage
        this.type = type
    }


}