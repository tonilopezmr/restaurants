package com.come.restaurants.order.domain.model

class Plate(val id: String,
            val name: String,
            val price: Double,
            val ingredients: List<Ingredients>)