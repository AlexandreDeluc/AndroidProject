package fr.isen.deluc.androiderestaurant.Basket

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.deluc.androiderestaurant.R
import fr.isen.deluc.androiderestaurant.databinding.CellBasketBinding

class BasketAdapter(private val items: List<BasketItem>,
    val deleteClickListener:(BasketItem) -> Unit):
    RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {
    lateinit var context: Context
    class BasketViewHolder(binding: CellBasketBinding) : RecyclerView.ViewHolder(binding.root){
        val dishName: TextView = binding.article
        val price: TextView = binding.prix
        val quantity: TextView = binding.nbArticle
        val delete: ImageButton = binding.icDelete
        val imageView: ImageView = binding.image

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        context = parent.context
        return BasketViewHolder(CellBasketBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val basketItem = items[position]
        holder.dishName.text = basketItem.dish.name
        holder.price.text = "${basketItem.dish.prices.first().prices}€"
        holder.quantity.text = "${context.getString(R.string.quantity)}: ${basketItem.quantity.toString()}"
        Picasso.get()
            .load(basketItem.dish.getUrl())
            .placeholder(R.drawable.ic_launcher_background)
            .into(holder.imageView)
        holder.delete.setOnClickListener{
            deleteClickListener.invoke(basketItem)
        }
    }

    override fun getItemCount(): Int {
        return items.count()
    }

}