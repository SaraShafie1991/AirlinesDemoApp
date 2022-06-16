package com.airlinesdemoapp.data.repository

import com.airlinesdemoapp.core.common.DataState
import com.airlinesdemoapp.data.api.RestAPI
import com.airlinesdemoapp.data.api_response.ApiResponse
import com.airlinesdemoapp.data.cache.InMemoryCache
import com.airlinesdemoapp.domain.entity.UserInfo
import com.airlinesdemoapp.domain.interactor.UpdateData
import com.airlinesdemoapp.domain.repository.Repository
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class RepositoryImpl(private val api: RestAPI) : Repository {
    override fun getAirlines(pageNo: Int): Single<DataState<List<UserInfo>>> {
        val cache = InMemoryCache.get()
        val filteredData = ArrayList<UserInfo>()

        cache.forEach {
            filteredData.add(it)
        }

        if (filteredData != null && filteredData.isNotEmpty()) {
            return Single.just(DataState.Success(filteredData))
        }
        return api.getAirLines(pageNo)
            .map {
                val restList = ArrayList<UserInfo>()
                it.data.forEach { rest ->
                    val newRestaurant = UserInfo(
                        id = rest.id,
                        first_name = rest.first_name,
                        last_name = rest.last_name,
                        email = rest.email,
                        avatar = rest.avatar,
                    )
                    restList.add(newRestaurant)
                }
                InMemoryCache.add(restList)
                DataState.Success(restList)
            }
    }

    override fun deleteUser(id: Int): Single<DataState<String>> {
        return api.deleteUser(id).map {
            DataState.Success(it.message)
        }
    }

    override fun updateUser(updateData: UpdateData): Single<DataState<String>> {
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("name", updateData.text)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        return api.updateUser(updateData.id, requestBody).map {
            DataState.Success(it.message)
        }
    }
}