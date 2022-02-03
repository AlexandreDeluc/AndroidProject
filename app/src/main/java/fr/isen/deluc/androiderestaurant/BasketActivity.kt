package fr.isen.deluc.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }

    private fun loadList(){
        val items = Basket.getBasket(this).items
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = BasketAdapter(items)
    }
}