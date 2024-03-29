package tech.henriquedev.convidados.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_guest_form.*
import kotlinx.android.synthetic.main.activity_guest_form.view.*
import tech.henriquedev.convidados.R
import tech.henriquedev.convidados.service.constants.GuestConstants
import tech.henriquedev.convidados.viewmodel.GuestFormViewModel

class GuestFormActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mViewModel: GuestFormViewModel
    private var mGuestId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_form)

        mViewModel = ViewModelProvider(this).get(GuestFormViewModel::class.java)

        setListeners()
        observe()
        loadData()

        radio_presence.isChecked = true
    }

    override fun onClick(view: View) {
        val id = view.id

        if (id == R.id.button_save) {
            val name = edit_name.text.toString()
            val presence = radio_presence.isChecked

            mViewModel.save(mGuestId, name, presence)
        }
    }

    private fun loadData() {
        val bundle = intent.extras
        if (bundle != null) {
            mGuestId = bundle.getInt(GuestConstants.GUEST_ID)

            mViewModel.load(mGuestId)
        }
    }

    private fun observe() {
        mViewModel.saveGuest.observe(this, Observer { success ->
            if (success) {
                Toast.makeText(applicationContext, "Sucesso", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(applicationContext, "Falha", Toast.LENGTH_SHORT).show()
                finish()
            }
        })

        mViewModel.guest.observe(this, Observer { guest ->
            edit_name.setText(guest.name)
            if(guest.presence) {
                radio_presence.isChecked = true
            } else {
                radio_absent.isChecked = true
            }
        })
    }

    private fun setListeners() {
        button_save.setOnClickListener(this)
    }
}