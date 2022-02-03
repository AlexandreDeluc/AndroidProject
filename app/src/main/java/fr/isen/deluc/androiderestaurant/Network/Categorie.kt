package fr.isen.deluc.androiderestaurant.Network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Categorie (
    @SerializedName("name_fr") val name: String,
    @SerializedName("items") val items: List<Plat>)
    :Serializable{
}