package org.apps.mov.home.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.apps.mov.R
import org.apps.mov.model.Film

 class NowPlayingAdapter(private var data: List<Film>, private val listener:(Film) -> Unit)
    : RecyclerView.Adapter<NowPlayingAdapter.ViewHolder>() {

    lateinit var contextAdapter :Context

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : NowPlayingAdapter.ViewHolder{
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context

        val inflatedView = layoutInflater.inflate(R.layout.row_item_nowplaying, parent, false)

        return ViewHolder(inflatedView)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: NowPlayingAdapter.ViewHolder, position: Int){
        holder.bindItem(data[position], listener, contextAdapter )
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        private val tvTitle:TextView = view.findViewById(R.id.tvKursi)
        private val tvGenre:TextView = view.findViewById(R.id.tvGenre)
        private val tvRate:TextView = view.findViewById(R.id.tvRate)

        private val tvImage: ImageView = view.findViewById(R.id.ivPoster)
        fun bindItem(data:Film, listener: (Film) -> Unit, context: Context){
            tvTitle.setText(data.judul)
            tvGenre.setText(data.genre)
            tvRate.setText(data.rating)

            Glide.with(context)
                .load(data.poster)
                .into(tvImage)

            itemView.setOnClickListener {
                listener(data)
            }
        }

    }

}
