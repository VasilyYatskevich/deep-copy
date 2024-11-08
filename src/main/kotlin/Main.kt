package org.example

fun main() {
    val original = Man("John", 25, listOf("To Kill a Mockingbird", "Lord of the Flies"))
    val copy = deepCopy(original)

    println("original === copy: ${original === copy}")
    println("Original object: $original")
    println("Copied object: $copy")
}
