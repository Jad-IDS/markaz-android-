package com.ids.markaz.model


class Asset {
    var id: Int? = null
    var title: String? = null
    var color: String? = null
    var percentage: String? = null


    constructor(id: Int?, title: String?, color: String?, percentage: String?) {
        this.id = id
        this.title = title
        this.color = color
        this.percentage = percentage
    }
}