package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseInvestmentInfo {



    @SerializedName("cash")
    @Expose
    var cash: String? = null
    @SerializedName("holding")
    @Expose
    var holding: String? = null

    @SerializedName("payable")
    @Expose
    var payable: String? = ""


    @SerializedName("receivable")
    @Expose
    var receivable: String? = null

    @SerializedName("portfolioValue")
    @Expose
    var portfolioValue: String? = null
    @SerializedName("mtdReturn")
    @Expose
    var mtdReturn: String? = null

    @SerializedName("ytdReturn")
    @Expose
    var ytdReturn: String? = null


    @SerializedName("benchmark")
    @Expose
    var benchmark: String? = null

    @SerializedName("assetValue")
    @Expose
    var assetValue: String? = null

    @SerializedName("liabilities")
    @Expose
    var liabilities: String? = null

    @SerializedName("holdingIndex")
    @Expose
    var holdingIndex: String? = null

    @SerializedName("cashIndex")
    @Expose
    var cashIndex: String? = null




}