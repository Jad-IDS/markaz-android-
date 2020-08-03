package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseTransaction {

    @SerializedName("systemId")
    @Expose
    var systemId: String? = ""
    @SerializedName("transactionDate")
    @Expose
    var transactionDate: String? = ""
    @SerializedName("transactionTypeAr")
    @Expose
    var transactionTypeAr: String? = ""
    @SerializedName("transactionTypeEn")
    @Expose
    var transactionTypeEn: String? = ""
    @SerializedName("productNo")
    @Expose
    var productNo: String? = ""
    @SerializedName("shortName")
    @Expose
    var shortName: String? = ""
    @SerializedName("securityCode")
    @Expose
    var securityCode: String? = ""
    @SerializedName("securityShortName")
    @Expose
    var securityShortName: String? = ""
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = 0
    @SerializedName("unitPrice")
    @Expose
    var unitPrice: Double? = 0.0
    @SerializedName("valueDate")
    @Expose
    var valueDate: String? = ""
    @SerializedName("valueAmount")
    @Expose
    var valueAmount: String? = ""
    @SerializedName("valueCurrency")
    @Expose
    var valueCurrency: String? = ""
    @SerializedName("settlementDate")
    @Expose
    var settlementDate: String? = ""
    @SerializedName("settlementAmount")
    @Expose
    var settlementAmount: String? = ""
    @SerializedName("settlementCurrency")
    @Expose
    var settlementCurrency: String? = ""


    @SerializedName("avgCost")
    @Expose
    var avgCost: String? = ""
    @SerializedName("realizedPL")
    @Expose
    var realizedPL: String? = ""
    


    
    @SerializedName("id")
    @Expose
    var id: Int? = 0
    @SerializedName("typeName")
    @Expose
    var typeName: String? = ""

    var date: String? = ""
    var arrayDetails: ArrayList<PortfolioDetails>? = arrayListOf()
    var expanded: Boolean? = false

}