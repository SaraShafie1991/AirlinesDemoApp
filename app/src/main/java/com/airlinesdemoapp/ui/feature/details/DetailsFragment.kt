package com.airlinesdemoapp.ui.feature.details

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.airlinesdemoapp.R
import com.airlinesdemoapp.core.common.DataState
import com.airlinesdemoapp.domain.entity.UserInfo
import com.airlinesdemoapp.domain.error.Failure
import com.airlinesdemoapp.domain.interactor.UpdateData
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_details.*

private const val ARG_AIRLINES = "airline"

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var updateData: UpdateData
    private var airline: UserInfo? = null

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            bundle.getParcelable<UserInfo>(ARG_AIRLINES).also { airline = it }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        setupClickListener()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.updateState.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success -> {
                    vChangeData()
                    handleLoading(false)
                    if (it.data != null && it.data.isNotEmpty())
                        displayMessage(it.data)
                    else
                        displayMessage("Name is updated successfully")
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

    private fun vChangeData() {
        textViewUserName?.text = NAME.plus(updateData.text)
    }

    private fun handleLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun displayMessage(msg: String) {
        msg.let {
            Snackbar.make(mainView, msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupClickListener() {
        imageViewInfolayout.setOnClickListener {
            vShowDialog()
        }
    }


    private fun vShowDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireActivity())
        // Set up the input
        val input = EditText(requireActivity())
        input.setHint("Enter new name")
        input.inputType = InputType.TYPE_CLASS_TEXT
        val param = input.layoutParams as? ViewGroup.MarginLayoutParams
        param?.setMargins(10,10,10,10)
        if(param != null) {
            input.layoutParams = param
        }
        builder.setView(input)
        // Set up the buttons
        builder.setPositiveButton("OK") { dialog, which ->
            // Here you get get input text from the Edittext
            val m_Text = input.text.toString()
            if (airline != null && m_Text.isNotEmpty() && m_Text.isNotBlank()) {
                updateData = UpdateData(airline!!.id, m_Text)
                viewModel.resetUpdateState()
                viewModel.updateUser(updateData)
            }
        }
        builder.setNegativeButton(
            "Cancel",
            { dialog, which -> dialog.cancel() })

        builder.show()
    }

    private fun loadData() {
        airline?.first_name?.let {
            textViewUserName?.text =
                if (NAME.isNotEmpty())
                    NAME.plus(it).plus(" ${airline?.last_name}")
                else NAME.plus(NA)
        }
        airline?.email?.let {
            textViewUserEmail?.text =
                if (ADDRESS.isNotEmpty())
                    ADDRESS.plus(it)
                else ADDRESS.plus(NA)
        }
        Glide.with(requireActivity())
            .load(airline?.avatar)
            .into(imageViewAvatar)
    }

    companion object {
        @JvmStatic
        fun newInstance(airline: UserInfo?) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_AIRLINES, airline)
                }
            }

        const val NAME = "Name: "
        const val ADDRESS = "Email Address: "
        const val NA = "N/A"
    }

}