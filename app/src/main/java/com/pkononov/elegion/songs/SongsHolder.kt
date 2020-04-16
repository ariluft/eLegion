package com.pkononov.elegion.songs

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pkononov.elegion.R
import com.pkononov.elegion.model.Song

class SongsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val mTitle: TextView = itemView.findViewById(R.id.tv_title)
    private val mDuration: TextView = itemView.findViewById(R.id.tv_duration)

    fun bind(item: Song){
        mTitle.text = item.name
        mDuration.text = item.duration
    }
}