package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseAccountSummary {

    @SerializedName("nameEn")
    @Expose
    var nameEn: String? = null
    @SerializedName("nameAr")
    @Expose
    var nameAr: String? = null
    @SerializedName("typeId")
    @Expose
    var typeId: Int? = null
    @SerializedName("productNumber")
    @Expose
    var productNumber: String? = null
    @SerializedName("productKey")
    @Expose
    var productKey: Int? = null
    @SerializedName("productSeries")
    @Expose
    var productSeries: String? = null
    @SerializedName("statusEn")
    @Expose
    var statusEn: String? = null
    @SerializedName("statusAr")
    @Expose
    var statusAr: String? = null

    @SerializedName("cashPosition")
    @Expose
    var cashPosition: Double? = null

    @SerializedName("cashPositionPercent")
    @Expose
    var cashPositionPercent: Double? = null
    @SerializedName("holdingPosition")
    @Expose
    var holdingPosition: Double? = null
    @SerializedName("holdingPositionPercent")
    @Expose
    var holdingPositionPercent: Double? = null
    @SerializedName("navValuePosition")
    @Expose
    var navValuePosition: Int? = null
    @SerializedName("navValuePositionPercent")
    @Expose
    var navValuePositionPercent: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("typeName")
    @Expose
    var typeName: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = ""
    var arrayDetails: ArrayList<PortfolioDetails>? = arrayListOf()
    var expanded: Boolean? = false

    @SerializedName("payableReceivable")
    @Expose
    var payableReceivable: String? = null

    @SerializedName("loans")
    @Expose
    var loans: String? = null

    @SerializedName("units")
    @Expose
    var units: String? = null

    @SerializedName("total")
    @Expose
    var total: String? = null

    @SerializedName("currency")
    @Expose
    var currency: String? = null
}