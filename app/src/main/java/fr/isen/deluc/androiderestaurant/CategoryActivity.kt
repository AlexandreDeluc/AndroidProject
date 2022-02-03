package fr.isen.deluc.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient.FileChooserParams.parseResult
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.HttpResponse
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.deluc.androiderestaurant.Network.MenuResult
import fr.isen.deluc.androiderestaurant.Network.NetworkConstant
import fr.isen.deluc.androiderestaurant.Network.Plat
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

        fun getCategoryTitle(type: LunchType): String{
            return when(type){
                STARTER -> "Entrées"
                MAIN -> "Plats"
                FINISH -> "Desserts"
            }
        }
    }
}

class CategoryActivity : BaseActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var currentCategory: LunchType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currentCategory = intent.getSerializableExtra(homeActivity.CategoryType) as? LunchType ?: LunchType.STARTER
        setUpTitle()

        makeRequest()


    }

    private fun setUpTitle(){
        binding.categoryTitle.text = "Nos "+getString(LunchType.getResString(currentCategory))
    }

    private fun setUpList(items: List<Plat>){
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ItemAdapter(items){
            showDetail(it)
        }
    }

    private fun showDetail(item: Plat){
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
                parseResult(it.toString())
            },
            {
                Log.d("volley error", "$it")
            })
        queue.add(request)
    }

    private fun parseResult(response: String){
        var result = GsonBuilder().create().fromJson(response, MenuResult::class.java)
        val items = result.data.firstOrNull{
            it.name == LunchType.getCategoryTitle(currentCategory)
        }?.items
        items?.let {
            setUpList(it)
        }

        /*
        if (items != null){
            setUpList(items)
        }
         */
    }


}