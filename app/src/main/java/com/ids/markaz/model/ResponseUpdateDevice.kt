package com.ids.markaz.model


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 class ResponseUpdateDevice {

    @SerializedName("type")
    @Expose
    var type: String? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("status")
    @Expose
    var status: Int? = null
    @SerializedName("traceId")
    @Expose
    var traceId: String? = null

}
