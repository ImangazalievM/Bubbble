package com.bubbble.coreui.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bubbble.coreui.R
import com.bubbble.coreui.databinding.ItemNetworkStateBinding

class LoadingStateAdapter(
    private val retryListener: () -> Unit
) : LoadStateAdapter<LoadingStateAdapter.LoaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoaderViewHolder(
            ItemNetworkStateBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_network_state, parent, false)
            ),
            retryListener
        )

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    class LoaderViewHolder(
        private val binding: ItemNetworkStateBinding,
        private val retryListener: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retryListener() }
        }

        fun bind(loadState: LoadState) = with(binding) {
            Log.d("Bubbble", """STATE: ${loadState.javaClass.simpleName}
                loadMoreProgress: ${loadState is LoadState.Loading}
                retryButton: ${loadState is LoadState.Loading}
                errorMsg: ${
                !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                }
            """.trimIndent())
            loadMoreLayout.isVisible = loadState is LoadState.Loading
            loadMoreProgress.isVisible = loadState is LoadState.Loading
            loadMoreErrorLayout.isVisible = loadState is LoadState.Error
            retryButton.isVisible = loadState is LoadState.Error
            errorMsg.isVisible =
                !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            errorMsg.text = (loadState as? LoadState.Error)?.error?.message
        }
    }
}