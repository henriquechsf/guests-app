package tech.henriquedev.convidados.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tech.henriquedev.convidados.service.constants.GuestConstants
import tech.henriquedev.convidados.service.model.GuestModel
import tech.henriquedev.convidados.service.repository.GuestRepository

class GuestsViewModel(application: Application) : AndroidViewModel(application) {
    private val mGuestRepository = GuestRepository.getInstance(application.applicationContext)

    private val mGuestList = MutableLiveData<List<GuestModel>>()
    val guestList: LiveData<List<GuestModel>> = mGuestList

    fun load(filter: Int) {
        when (filter) {
            GuestConstants.FILTER.PRESENT -> mGuestList.value = mGuestRepository.getPresent()
            GuestConstants.FILTER.ABSENT -> mGuestList.value = mGuestRepository.getAbsent()
            else -> mGuestList.value = mGuestRepository.getAll()
        }
    }

    fun delete(id: Int) {
        mGuestRepository.delete(id)
    }
}