package com.pkononov.elegion.albums

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pkononov.elegion.ApiUtils
import com.pkononov.elegion.R
import com.pkononov.elegion.albums.AlbumsAdapter.OnItemClickListener
import com.pkononov.elegion.model.Album
import com.pkononov.elegion.model.Albums
import com.pkononov.elegion.songs.DetailAlbumFragment
import retrofit2.Call
import retrofit2.Response

class AlbumsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(album: Album.Companion.DataBean) {
            fragmentManager!!.beginTransaction()
                .replace(R.id.fragmentContainer,
                    DetailAlbumFragment.newInstance(album)
                )
                .addToBackStack(DetailAlbumFragment::class.java.simpleName)
                .commit()
        }
    }

    private val mAlbumAdapter = AlbumsAdapter(onItemClickListener)



    private lateinit var mRecycleView : RecyclerView

    private lateinit var mRefresher : SwipeRefreshLayout
    private lateinit var mErrorView : View

    companion object{
        fun newInstance(): AlbumsFragment = AlbumsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fr_recycler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mRecycleView = view.findViewById(R.id.recycler)
        mRefresher = view.findViewById(R.id.refresher)
        mRefresher.setOnRefreshListener (this)
        mErrorView = view.findViewById(R.id.errorView)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.setTitle(R.string.albums)
        mRecycleView.layoutManager = LinearLayoutManager(activity)
        mRecycleView.adapter = mAlbumAdapter

        onRefresh()
    }

    override fun onRefresh() {
        mRefresher.post{
            mRefresher.isRefreshing = true
            getAlbums()
        }
    }

    private fun getAlbums(){
        ApiUtils.getApi().getAlbums().enqueue(object : retrofit2.Callback<Albums>{
            override fun onFailure(call: Call<Albums>, t: Throwable) {
                mRecycleView.visibility = View.GONE
                mErrorView.visibility = View.VISIBLE

                mRefresher.isRefreshing = false
            }

            override fun onResponse(call: Call<Albums>, response: Response<Albums>) {
                if (response.isSuccessful){
                    mRecycleView.visibility = View.VISIBLE
                    mErrorView.visibility = View.GONE

                    mAlbumAdapter.addData(response.body()!!.data, true)
                }else{
                    mRecycleView.visibility = View.GONE
                    mErrorView.visibility = View.VISIBLE
                }

                mRefresher.isRefreshing = false
            }
        })
    }
}