package com.example.deber03

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Inicio : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_inicio, container, false)

        // Crear adaptador para el primer RecyclerView
        val adapterMovements = AdapterMovements()

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_movements)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapterMovements

        // Datos para el RecyclerView de contactos
        val titlesContactos = arrayOf("TRANS EXPRESS INC.", "JUAN GALINDEZ", "PEDRO QUINTANA",
            "ANDRES PEREZ", "BELEN JULIO")
        val imagesContactos = intArrayOf(R.drawable.icon_shop, R.drawable.icon_name_jg,
            R.drawable.icon_name_pq, R.drawable.icon_name_ap, R.drawable.icon_name_bj)

        // Crear adaptador para el RecyclerView de contactos
        val adapterContactos = AdapterContacts(titlesContactos, imagesContactos)

        val recyclerViewContactos = rootView.findViewById<RecyclerView>(R.id.rc_contactos)
        recyclerViewContactos.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerViewContactos.adapter = adapterContactos

        return rootView
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            Inicio().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
