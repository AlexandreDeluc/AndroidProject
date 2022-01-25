package fr.isen.deluc.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import fr.isen.deluc.androiderestaurant.Network.NetworkConstant
import fr.isen.deluc.androiderestaurant.databinding.ActivityCategoryBinding
import fr.isen.deluc.androiderestaurant.databinding.ActivityHomeBinding
import org.json.JSONObject

enum class LunchType {
    STARTER, MAIN, FINISH;

    companion object{
        fun getResString(type: LunchType): Int{
            return when(type){
                STARTER -> R.string.starterbtn
                MAIN -> R.string.dishbtn
                FINISH -> R.string.dessertbtn
            }
        }
    }
}

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var currentCategory: LunchType

    private var fakelist = listOf("tiramisu", "crêpes", "gaufres")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentCategory = intent.getSerializableExtra(homeActivity.CategoryType) as? LunchType ?: LunchType.STARTER
        setUpTitle()

        makeRequest()

        setUpList()
    }

    private fun setUpTitle(){
        binding.categoryTitle.text = "Nos "+getString(LunchType.getResString(currentCategory))
    }

    private fun setUpList(){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ItemAdapter(fakelist){
            showDetail(it)
        }
    }

    private fun showDetail(item: String){
        val intent = Intent(this@CategoryActivity, DetailActivity::class.java)
        intent.putExtra(CategoryActivity.selectedItem, item)
        startActivity(intent)
    }

    companion object{
        const val selectedItem = "SelectedItem"
    }

    private fun makeRequest(){

        val queue = Volley.newRequestQueue(this)
        val url = NetworkConstant.BASE_URL + NetworkConstant.MENU
        val parameters = JSONObject()
        parameters.put(NetworkConstant.KEY_SHOP, NetworkConstant.SHOP)
        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            parameters,
            {
                Log.d("debug", "$it")
            },
            {
                Log.d("debug", "$it")
            })
        queue.add(request)
    }


}