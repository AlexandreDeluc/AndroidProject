package fr.isen.deluc.androiderestaurant.Network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Ingredient(@SerializedName("name_fr") var name: String): Serializable {
}