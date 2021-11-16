package com.mindi.movies.ui.movies

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.mindi.movies.R
import com.mindi.movies.data.room.MovieEntity
import com.mindi.movies.data.source.ViewModelFactory
import com.mindi.movies.databinding.FragmentMoviesBinding
import com.mindi.movies.ui.adapter.MoviesAdapter
import com.mindi.movies.ui.favorite.FavoriteActivity
import com.mindi.movies.utils.SortUtil.RANDOM
import com.mindi.movies.utils.SortUtil.VOTE_BEST
import com.mindi.movies.utils.SortUtil.VOTE_WORST
import com.mindi.movies.vo.Resource
import com.mindi.movies.vo.Status

class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val fragmentMovieBinding get() = _binding!!

    private lateinit var viewModel: MovieViewModel
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {

            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            moviesAdapter = MoviesAdapter()

            fragmentMovieBinding.progressBar.visibility = View.VISIBLE
            viewModel.getMovies(VOTE_BEST).observe(viewLifecycleOwner, movieObserver)

            with(fragmentMovieBinding.rvMovies) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = this@MoviesFragment.moviesAdapter
            }
        }
    }

    private val movieObserver = Observer<Resource<PagedList<MovieEntity>>> { movies ->
        if (movies != null) {
            when (movies.status) {
                Status.LOADING -> showProgressBar(true)
                Status.SUCCESS -> {
                    showProgressBar(false)
                    moviesAdapter.submitList(movies.data)
                    moviesAdapter.notifyDataSetChanged()
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

        viewModel.getMovies(sort).observe(viewLifecycleOwner, movieObserver)
        item.isChecked = true
        return super.onOptionsItemSelected(item)
    }

    private fun showProgressBar(state: Boolean) {
        fragmentMovieBinding.progressBar.isVisible = state
        fragmentMovieBinding.rvMovies.isInvisible = state
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}