package fr.isen.deluc.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import fr.isen.deluc.androiderestaurant.databinding.ActivityHomeBinding

class homeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listenClick()
        Log.d("Debug","Home onCreate")
    }

    private fun listenClick() {
        binding.starterbtn.setOnClickListener {
            showCategory(LunchType.STARTER)
        }
        binding.dishbtn.setOnClickListener {
            showCategory(LunchType.MAIN)
        }
        binding.dessertbtn.setOnClickListener {
            showCategory(LunchType.FINISH)
        }
    }
    private fun showCategory(item: LunchType){
            val intent = Intent(this@homeActivity, CategoryActivity::class.java)
            intent.putExtra(homeActivity.CategoryType, item)
            startActivity(intent)

    }

    companion object{
        const val CategoryType = "CategoryType"
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Debug:", "Home onDestroy")
    }
    override fun onStop() {
        super.onStop()
        Log.d("Debug","Home onStop")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Debug","Home onPause")
    }


}