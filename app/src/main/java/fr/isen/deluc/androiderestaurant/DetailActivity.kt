package fr.isen.deluc.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.deluc.androiderestaurant.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.title.text= intent.getStringExtra(CategoryActivity.selectedItem)
    }


}