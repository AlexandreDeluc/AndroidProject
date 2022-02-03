package fr.isen.deluc.androiderestaurant.Basket

import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import com.google.gson.GsonBuilder
import fr.isen.deluc.androiderestaurant.Network.Plat
import java.io.File
import java.io.Serializable

class Basket(val items: MutableList<BasketItem>): Serializable {

    var itemsCount: Int=0
    get() {
        val count = items.map {
            it.quantity
        }.reduceOrNull(){ acc, i -> acc + i}
        return count ?:0
    }

    var totalPrice: Float = 0f
    get() {
        return items.map {
            it.quantity * it.dish.prices.first().prices.toFloat()
        }.reduceOrNull { acc, fl -> acc + fl }?:0f
        }


    fun addItems(item: Plat, quantity: Int) {
        val existingItem = items.firstOrNull { it.dish.name == item.name }
        existingItem?.let{
            existingItem.quantity += quantity
        }?: run{
            val basketItem = BasketItem(item, quantity)
            items.add(basketItem)
        }
    }

    fun saveItems(context: Context) {
        val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
        val json = GsonBuilder().create().toJson(this)
        jsonFile.writeText(json)
        Log.d("basket",json)
        updateCounter(context)

    }

    private fun updateCounter(context: Context) {
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, itemsCount)
        editor.apply()
    }

    companion object {
        fun getBasket(context: Context): Basket {
            val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
            if (jsonFile.exists()) {
                val json = jsonFile.readText()
                return GsonBuilder().create().fromJson(json, Basket::class.java)
            }
            else {
                return Basket(mutableListOf())
            }
        }
        const val BASKET_FILE = "basket.json"
        const val ITEMS_COUNT = "ITEMS_COUNT"
        const val USER_PREFERENCE_NAME = "USER_PREFERENCE_NAME"
    }

}
class BasketItem(val dish: Plat, var quantity: Int) : Serializable {}
