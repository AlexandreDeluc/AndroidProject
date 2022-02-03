package fr.isen.deluc.androiderestaurant.Network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Price (@SerializedName("price") val prices: String): Serializable{
}