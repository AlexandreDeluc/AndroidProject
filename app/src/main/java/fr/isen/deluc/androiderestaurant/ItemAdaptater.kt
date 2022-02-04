package fr.isen.deluc.androiderestaurant

import android.content.Intent
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.deluc.androiderestaurant.Network.Plat
import fr.isen.deluc.androiderestaurant.databinding.CellMainBinding

class ItemAdapter(val items: List<Plat>, val itemClickListener: (Plat)->Unit): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    class ItemViewHolder(binding: CellMainBinding): RecyclerView.ViewHolder(binding.root){
        val title: TextView = binding.name
        val price: TextView = binding.price
        val img: ImageView = binding.image
        val layout: CardView = binding.root
    }

    override fun onCreateViewHolder(viewparent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = CellMainBinding.inflate(LayoutInflater.from(viewparent.context), viewparent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        val item = items[position]
        viewHolder.title.text = item.name
        viewHolder.price.text = "${item.prices.first().prices} €"
        Picasso.get()
            .load(item.getUrl())
            .placeholder(R.drawable.logo)
            .into(viewHolder.img)
        viewHolder.layout.setOnClickListener{
            itemClickListener.invoke(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}