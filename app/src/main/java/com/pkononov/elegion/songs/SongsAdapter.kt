package com.pkononov.elegion.songs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pkononov.elegion.R
import com.pkononov.elegion.model.Song

class SongsAdapter : RecyclerView.Adapter<SongsHolder>() {

    private val mSongs: ArrayList<Song> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsHolder {
        var infalter = LayoutInflater.from(parent.context)
        var view = infalter.inflate(R.layout.list_item_song, parent, false)
        return SongsHolder(view)
    }

    override fun getItemCount() = mSongs.size

    override fun onBindViewHolder(holder: SongsHolder, position: Int) {
        var song = mSongs.get(position)
        holder.bind(song)
    }

    fun addData(data:List<Song>, isRefreshed:Boolean){
        if (isRefreshed){
            mSongs.clear()
        }

        mSongs.addAll(data)
        notifyDataSetChanged()
    }
}