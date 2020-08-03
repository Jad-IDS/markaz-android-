package com.ids.markaz.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Holdings {

    @SerializedName("name")
    @Expose
    var name: String? = ""
    @SerializedName("price")
    @Expose
    var price: String? = ""
    @SerializedName("percentage")
    @Expose
    var percentage: String? = ""

    @SerializedName("up")
    @Expose
    var up: Boolean? = false


    constructor(name: String?, price: String?, percentage: String?, up: Boolean?) {
        this.name = name
        this.price = price
        this.percentage = percentage
        this.up = up
    }
}