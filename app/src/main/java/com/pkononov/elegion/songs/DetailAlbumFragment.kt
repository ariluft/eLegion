package com.pkononov.elegion.songs

import android.annotation.SuppressLint
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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailAlbumFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var mRecycleView: RecyclerView

    private lateinit var mRefresher: SwipeRefreshLayout
    private lateinit var mErrorView: View

    private lateinit var mAlbum: Album.Companion.DataBean

    private val mSongAdapter = SongsAdapter()

    companion object {
        val ALBUM_KEY: String = "ALBUM_KEY"
        fun newInstance(album: Album.Companion.DataBean): DetailAlbumFragment {
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
        mRefresher.setOnRefreshListener(this)
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
        mRefresher.post {
            mRefresher.isRefreshing = true
            getAlbum()
        }
    }

    @SuppressLint("CheckResult")
    private fun getAlbum() {
        ApiUtils.getApi()
            .getAlbum(mAlbum.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                mRefresher.isRefreshing = false
            }
            .doFinally {
                mRefresher.isRefreshing = false
            }
            .subscribe({
                mRecycleView.visibility = View.VISIBLE
                mErrorView.visibility = View.GONE

                mSongAdapter.addData(it.mData.songs, true)
            }, {
                mRecycleView.visibility = View.GONE
                mErrorView.visibility = View.VISIBLE
            })
    }
}
