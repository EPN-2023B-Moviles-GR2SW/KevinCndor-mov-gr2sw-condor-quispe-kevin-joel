package com.example.deber02.dto

class ProductDto(
    val name: String,
    val price: Double,
    val stock: Int,
    val isAvailable: Boolean,
    val distributorId: String,
) {
}