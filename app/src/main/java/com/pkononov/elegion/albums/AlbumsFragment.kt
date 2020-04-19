package com.pkononov.elegion.albums

import android.annotation.SuppressLint
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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response

class AlbumsFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(album: Album.Companion.DataBean) {
            fragmentManager!!.beginTransaction()
                .replace(
                    R.id.fragmentContainer,
                    DetailAlbumFragment.newInstance(album)
                )
                .addToBackStack(DetailAlbumFragment::class.java.simpleName)
                .commit()
        }
    }

    private val mAlbumAdapter = AlbumsAdapter(onItemClickListener)


    private lateinit var mRecycleView: RecyclerView

    private lateinit var mRefresher: SwipeRefreshLayout
    private lateinit var mErrorView: View

    companion object {
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
        mRefresher.setOnRefreshListener(this)
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
        mRefresher.post {
            mRefresher.isRefreshing = true
            getAlbums()
        }
    }

    @SuppressLint("CheckResult")
    private fun getAlbums() {
        ApiUtils.getApi()
            .getAlbums()
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

                mAlbumAdapter.addData(it.data, true)
            }, {
                mRecycleView.visibility = View.GONE
                mErrorView.visibility = View.VISIBLE
            })
    }
}