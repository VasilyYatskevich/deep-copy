package org.example

data class Man(
    var name: String?,
    var age: Int?,
    var favoriteBooks: List<String>?,
    var internalMan: Man? = null
)