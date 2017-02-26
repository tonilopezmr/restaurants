package com.come.restaurants.restaurant.login

import com.come.restaurants.restaurant.domain.model.Restaurant

//TODO We should actually replace it with configuration files
data class User (val username : String, val restaurant : Restaurant)