package com.airlinesdemoapp.ui.feature.details

import com.airlinesdemoapp.domain.interactor.UpdateData
import com.airlinesdemoapp.domain.interactor.UpdateUser
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class DetailsViewModelTest {
    private lateinit var viewModel: DetailsViewModel
    private lateinit var updateUser: UpdateUser

    @Before
    fun setUp() {
        updateUser = mock(UpdateUser::class.java)
        viewModel = DetailsViewModel(updateUser)
        viewModel.updateUserFromFragmentValue(UpdateData(5, "name"))
    }

    @Test
    fun `verify that the updated name not null or empty`() {
        assertNotEquals(null, viewModel.updateUserFromFragment.text)
        assertNotEquals("", viewModel.updateUserFromFragment.text)
    }

    @Test
    fun `verify that user id is valid`() {
        assertNotEquals(-1, viewModel.updateUserFromFragment.id)
    }
}