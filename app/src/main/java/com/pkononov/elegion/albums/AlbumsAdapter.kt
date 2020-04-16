package com.pkononov.elegion.albums

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.pkononov.elegion.R
import com.pkononov.elegion.model.Album

class AlbumsAdapter(var mOnClickListener: OnItemClickListener) : RecyclerView.Adapter<AlbumsHolder>() {
    private val mAlbums:ArrayList<Album.Companion.DataBean> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsHolder {
        var inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.list_item_album, parent, false)
        return AlbumsHolder(view)
    }

    override fun getItemCount(): Int {
        return mAlbums.count()
    }

    override fun onBindViewHolder(holder: AlbumsHolder, position: Int) {
        var album = mAlbums.get(position)
        holder.bind(album, mOnClickListener)
    }

    fun addData(data:List<Album.Companion.DataBean>, isRefreshed:Boolean){
        if (isRefreshed){
            mAlbums.clear()
        }

        mAlbums.addAll(data)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(album: Album.Companion.DataBean)
    }
}