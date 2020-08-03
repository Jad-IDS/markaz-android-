package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponsePerformance {

    @SerializedName("nameEn")
    @Expose
    var nameEn: String? = null
    @SerializedName("nameAr")
    @Expose
    var nameAr: String? = null
    @SerializedName("ytdPortfolio")
    @Expose
    var ytdPortfolio: Double? = null
    @SerializedName("ytdBench")
    @Expose
    var ytdBench: Int? = null
    @SerializedName("mtdPortfolio")
    @Expose
    var mtdPortfolio: Double? = null
    @SerializedName("mtdBench")
    @Expose
    var mtdBench: Int? = null
    @SerializedName("wtdPortfolio")
    @Expose
    var wtdPortfolio: Int? = null
    @SerializedName("wtdBench")
    @Expose
    var wtdBench: Int? = null
    @SerializedName("siPortfolio")
    @Expose
    var siPortfolio: Double? = null
    @SerializedName("siBench")
    @Expose
    var siBench: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("typeName")
    @Expose
    var typeName: String? = null

}