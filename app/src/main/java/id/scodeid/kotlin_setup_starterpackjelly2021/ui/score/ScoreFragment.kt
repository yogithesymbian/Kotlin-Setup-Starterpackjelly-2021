package id.scodeid.kotlin_setup_starterpackjelly2021.ui.score

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates
import kotlinx.coroutines.*
import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.score.ScoresItem
import id.scodeid.kotlin_setup_starterpackjelly2021.databinding.FragmentScoreBinding
import id.scodeid.kotlin_setup_starterpackjelly2021.ui.score.detail.ScoreDetailActivity
import id.scodeid.kotlin_setup_starterpackjelly2021.utils.gone
import id.scodeid.kotlin_setup_starterpackjelly2021.utils.visible


class ScoreFragment : Fragment(), ScoreView {

    private lateinit var binding: FragmentScoreBinding

    private val observer =
        Observer<MutableList<ScoresItem>> { item ->
            if (item != null)
                scoreAdapter.setData(item)
            binding.pgBar.gone()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageScoreAdapter = 1
        totalPageScoreAdapter = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        return inflater.inflate(R.layout.fragment_score, container, false)
        binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scoreAdapter = ScoreAdapter(mutableList) {
            Log.d(TAG_LOG, "clicked ${it.score}")
            try {
                val intent = Intent(requireContext(), ScoreDetailActivity::class.java)
                intent.putExtra(ScoreDetailActivity.EXTRA_SCORE_DATA, it)
                requireContext().startActivity(intent)
            } catch (e: Exception) {
                Log.d(TAG_LOG, "clicked $e")
            }
        }
        initListenerScoreAdapter()

    }

    private fun initListenerScoreAdapter() {
        binding.rvScore
            .addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val countItem = linearLayoutManager.itemCount
                    val lastVisiblePosition =
                        linearLayoutManager.findLastCompletelyVisibleItemPosition()
                    val isLastPosition = countItem.minus(1) == lastVisiblePosition
                    if (isLastPosition && pageScoreAdapter < totalPageScoreAdapter) {
                        pageScoreAdapter = pageScoreAdapter.plus(1)
                        val viewModel: ScoreViewModel by viewModels { ScoreViewModelFactory(this@ScoreFragment) }
//                        viewModel.showScoreViewModel(requireContext(), pageScoreAdapter.toString())
                        viewModel.showScoreViewModel(requireContext())
                    }
                }
            })
    }


    override fun onResume() {
        super.onResume()
        GlobalScope.launch {
            showScoreViewModel()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private suspend fun showScoreViewModel() {
        try {

            withContext(Dispatchers.Main) {
                scoreAdapter.notifyDataSetChanged()
                binding.rvScore.itemAnimator = DefaultItemAnimator()
                binding.rvScore.setHasFixedSize(true)
                binding.rvScore.layoutManager = LinearLayoutManager(requireContext())
                binding.rvScore.adapter = scoreAdapter
            }

            val viewModel: ScoreViewModel by viewModels { ScoreViewModelFactory(this) }

            withContext(Dispatchers.Main) {
                viewModel.liveData().observe(viewLifecycleOwner, observer)
                if (scoreAdapter.itemCount == 0)
                    viewModel.showScoreViewModel(requireContext())
//                    viewModel.showScoreViewModel(requireContext(), pageScoreAdapter.toString())
            }
        } catch (e: Exception) {
            Log.d(TAG_LOG, "exception request ${e.printStackTrace()}")
        }
    }

    override fun showLoadingScoreView() {
        binding.pgBar.visible()
    }

    override fun hideLoadingScoreView() {
        binding.pgBar.gone()
    }

    override fun paginationTotalPageScoreView(totalPageDef: Int) {
        totalPageScoreAdapter = totalPageDef
    }

    override fun showSingleScoreView(data: ScoresItem?) {
        TODO("Not yet implemented")
    }

    override fun showScoreView(data: MutableList<ScoresItem>?) {
        GlobalScope.launch {
            mutableList.clear()
            val assign = async {
                data?.let {
                    mutableList.addAll(it)
                }
            }
            assign.await()
        }
    }

    companion object {
        val TAG_LOG: String = ScoreFragment::class.java.simpleName
        lateinit var scoreAdapter: ScoreAdapter
        private var mutableList: MutableList<ScoresItem> = mutableListOf()

        // pagination
        private var pageScoreAdapter by Delegates.notNull<Int>()
        private var totalPageScoreAdapter by Delegates.notNull<Int>()
    }

}