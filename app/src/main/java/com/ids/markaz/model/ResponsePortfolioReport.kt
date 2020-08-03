package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponsePortfolioReport {

    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("number")
    @Expose
    var number: Int? = null
    @SerializedName("titleAr")
    @Expose
    var titleAr: String? = null
    @SerializedName("titleEn")
    @Expose
    var titleEn: String? = null
    @SerializedName("reportNumber")
    @Expose
    var reportNumber: Int? = null
    @SerializedName("descriptionAr")
    @Expose
    var descriptionAr: String? = null
    @SerializedName("descriptionEn")
    @Expose
    var descriptionEn: String? = null
    @SerializedName("creationDate")
    @Expose
    var creationDate: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("typeName")
    @Expose
    var typeName: String? = null
    var selected: Boolean? = false
}