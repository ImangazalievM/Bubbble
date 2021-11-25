package com.bubbble.presentation.userprofile.shots

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bubbble.R
import com.bubbble.core.models.shot.Shot
import com.bubbble.coreui.ui.base.BaseMvpFragment
import com.bubbble.shots.databinding.FragmentShotsBinding
import com.bubbble.ui.extensions.isVisible
import dagger.hilt.android.AndroidEntryPoint
import moxy.ktx.moxyPresenter
import javax.inject.Inject

@AndroidEntryPoint
class UserShotsFragment : BaseMvpFragment(), UserShotsView {

    override val layoutRes: Int = R.layout.fragment_shots

    @Inject
    lateinit var presenterFactory: UserShotsPresenter.Factory

    val presenter by moxyPresenter {
        val userName = requireArguments().getString(USER_NAME)!!
        presenterFactory.create(userName)
    }

    private val binding: FragmentShotsBinding by viewBinding()
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
        private const val USER_NAME = "user_name"

        @JvmStatic
        fun newInstance(userName: String): UserShotsFragment {
            val fragment = UserShotsFragment()
            val args = Bundle()
            args.putString(USER_NAME, userName)
            fragment.arguments = args
            return fragment
        }
    }
}