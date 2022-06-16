package com.airlinesdemoapp.ui.feature.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.airlinesdemoapp.core.common.BaseViewModel
import com.airlinesdemoapp.core.common.DataState
import com.airlinesdemoapp.domain.interactor.UpdateData
import com.airlinesdemoapp.domain.interactor.UpdateUser
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val updateUser: UpdateUser)
    : BaseViewModel() {

    private val _updateState = MutableLiveData<DataState<String>>()

    val updateState: LiveData<DataState<String>>
        get() = _updateState

    fun updateUser(current: UpdateData?) {
        if (_updateState.value != null) return
        if (current != null) {
            _updateState.value = DataState.loading
            updateUser.execute(current)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    _updateState.value = response
                }
                .also {
                    compositeDisposable.add(it)
                }
        }
    }
    fun resetUpdateState() {
        _updateState.value = null
    }
}