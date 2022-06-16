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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var appNavigator: AppNavigator
    private val viewModel:HomeViewModel  by viewModels()
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        vGetAirLines()
        setupObservers()
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
                    vGetAirLines()
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
                    addToListView(it.data)
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

    private fun addToListView(data: List<UserInfo>) {
        val newLines = viewModel.getNewAirLines(data)
        newLines.forEach { line ->
            viewModel.airlines[line.id] = line
        }
        adapter.submitList(newLines)
    }

    private fun handleLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun displayMessage(msg: String) {
        msg.let {
            Snackbar.make(mainView, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun vGetAirLines() {
        viewModel.resetAirlineState()
        viewModel.getAirlines(1)
    }

    private fun setupUI() {
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = HomeAdapter(requireActivity() as AppCompatActivity,
            object : HomeAdapter.OnClickInterface {
                override fun onClickRow(current: UserInfo) {
                    val airline = UserInfo(current.id, current.email, current.first_name,
                        current.last_name, current.avatar)
                    appNavigator.navigateTo(Screen.DETAILS, airline)
                }

                override fun onDeleteItem(current: UserInfo) {
                    viewModel.resetDeleteState()
                    viewModel.deleteAirLine(current)
                }

            })
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

}