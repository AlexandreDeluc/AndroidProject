package fr.isen.deluc.androiderestaurant

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.deluc.androiderestaurant.Basket.Basket
import fr.isen.deluc.androiderestaurant.Basket.BasketAdapter
import fr.isen.deluc.androiderestaurant.Network.NetworkConstant
import fr.isen.deluc.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.deluc.androiderestaurant.databinding.ActivityHomeBinding
import org.json.JSONObject

class BasketActivity : AppCompatActivity() {

    lateinit var binding: ActivityBasketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadList()

        binding.orderBtn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            getResult.launch(intent)
        }
    }

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if(it.resultCode == Activity.RESULT_OK){
            makeRequest()
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

    private fun makeRequest(){
        val path = NetworkConstant.BASE_URL + NetworkConstant.ORDER
        val queue = Volley.newRequestQueue(this)
        val basket = Basket.getBasket(this)
        val sharedPreferences = getSharedPreferences(LoginActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val parameters = JSONObject()

        parameters.put(NetworkConstant.KEY_MSG, basket.toJson())
        parameters.put(NetworkConstant.KEY_USER, sharedPreferences.getInt(LoginActivity.ID_USER, -1))
        parameters.put(NetworkConstant.KEY_SHOP, NetworkConstant.SHOP)

        val request = JsonObjectRequest(
            Request.Method.POST,
            path,
            parameters,
            {
                Log.d("request", it.toString(2))
                basket.clear()
                basket.saveItems(this)
                finish()
            },
            {
                Log.d("request", it.message ?: "une erreur est survenue")
            }
        )
        queue.add(request)
    }
}