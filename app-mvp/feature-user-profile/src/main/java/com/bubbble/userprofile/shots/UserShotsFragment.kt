package com.bubbble.userprofile.shots

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bubbble.userprofile.R
import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.ui.base.BaseMvpFragment
import com.bubbble.ui.extensions.isVisible
import com.bubbble.userprofile.databinding.FragmentUserShotsBinding
import com.bubbble.coreui.navigationargs.createFragment
import com.bubbble.coreui.navigationargs.getScreenData
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class UserShotsFragment : BaseMvpFragment(), UserShotsView {

    override val layoutRes: Int = R.layout.fragment_user_shots

    @Inject
    lateinit var presenterFactory: UserShotsPresenter.Factory

    val presenter by moxyPresenter {
        val userName = getScreenData<UserShotsScreenData>().userName
        presenterFactory.create(userName)
    }

    private val binding: FragmentUserShotsBinding by viewBinding()

    //private val shotsAdapter: ShotsAdapter by lazy {
    //    //ShotsAdapter(requireContext())
    //}
    private val shotsListLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.noNetworkLayout.retryButton
        binding.shotsList.layoutManager = shotsListLayoutManager
        //shotsRecyclerView.adapter = shotsAdapter
        // shotsAdapter.setOnItemClickListener { position: Int ->
        //     presenter.onShotClick(position)
        // }
        //  shotsRecyclerView.addOnScrollListener(object :
        //      EndlessRecyclerOnScrollListener(shotsListLayoutManager) {
        //      override fun onLoadMore() {
        //          presenter.onLoadMoreShotsRequest()
        //      }
        //  })
    }

    override fun showNewShots(newShots: List<Shot>) {
        binding.shotsList.visibility = View.VISIBLE
        ///shotsAdapter.addItems(newShots)
    }

    override fun showShotsLoadingProgress(isVisible: Boolean) {
        binding.loadingLayout.isVisible = isVisible
    }

    override fun showShotsLoadingMoreProgress(isVisible: Boolean) {
        // shotsAdapter.setLoadingMore(isVisible)
    }

    override fun showNoNetworkLayout(isVisible: Boolean) {
        binding.noNetworkLayout.isVisible = isVisible
    }

    override fun showLoadMoreError() {
        // shotsAdapter.setLoadingError(true)
    }

    companion object {
        @JvmStatic
        fun newInstance(
            userName: String
        ) = createFragment<UserShotsFragment>(UserShotsScreenData(userName))
    }
}