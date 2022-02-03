package fr.isen.deluc.androiderestaurant

import android.content.Context
import android.content.Intent
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import fr.isen.deluc.androiderestaurant.Basket.Basket

open class BaseActivity: AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)


        val menuView = menu?.findItem(R.id.panier)?.actionView
        val countText = menuView?.findViewById(R.id.textPanier) as? TextView
        val count = getItemsCount()
        countText?.isVisible = count > 0
        countText?.text = count.toString()

        menuView?.setOnClickListener{
            if(count>0){
                val intent = Intent(this@BaseActivity, BasketActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        invalidateOptionsMenu()
    }

    private fun getItemsCount(): Int{
        val sharedPrerences = getSharedPreferences(Basket.USER_PREFERENCE_NAME, Context.MODE_PRIVATE)
        return sharedPrerences.getInt(Basket.ITEMS_COUNT, 0)
    }
}