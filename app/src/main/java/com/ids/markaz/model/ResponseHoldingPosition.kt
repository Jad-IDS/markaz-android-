package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseHoldingPosition {

    @SerializedName("nameEn")
    @Expose
    var nameEn: String? = null
    @SerializedName("nameAr")
    @Expose
    var nameAr: String? = null
    @SerializedName("code")
    @Expose
    var code: String? = null
    @SerializedName("tickerAr")
    @Expose
    var tickerAr: String? = null
    @SerializedName("tickerEn")
    @Expose
    var tickerEn: String? = null
    @SerializedName("codeTickerAr")
    @Expose
    var codeTickerAr: String? = null
    @SerializedName("codeTickerEn")
    @Expose
    var codeTickerEn: String? = null
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = null
    @SerializedName("stockDivDue")
    @Expose
    var stockDivDue: Int? = null
    @SerializedName("lastPrice")
    @Expose
    var lastPrice: Double? = null
    @SerializedName("totalMarketValue")
    @Expose
    var totalMarketValue: Double? = null
    @SerializedName("avgCost")
    @Expose
    var avgCost: Double? = null
    @SerializedName("totalCost")
    @Expose
    var totalCost: Double? = null
    @SerializedName("currentPrice")
    @Expose
    var currentPrice: Double? = null
    @SerializedName("totalCurrentValue")
    @Expose
    var totalCurrentValue: Double? = null
    @SerializedName("unrealizedValue")
    @Expose
    var unrealizedValue: Int? = null
    @SerializedName("unrealizedPercent")
    @Expose
    var unrealizedPercent: Int? = null
    @SerializedName("realizedPL")
    @Expose
    var realizedPL: Int? = null
    @SerializedName("divRecieved")
    @Expose
    var divRecieved: Int? = null
    @SerializedName("wtdReturn")
    @Expose
    var wtdReturn: Int? = null
    @SerializedName("mtdReturn")
    @Expose
    var mtdReturn: Int? = null
    @SerializedName("ytdReturn")
    @Expose
    var ytdReturn: Int? = null
    @SerializedName("changeValue")
    @Expose
    var changeValue: Int? = null
    @SerializedName("changePercent")
    @Expose
    var changePercent: Int? = null
    @SerializedName("costPercent")
    @Expose
    var costPercent: Int? = null
    @SerializedName("marketValuePercent")
    @Expose
    var marketValuePercent: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("typeName")
    @Expose
    var typeName: String? = null

}