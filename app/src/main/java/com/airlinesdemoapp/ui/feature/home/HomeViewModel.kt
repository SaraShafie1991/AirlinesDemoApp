package com.airlinesdemoapp.ui.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airlinesdemoapp.core.common.BaseViewModel
import com.airlinesdemoapp.core.common.DataState
import com.airlinesdemoapp.data.api_response.ApiResponse
import com.airlinesdemoapp.domain.entity.UserInfo
import com.airlinesdemoapp.domain.interactor.DeleteUser
import com.airlinesdemoapp.domain.interactor.GetAirlines
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getAirlines: GetAirlines,
                                        private val deleteUser: DeleteUser)
    : BaseViewModel() {

    private val _airlinesState = MutableLiveData<DataState<List<UserInfo>>>()

    val airlinesState: LiveData<DataState<List<UserInfo>>>
        get() = _airlinesState

    private val _deleteState = MutableLiveData<DataState<String>>()

    val deleteState: LiveData<DataState<String>>
        get() = _deleteState

    val airlines = HashMap<Int, UserInfo>()

    fun deleteAirLine(current: UserInfo) {
        if (_deleteState.value != null) return
        _deleteState.value = DataState.loading
        deleteUser.execute(current.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { response ->
                _deleteState.value = response
            }
            .also {
                compositeDisposable.add(it)
            }
    }
    fun resetDeleteState() {
        _deleteState.value = null
    }
    fun getAirlines(pageNo:Int) {
        if (_airlinesState.value != null) return
        _airlinesState.value = DataState.loading
        getAirlines.execute(pageNo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { airlines ->
                _airlinesState.value = airlines
            }
            .also {
                compositeDisposable.add(it)
            }
    }

    fun resetAirlineState() {
        _airlinesState.value = null
    }
    fun getNewAirLines(list: List<UserInfo>):ArrayList<UserInfo>{
        val airlinesToBeDisplayed = ArrayList<UserInfo>()
        val mainList = airlines.values
        if(mainList.isNotEmpty()) {
            list.forEach {
                if (mainList.contains(it))
                    airlinesToBeDisplayed.add(it)
            }
        }else{
            airlinesToBeDisplayed.addAll(list)
        }
        return  airlinesToBeDisplayed
    }
}