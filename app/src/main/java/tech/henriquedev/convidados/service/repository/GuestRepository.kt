package tech.henriquedev.convidados.service.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import tech.henriquedev.convidados.service.constants.DatabaseConstants
import tech.henriquedev.convidados.service.model.GuestModel

class GuestRepository(context: Context) {

    // acesso ao banco de dados
    private val mDatabase = GuestDatabase.getDatabase(context).guestDAO()

    fun get(id: Int): GuestModel {
        return mDatabase.get(id)
    }

    fun save(guest: GuestModel): Boolean {
        return mDatabase.save(guest) > 0
    }

    fun update(guest: GuestModel): Boolean {
        return mDatabase.update(guest) > 0
    }

    fun delete(guest: GuestModel) {
        return mDatabase.delete(guest)
    }

    fun getAll(): List<GuestModel> {
        return mDatabase.getInvited()
    }


    fun getPresent(): List<GuestModel> {
        return mDatabase.getPresent()
    }

    fun getAbsent(): List<GuestModel> {
        return mDatabase.getAbsent()
    }
}