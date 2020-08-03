package com.ids.markaz.model


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseUserPortfolio {

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
    @SerializedName("statusId")
    @Expose
    var statusId: Int? = null
    @SerializedName("statusEn")
    @Expose
    var statusEn: String? = null
    @SerializedName("statusAr")
    @Expose
    var statusAr: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("typeName")
    @Expose
    var typeName: String? = null

}