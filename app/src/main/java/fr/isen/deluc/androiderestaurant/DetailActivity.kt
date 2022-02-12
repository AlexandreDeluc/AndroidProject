package fr.isen.deluc.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import fr.isen.deluc.androiderestaurant.Basket.Basket
import fr.isen.deluc.androiderestaurant.Detail.PhotoAdapter
import fr.isen.deluc.androiderestaurant.Network.Plat
import fr.isen.deluc.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.deluc.androiderestaurant.databinding.FragmentPhotoBinding
import kotlin.math.max

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var currentDish: Plat? =null
    private var itemCount =0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        binding= ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentDish = intent.getSerializableExtra(CategoryActivity.selectedItem) as? Plat

        setUpContent()
        refreshShopBtn()
        listenClick()

    }

    private fun setUpContent(){
        val currentDish = intent.getSerializableExtra(CategoryActivity.selectedItem) as? Plat

        binding.titleDetail.text = currentDish?.name

        binding.ingredients.text = currentDish?.ingredients?.map { it.name }?.joinToString (",\n")

        currentDish?.let {
            binding.viewPager.adapter = PhotoAdapter(this, it.images)
        }
    }

    private fun listenClick(){
        binding.plusBtn.setOnClickListener{
            itemCount++
            refreshShopBtn()

        }
        binding.moinsBtn.setOnClickListener{
            itemCount = max(0f,itemCount-1)
            refreshShopBtn()
        }
        binding.shopBtn.setOnClickListener{
            currentDish?.let { dish->
                val basket = Basket.getBasket(this)
                basket.addItems(dish, itemCount.toInt())
                basket.saveItems(this)
                Snackbar.make(binding.root, "Votre plat a été aujouté au panier", Snackbar.LENGTH_SHORT).show()
                invalidateOptionsMenu()

            }
        }
    }

    private fun refreshShopBtn(){
        currentDish?.let{ dish ->
            val price : Float = dish.prices.first().prices.toFloat()
            val total = price * itemCount
            binding.shopBtn.text = "${getString(R.string.total)} $total €"
            binding.number.setText("${itemCount.toInt()}")
        }

    }


}