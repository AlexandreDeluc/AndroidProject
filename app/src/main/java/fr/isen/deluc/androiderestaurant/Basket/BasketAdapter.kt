package fr.isen.deluc.androiderestaurant.Basket

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.deluc.androiderestaurant.R
import fr.isen.deluc.androiderestaurant.databinding.CellBasketBinding

class BasketAdapter(private val items: List<BasketItem>): RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {
    class BasketViewHolder(binding: CellBasketBinding) : RecyclerView.ViewHolder(binding.root){
        val dishName: TextView = binding.article
        val price: TextView = binding.prix
        val quantity: TextView = binding.nbArticle
        val delete: ImageButton = binding.icDelete
        val imageView: ImageView = binding.image

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        return BasketViewHolder(CellBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val basketItem = items[position]
        holder.dishName.text = basketItem.dish.name
        holder.price.text = basketItem.dish.prices.first().prices
        holder.quantity.text = basketItem.quantity.toString()
        Picasso.get()
            .load(basketItem.dish.getUrl())
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        class CategoryViewHolder(view: CellBasketBinding)
        {
            
        }
    }

}