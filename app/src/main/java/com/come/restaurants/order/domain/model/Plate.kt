package com.come.restaurants.order.domain.model

class Plate(val id: String = "",
            val name: String = "",
            val price: Double = 0.0,
            val ingredients: List<Ingredients> = emptyList())