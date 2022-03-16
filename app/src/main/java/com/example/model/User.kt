package com.example.model

class User {
    var id: Int? = null
    var name: String? = null
    var phoneNumber: String? = null
    var country: String? = null
    var address: String? = null
    var password: String? = null
    var imagePath: String? = null

    constructor(
        id: Int?,
        name: String?,
        phoneNumber: String?,
        country: String?,
        address: String?,
        password: String?,
        imagePath: String?,
    ) {
        this.id = id
        this.name = name
        this.phoneNumber = phoneNumber
        this.country = country
        this.address = address
        this.password = password
        this.imagePath = imagePath
    }

    constructor()

    constructor(
        name: String?,
        phoneNumber: String?,
        country: String?,
        address: String?,
        password: String?,
        imagePath: String?,
    ) {
        this.name = name
        this.phoneNumber = phoneNumber
        this.country = country
        this.address = address
        this.password = password
        this.imagePath = imagePath
    }

}