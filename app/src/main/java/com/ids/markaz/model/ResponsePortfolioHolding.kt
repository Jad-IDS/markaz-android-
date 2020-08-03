package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponsePortfolioHolding {

    @SerializedName("totalholdingsValue")
    @Expose
    var totalholdingsValue: Double? = null
    @SerializedName("changePercent")
    @Expose
    var changePercent: Int? = null
    @SerializedName("mainIndexNameEn")
    @Expose
    var mainIndexNameEn: String? = null
    @SerializedName("mainIndexNameAr")
    @Expose
    var mainIndexNameAr: String? = null
    @SerializedName("indexChangePercent")
    @Expose
    var indexChangePercent: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("typeName")
    @Expose
    var typeName: String? = null

}