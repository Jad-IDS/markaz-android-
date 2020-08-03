package com.ids.markaz.model

class PerformanceInside {
    var id: Int? = null
    var holdingCode: String? = null
    var mv_kd: String? = null


    constructor(id: Int?, holdingCode: String?, mv_kd: String?) {
        this.id = id
        this.holdingCode = holdingCode
        this.mv_kd = mv_kd
    }
}