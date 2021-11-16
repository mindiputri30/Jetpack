package com.mindi.movies.ui.tv_show

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindi.movies.R
import com.mindi.movies.data.room.TvEntity
import com.mindi.movies.data.source.ViewModelFactory
import com.mindi.movies.databinding.FragmentTvShowBinding
import com.mindi.movies.ui.adapter.TvAdapter
import com.mindi.movies.ui.favorite.FavoriteActivity
import com.mindi.movies.utils.SortUtil.RANDOM
import com.mindi.movies.utils.SortUtil.VOTE_BEST
import com.mindi.movies.utils.SortUtil.VOTE_WORST
import com.mindi.movies.vo.Resource
import com.mindi.movies.vo.Status


class TvShowFragment : Fragment() {

    private var _binding: FragmentTvShowBinding? = null
    private val fragmentTvBinding get() = _binding!!

    private lateinit var viewModel: TvViewModel
    private lateinit var tvAdapter: TvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return fragmentTvBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[TvViewModel::class.java]
            tvAdapter = TvAdapter()

            fragmentTvBinding.progressBar.visibility = View.VISIBLE
            viewModel.getTvShow(VOTE_BEST).observe(viewLifecycleOwner, movieObserver)
            with(fragmentTvBinding.rvTvShow) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = this@TvShowFragment.tvAdapter
            }
        }
    }

    private val movieObserver = Observer<Resource<PagedList<TvEntity>>> { tv ->
        if (tv != null) {
            when (tv.status) {
                Status.LOADING -> showProgressBar(true)
                Status.SUCCESS -> {
                    showProgressBar(false)
                    tvAdapter.submitList(tv.data)
                    tvAdapter.notifyDataSetChanged()
                }
                Status.ERROR -> {
                    showProgressBar(false)
                    Toast.makeText(context, getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu, menu)
        inflater.inflate(R.menu.favorite_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""

        when (item.itemId) {
            R.id.action_bookmark -> sort = VOTE_BEST
            R.id.action_bookmark2 -> sort = VOTE_WORST
            R.id.action_bookmark3 -> sort = RANDOM
            R.id.favorite_menu -> startActivity(Intent(requireContext(), FavoriteActivity::class.java))
        }

        viewModel.getTvShow(sort).observe(viewLifecycleOwner, movieObserver)
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    private fun showProgressBar(state: Boolean) {
        fragmentTvBinding.progressBar.isVisible = state
        fragmentTvBinding.rvTvShow.isInvisible = state
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}