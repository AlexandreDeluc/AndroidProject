package fr.isen.deluc.androiderestaurant.Network

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Plat (
    @SerializedName("name_fr") val name:String,
    @SerializedName("images") val images:List<String>,
    @SerializedName("ingredients")val ingredients: List<Ingredient>,
    @SerializedName("prices") val prices: List<Price>

    ):Serializable{

        fun getUrl(): String? {
            return if(images.isNotEmpty() && images.first().isNotEmpty()) {
                images.first()
            }
                else{
                    null
                }
            }
        }
