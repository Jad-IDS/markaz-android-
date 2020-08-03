package com.ids.markaz.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RequestUpdateDevice {

    @SerializedName("model")
    @Expose
    var model: String? = null
    @SerializedName("imei")
    @Expose
    var imei: String? = null
    @SerializedName("badge")
    @Expose
    var badge: Int? = null
    @SerializedName("osVersion")
    @Expose
    var osVersion: String? = null
    @SerializedName("deviceToken")
    @Expose
    var deviceToken: String? = null
    @SerializedName("deviceTypeId")
    @Expose
    var deviceTypeId: String? = null
    @SerializedName("appVersion")
    @Expose
    var appVersion: String? = null
    @SerializedName("isProduction")
    @Expose
    var isProduction: Int? = null
    @SerializedName("generalNotification")
    @Expose
    var generalNotification: Int? = null
    @SerializedName("registrationDate")
    @Expose
    var registrationDate: String? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null


    constructor(
        model: String?,
        imei: String?,
        badge: Int?,
        osVersion: String?,
        deviceToken: String?,
        deviceTypeId: String?,
        appVersion: String?,
        isProduction: Int?,
        generalNotification: Int?,
        registrationDate: String?,
        id: Int?
    ) {
        this.model = model
        this.imei = imei
        this.badge = badge
        this.osVersion = osVersion
        this.deviceToken = deviceToken
        this.deviceTypeId = deviceTypeId
        this.appVersion = appVersion
        this.isProduction = isProduction
        this.generalNotification = generalNotification
        this.registrationDate = registrationDate
        this.id = id
    }
}