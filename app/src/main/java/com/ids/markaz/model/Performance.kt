package com.ids.markaz.model


class Performance {
    var id: Int? = null
    var portfolio: String? = null
    var percentage: String? = null
    var benchmark: String? = null


    constructor(id: Int?, portfolio: String?, percentage: String?, benchmark: String?) {
        this.id = id
        this.portfolio = portfolio
        this.percentage = percentage
        this.benchmark = benchmark
    }
}