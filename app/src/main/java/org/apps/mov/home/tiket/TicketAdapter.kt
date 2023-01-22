package org.apps.mov.home.tiket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.apps.mov.R
import org.apps.mov.model.Checkout
import org.apps.mov.model.Film
import java.text.NumberFormat
import java.util.*

class TicketAdapter(private var data: List<Checkout>, private val listener:(Checkout) -> Unit)
    : RecyclerView.Adapter<TicketAdapter.ViewHolder>() {

    lateinit var contextAdapter :Context

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : TicketAdapter.ViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context

        val inflatedView = layoutInflater.inflate(R.layout.row_item_checkout_white, parent, false)

        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: TicketAdapter.ViewHolder, position: Int){
        holder.bindItem(data[position], listener, contextAdapter )
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val tvTitle:TextView = view.findViewById(R.id.tvKursi)

        fun bindItem(data:Checkout, listener: (Checkout) -> Unit, context: Context){

            tvTitle.setText("Seat No. " + data.kursi)


            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

}
