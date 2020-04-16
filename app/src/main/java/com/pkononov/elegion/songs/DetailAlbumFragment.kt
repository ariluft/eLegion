package com.pkononov.elegion.songs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.pkononov.elegion.ApiUtils
import com.pkononov.elegion.R
import com.pkononov.elegion.model.Album
import com.pkononov.elegion.model.Songs
import retrofit2.Call
import retrofit2.Response

class DetailAlbumFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var mRecycleView : RecyclerView

    private lateinit var mRefresher : SwipeRefreshLayout
    private lateinit var mErrorView : View

    private lateinit var mAlbum : Album.Companion.DataBean

    private val mSongAdapter = SongsAdapter()

    companion object{
        val ALBUM_KEY : String = "ALBUM_KEY"
        fun newInstance(album:Album.Companion.DataBean): DetailAlbumFragment{
            var args = Bundle()
            args.putSerializable(ALBUM_KEY, album)

            var fragment = DetailAlbumFragment()
            fragment.arguments = args

            return fragment
        }
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

        mAlbum = arguments!!.getSerializable(ALBUM_KEY) as Album.Companion.DataBean
        activity!!.title = mAlbum.name

        mRecycleView.layoutManager = LinearLayoutManager(activity)
        mRecycleView.adapter = mSongAdapter

        onRefresh()
    }

    override fun onRefresh() {
        mRefresher.post{
            mRefresher.isRefreshing = true
            getAlbum()
        }
    }

    private fun getAlbum(){
        ApiUtils.getApi().getAlbum(mAlbum.id).enqueue(object : retrofit2.Callback<Album>{
            override fun onFailure(call: Call<Album>, t: Throwable) {
                mRecycleView.visibility = View.GONE
                mErrorView.visibility = View.VISIBLE

                mRefresher.isRefreshing = false
            }

            override fun onResponse(call: Call<Album>, response: Response<Album>) {
                if (response.isSuccessful){
                    mRecycleView.visibility = View.VISIBLE
                    mErrorView.visibility = View.GONE

                    mSongAdapter.addData(response.body()!!.mData.songs, true)
                }else{
                    mRecycleView.visibility = View.GONE
                    mErrorView.visibility = View.VISIBLE
                }

                mRefresher.isRefreshing = false
            }
        })
    }
}
