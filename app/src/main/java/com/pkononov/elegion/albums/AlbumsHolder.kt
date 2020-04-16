package com.pkononov.elegion.albums

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pkononov.elegion.R
import com.pkononov.elegion.model.Album

class AlbumsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private var mTitle: TextView = itemView.findViewById(R.id.tv_title)
    private var mReleaseDate: TextView = itemView.findViewById(R.id.tv_release_date)

    fun bind(item: Album){
        mTitle.setText(item.name)
        mReleaseDate.setText(item.release_date)
    }
}