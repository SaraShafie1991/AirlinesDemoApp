package com.airlinesdemoapp.ui.feature.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airlinesdemoapp.R
import com.airlinesdemoapp.domain.entity.UserInfo
import kotlinx.android.synthetic.main.fragment_details.*

private const val ARG_AIRLINES = "airline"

class DetailsFragment : Fragment() {

    private var airline: UserInfo? = null
    private lateinit var viewModel: DetailsViewModel

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
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        airline?.first_name?.let {
            name?.text =
                if (NAME.isNotEmpty())
                    NAME.plus(it).plus(" ${airline?.last_name}")
                else NAME.plus(NA)
        }
        airline?.email?.let {
            address?.text =
                if (ADDRESS.isNotEmpty())
                    ADDRESS.plus(it)
                else ADDRESS.plus(NA)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param restaurant Parameter Restaurant.
         * @return A new instance of fragment RestaurantDetails.
         */
        // TODO: Rename and change types and number of parameters
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