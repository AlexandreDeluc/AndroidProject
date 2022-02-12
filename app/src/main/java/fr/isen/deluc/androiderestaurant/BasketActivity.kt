package fr.isen.deluc.androiderestaurant

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.deluc.androiderestaurant.Basket.Basket
import fr.isen.deluc.androiderestaurant.Basket.BasketAdapter
import fr.isen.deluc.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.deluc.androiderestaurant.databinding.ActivityHomeBinding

class BasketActivity : AppCompatActivity() {

    lateinit var binding: ActivityBasketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadList()

        binding.orderBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    private fun loadList(){
        val basket = Basket.getBasket(this)
        val items = basket.items
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = BasketAdapter(items){
            basket.removeItem(it)
            basket.saveItems(this)
            loadList()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Toast.makeText(this, "Order send", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
       const val REQUEST_CODE = 111
    }
}