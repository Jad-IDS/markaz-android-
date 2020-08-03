package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponsePortfolioCash {

    @SerializedName("nameEn")
    @Expose
    var nameEn: String? = null
    @SerializedName("nameAr")
    @Expose
    var nameAr: String? = null
    @SerializedName("balanceCashAFT")
    @Expose
    var balanceCashAFT: Double? = null
    @SerializedName("balanceDeposits")
    @Expose
    var balanceDeposits: Int? = null
    @SerializedName("balanceBlocked")
    @Expose
    var balanceBlocked: Int? = null
    @SerializedName("receivableDividends")
    @Expose
    var receivableDividends: String? = null
    @SerializedName("receivableInterestAccrued")
    @Expose
    var receivableInterestAccrued: Int? = null
    @SerializedName("receivableOthers")
    @Expose
    var receivableOthers: Int? = null
    @SerializedName("payableFees")
    @Expose
    var payableFees: Double? = null
    @SerializedName("payableInterest")
    @Expose
    var payableInterest: Int? = null
    @SerializedName("payableOthers")
    @Expose
    var payableOthers: Int? = null
    @SerializedName("totalCashPosition")
    @Expose
    var totalCashPosition: Double? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("typeName")
    @Expose
    var typeName: String? = null

}