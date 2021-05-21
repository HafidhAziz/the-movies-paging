package com.example.sharingsessionpaging.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.sharingsessionpaging.BuildConfig
import com.example.sharingsessionpaging.R
import com.example.sharingsessionpaging.databinding.MovieItemBinding
import com.example.sharingsessionpaging.databinding.MovieItemSeperatorBinding
import com.example.sharingsessionpaging.ui.MovieModel

class MovieAdapter : PagingDataAdapter<MovieModel, RecyclerView.ViewHolder>(
    MovieModelComparator
) {

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val movieModel: MovieModel? = getItem(position)
        movieModel?.let {
            when (movieModel) {
                is MovieModel.MovieItem -> {
                    val viewHolder = holder as MovieViewHolder
                    val drawable = CircularProgressDrawable(viewHolder.movieItemBinding.view.context)
                    drawable.setColorSchemeColors(
                        R.color.colorPrimary,
                        android.R.color.white,
                        android.R.color.white
                    )
                    drawable.centerRadius = 30f
                    drawable.strokeWidth = 5f
                    drawable.start()
                    viewHolder.movieItemBinding.tvMovieTitle.text = movieModel.movie.original_title
                    viewHolder.movieItemBinding.tvMovieVoteCount.text =
                        "Vote count ${movieModel.movie.vote_count}"
                    Glide.with(viewHolder.movieItemBinding.ivMovieImage.context)
                        .load(BuildConfig.SMALL_IMAGE_URL + "" + movieModel.movie.poster_path)
                        .placeholder(drawable)
                        .into(viewHolder.movieItemBinding.ivMovieImage)
                }
                is MovieModel.SeparatorItem -> {
                    val viewHolder = holder as MovieSeparatorViewHolder
                    viewHolder.movieItemSeparatorBinding
                        .tvSeparatorDescription.text = movieModel.description
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is MovieModel.MovieItem -> R.layout.movie_item
            is MovieModel.SeparatorItem -> R.layout.movie_item_seperator
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.movie_item -> {
                MovieViewHolder(
                    MovieItemBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
            else -> {
                MovieSeparatorViewHolder(
                    MovieItemSeperatorBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

    class MovieViewHolder(val movieItemBinding: MovieItemBinding) :
        RecyclerView.ViewHolder(movieItemBinding.root)

    class MovieSeparatorViewHolder(val movieItemSeparatorBinding: MovieItemSeperatorBinding) :
        RecyclerView.ViewHolder(movieItemSeparatorBinding.root)

    companion object {
        private val MovieModelComparator = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return (oldItem is MovieModel.MovieItem && newItem is MovieModel.MovieItem &&
                        oldItem.movie.id == newItem.movie.id) ||
                        (oldItem is MovieModel.SeparatorItem && newItem is MovieModel.SeparatorItem &&
                                oldItem.description == newItem.description)
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean =
                oldItem == newItem
        }
    }

}
