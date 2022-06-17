package com.airlinesdemoapp.ui.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.airlinesdemoapp.R
import com.airlinesdemoapp.core.common.DataState
import com.airlinesdemoapp.core.navigation.AppNavigator
import com.airlinesdemoapp.core.navigation.Screen
import com.airlinesdemoapp.domain.entity.UserInfo
import com.airlinesdemoapp.domain.error.Failure
import com.airlinesdemoapp.utils.PaginationScrollListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var pageNo: Int = 1

    @Inject
    lateinit var appNavigator: AppNavigator
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var adapter: HomeAdapter
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupClickListener()
        setupObservers()
    }

    private fun setupClickListener() {
        button.setOnClickListener {
            vGetAirLines(pageNo)
        }
    }

    private fun setupObservers() {
        observerAirlines()
        observerDelete()
    }

    private fun observerDelete() {
        viewModel.deleteState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    handleLoading(false)
                    displayMessage(it.data)
                    vGetAirLines(pageNo)
                }
                is DataState.Error -> {
                    handleLoading(false)
                    val string: String = if (it.error is Failure.NetworkConnectionError)
                        getString(R.string.network_connection)
                    else
                        getString(R.string.unknown_error)
                    displayMessage(string)
                }
                is DataState.loading -> {
                    handleLoading(true)
                }
            }
        }
    }

    private fun observerAirlines() {
        viewModel.airlinesState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    handleLoading(false)
                    adapter.addData(it.data.list)
                    isLoading = pageNo < it.data.total
                    isLastPage = pageNo > it.data.total
                    if (isLoading) {
                        pageNo++
                        getMoreItems()
                    }
                }
                is DataState.Error -> {
                    handleLoading(false)
                    vHandleInternetConnection()
                    val string: String = if (it.error is Failure.NetworkConnectionError)
                        getString(R.string.network_connection)
                    else
                        getString(R.string.unknown_error)
                    displayMessage(string)
                }
                is DataState.loading -> {
                    handleLoading(true)
                }
            }
        }
    }

    private fun vHandleInternetConnection() {
        internetconnection.visibility = if (adapter.list.size == 0) View.VISIBLE else View.GONE
    }


    private fun handleLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        recyclerView.visibility = if (adapter.list.size == 0) View.GONE else View.VISIBLE
        internetconnection.visibility = View.GONE
    }

    private fun displayMessage(msg: String) {
        msg.let {
            Snackbar.make(mainView, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun vGetAirLines(_pageNo: Int) {
        viewModel.resetAirlineState()
        viewModel.getAirlines(_pageNo)
    }

    private fun setupUI() {
        val layout = LinearLayoutManager(requireActivity())
        recyclerView.layoutManager = layout
        adapter = HomeAdapter(
            requireActivity() as AppCompatActivity,
            object : HomeAdapter.OnClickInterface {
                override fun onClickRow(current: UserInfo) {
                    val airline = UserInfo(
                        current.id, current.email, current.first_name,
                        current.last_name, current.avatar
                    )
                    appNavigator.navigateTo(Screen.DETAILS, airline)
                }

                override fun onDeleteItem(current: UserInfo) {
                    viewModel.resetDeleteState()
                    viewModel.deleteAirLine(current)
                }

            }, ArrayList()
        )
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter

        recyclerView?.addOnScrollListener(object : PaginationScrollListener(layout) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                pageNo++
                //you have to call loadmore items to get more data
                getMoreItems()
            }
        })
    }

    fun getMoreItems() {
        //after fetching your data assuming you have fetched list in your
        // recyclerview adapter assuming your recyclerview adapter is
        //rvAdapter
        isLoading = false
        vGetAirLines(pageNo)
    }

    override fun onResume() {
        super.onResume()
        resetFlags()
    }

    private fun resetFlags() {
        isLoading = false
        isLastPage = false
        pageNo = 1
        adapter.list.clear()
        vGetAirLines(pageNo)
    }
}