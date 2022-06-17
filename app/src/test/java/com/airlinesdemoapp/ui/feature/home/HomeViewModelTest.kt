package com.airlinesdemoapp.ui.feature.home

import com.airlinesdemoapp.data.api_response.Response
import com.airlinesdemoapp.domain.interactor.DeleteUser
import com.airlinesdemoapp.domain.interactor.GetAirlines
import com.airlinesdemoapp.domain.interactor.UpdateData
import com.airlinesdemoapp.domain.interactor.UpdateUser
import com.airlinesdemoapp.ui.feature.details.DetailsViewModel
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

internal class HomeViewModelTest {
    private lateinit var viewModel: HomeViewModel
    private lateinit var getAirlines: GetAirlines
    private lateinit var deleteUser: DeleteUser
    private lateinit var responseTxt:String
    private lateinit var response:Response
    @Before
    fun setUp() {
        getAirlines = mock(GetAirlines::class.java)
        deleteUser = mock(DeleteUser::class.java)
        viewModel = HomeViewModel(getAirlines, deleteUser)
        val gson = Gson()
        responseTxt = "{\"page\":2,\"per_page\":6,\"total\":12,\"total_pages\":2,\"data\":[{\"id\":7,\"email\":\"michael.lawson@reqres.in\",\"first_name\":\"Michael\",\"last_name\":\"Lawson\",\"avatar\":\"https://reqres.in/img/faces/7-image.jpg\"},{\"id\":8,\"email\":\"lindsay.ferguson@reqres.in\",\"first_name\":\"Lindsay\",\"last_name\":\"Ferguson\",\"avatar\":\"https://reqres.in/img/faces/8-image.jpg\"},{\"id\":9,\"email\":\"tobias.funke@reqres.in\",\"first_name\":\"Tobias\",\"last_name\":\"Funke\",\"avatar\":\"https://reqres.in/img/faces/9-image.jpg\"},{\"id\":10,\"email\":\"byron.fields@reqres.in\",\"first_name\":\"Byron\",\"last_name\":\"Fields\",\"avatar\":\"https://reqres.in/img/faces/10-image.jpg\"},{\"id\":11,\"email\":\"george.edwards@reqres.in\",\"first_name\":\"George\",\"last_name\":\"Edwards\",\"avatar\":\"https://reqres.in/img/faces/11-image.jpg\"},{\"id\":12,\"email\":\"rachel.howell@reqres.in\",\"first_name\":\"Rachel\",\"last_name\":\"Howell\",\"avatar\":\"https://reqres.in/img/faces/12-image.jpg\"}],\"support\":{\"url\":\"https://reqres.in/#support-heading\",\"text\":\"To keep ReqRes free, contributions towards server costs are appreciated!\"}}"
        response = gson.fromJson(responseTxt, Response::class.java)
    }
    @Test
    fun `verify list is not empty or equal null`(){
        assertNotEquals(null, response.data)
        assertNotEquals(0, response.data.size)
    }
    @Test
    fun `verify list size with total`(){
        assertNotEquals(0, response.per_page)
        assertEquals(response.per_page, response.data.size)
    }

}