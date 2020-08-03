package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseAllInvestment {

    @SerializedName("currencyEn")
    @Expose
    var currencyEn: String? = null
    @SerializedName("currencyAr")
    @Expose
    var currencyAr: String? = null


    @SerializedName("countryEn")
    @Expose
    var countryEn: String? = null
    @SerializedName("countryAr")
    @Expose
    var countryAr: String? = null


    @SerializedName("typeEn")
    @Expose
    var typeEn: String? = null
    @SerializedName("typeAr")
    @Expose
    var typeAr: String? = null


    @SerializedName("sectorEn")
    @Expose
    var sectorEn: String? = null
    @SerializedName("sectorAr")
    @Expose
    var sectorAr: String? = null

    @SerializedName("value")
    @Expose
    var value: String? = null
    var color: Int? = null

    @SerializedName("totalPercentage")
    @Expose
    var totalPercentage: Double? = null

}