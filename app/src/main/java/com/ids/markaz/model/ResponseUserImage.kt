package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName




class ResponseUserImage {

    @SerializedName("userId")
    @Expose
    var userId: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null


    constructor(userId: String?, image: String?) {
        this.userId = userId
        this.image = image
    }
}