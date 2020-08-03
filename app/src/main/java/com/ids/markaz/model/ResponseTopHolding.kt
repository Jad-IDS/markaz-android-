package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseTopHolding {

    @SerializedName("stock")
    @Expose
    var stock: String? = null
    @SerializedName("currency")
    @Expose
    var currency: String? = null
    @SerializedName("securityPosition")
    @Expose
    var securityPosition: Int? = null
    @SerializedName("cost")
    @Expose
    var cost: Double? = null
    @SerializedName("price")
    @Expose
    var price: Double? = null
    @SerializedName("totalCost")
    @Expose
    var totalCost: Double? = null
    @SerializedName("totalValue")
    @Expose
    var totalValue: Double? = null
    @SerializedName("unrealized")
    @Expose
    var unrealized: Double? = null
    @SerializedName("percentage")
    @Expose
    var percentage: Double? = null

}