package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class PortfolioDetails {

    @SerializedName("name")
    @Expose
    var name: String? = ""
    @SerializedName("price")
    @Expose
    var price: String? = ""
    @SerializedName("percentage")
    @Expose
    var percentage: String? = ""

    constructor(name: String?, price: String?, percentage: String?) {
        this.name = name
        this.price = price
        this.percentage = percentage
    }

}