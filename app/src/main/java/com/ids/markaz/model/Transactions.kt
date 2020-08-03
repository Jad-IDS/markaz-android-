package com.ids.markaz.model


class Transactions {
    var id: Int? = null
    var date: String? = null
    var product: String? = null
    var type: String? = null
    var security: String? = null
    var qty: String? = null
    var unitPrice: String? = null


    constructor(
        id: Int?,
        date: String?,
        product: String?,
        type: String?,
        security: String?,
        qty: String?,
        unitPrice: String?
    ) {
        this.id = id
        this.date = date
        this.product = product
        this.type = type
        this.security = security
        this.qty = qty
        this.unitPrice = unitPrice
    }
}