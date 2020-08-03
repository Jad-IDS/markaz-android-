package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseFundDetails {

    @SerializedName("numberOfUnits")
    @Expose
    var numberOfUnits: Double? = null
    @SerializedName("avgCost")
    @Expose
    var avgCost: Double? = null
    @SerializedName("totalCost")
    @Expose
    var totalCost: Double? = null
    @SerializedName("navPerUnit")
    @Expose
    var navPerUnit: Double? = null
    @SerializedName("totalValue")
    @Expose
    var totalValue: Int? = null
    @SerializedName("unrealizedPL")
    @Expose
    var unrealizedPL: Int? = null
    @SerializedName("ytdPerfomancePerc")
    @Expose
    var ytdPerfomancePerc: Int? = null

}