package com.ids.markaz.model



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class ResponseHtmlContent {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("htmlText")
    @Expose
    var htmlText: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("typeName")
    @Expose
    var typeName: String? = null

}
