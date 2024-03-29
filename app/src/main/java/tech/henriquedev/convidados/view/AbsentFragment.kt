package tech.henriquedev.convidados.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tech.henriquedev.convidados.R
import tech.henriquedev.convidados.databinding.FragmentAbsentBinding
import tech.henriquedev.convidados.service.constants.GuestConstants
import tech.henriquedev.convidados.view.adapter.GuestAdapter
import tech.henriquedev.convidados.view.listener.GuestListener
import tech.henriquedev.convidados.viewmodel.GuestsViewModel

class AbsentFragment : Fragment() {

    private lateinit var mViewModel: GuestsViewModel
    private lateinit var mListener: GuestListener
    private var _binding: FragmentAbsentBinding? = null

    private val mAdapter: GuestAdapter = GuestAdapter()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel =
            ViewModelProvider(this).get(GuestsViewModel::class.java)

        _binding = FragmentAbsentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // RecyclerView
        // 1 - Obter a recycler
        val recycler = root.findViewById<RecyclerView>(R.id.recycler_absent)

        // 2 - Definir um layout
        recycler.layoutManager = LinearLayoutManager(root.context)

        // 3 - Definir um adapter
        recycler.adapter = mAdapter

        mListener = object : GuestListener {
            override fun onClick(id: Int) {
                val intent = Intent(context, GuestFormActivity::class.java)

                val bundle = Bundle()
                bundle.putInt(GuestConstants.GUEST_ID, id)

                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun onDelete(id: Int) {
                mViewModel.delete(id)
                mViewModel.load(GuestConstants.FILTER.ABSENT)
            }
        }

        mAdapter.attachListener(mListener)

        observe()
        mViewModel.load(GuestConstants.FILTER.ABSENT)

        return root
    }

    private fun observe() {
        mViewModel.guestList.observe(viewLifecycleOwner, Observer { guests ->
            mAdapter.updateGuests(guests)
        })
    }

    override fun onResume() {
        super.onResume()
        mViewModel.load(GuestConstants.FILTER.ABSENT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}