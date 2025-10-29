package com.example.gameforgamers.model
import java.io.Serializable
data class Game(
    val id:Int,
    val title:String,
    val price:String,
    val drawableName:String,
    val genre:String,
    val durationHours:Int,
    val shortDesc:String,
    val originalPrice:String?=null,
    val discountPercent:Int=0
): Serializable {
    fun isOffer():Boolean = (originalPrice!=null && discountPercent>0)
    fun offerPrice(): String? {
        if(!isOffer()) return null
        val base = (originalPrice ?: price).replace("$","").replace(".","").toIntOrNull() ?: return null
        val disc = (base * (100 - discountPercent)) / 100
        return "$" + "%,d".format(disc).replace(",", ".")
    }
}