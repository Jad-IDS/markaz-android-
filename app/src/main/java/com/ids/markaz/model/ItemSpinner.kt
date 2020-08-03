package com.ids.markaz.model

class ItemSpinner {
    var id: Int? = null
    var name: String? = null
    var number: Int? = null



    constructor(id: Int?, name: String?) {
        this.name = name
        this.id = id
    }

    constructor(id: Int?, name: String?, number: Int?) {
        this.id = id
        this.name = name
        this.number = number
    }


}