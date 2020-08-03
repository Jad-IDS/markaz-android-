package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ResponseReviewPerformance {

    @SerializedName("nameEn")
    @Expose
    var nameEn: String? = null
    @SerializedName("nameAr")
    @Expose
    var nameAr: String? = null
    @SerializedName("portfolioWeight")
    @Expose
    var portfolioWeight: Int? = null
    @SerializedName("sectorBenchmarkWeight")
    @Expose
    var sectorBenchmarkWeight: Int? = null
    @SerializedName("portfolioReturn")
    @Expose
    var portfolioReturn: Int? = null
    @SerializedName("sectorBenchmarkReturn")
    @Expose
    var sectorBenchmarkReturn: Int? = null
    @SerializedName("pureSectorAllocation")
    @Expose
    var pureSectorAllocation: Int? = null
    @SerializedName("allocationSelectionInteraction")
    @Expose
    var allocationSelectionInteraction: Int? = null
    @SerializedName("withinSectorSelection")
    @Expose
    var withinSectorSelection: Int? = null
    @SerializedName("totalValueAdded")
    @Expose
    var totalValueAdded: Int? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("typeName")
    @Expose
    var typeName: String? = null

}