package com.example.deber03

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterMovements: RecyclerView.Adapter<AdapterMovements.ViewHolder>() {

    val titles = arrayOf("TRANS EXPRESS INC.","JUAN GALINDEZ",
        "TRANS EXPRESS INC.","Epic Games Commerce GmbH")
    val details = arrayOf("","Pago de clases de ingles","Pago de Servicios","")
    val valores = arrayOf("-$43.,44","+$30,00","-$20,00","-$0,39")
    val fechas = arrayOf("29 ene","20 ene","19 ene","01 ene")
    val tipoMov = arrayOf("Pago","Dinero recibido", "Dinero enviado","Pago")
    val images = intArrayOf(R.drawable.icon_shop,R.drawable.icon_name_jg,
        R.drawable.icon_shop,R.drawable.icon_shop)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_mov, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemTitle.text = titles[i]
        viewHolder.itemDetail.text = details[i]
        viewHolder.itemValor.text = valores[i]
        viewHolder.itemFecha.text = fechas[i]
        viewHolder.itemTipoMov.text = tipoMov[i]
        viewHolder.itemImage.setImageResource(images[i])
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetail: TextView
        var itemValor: TextView
        var itemFecha: TextView
        var itemTipoMov: TextView

        init {
            itemImage = itemView.findViewById(R.id.imgv_icon_mov)
            itemTitle = itemView.findViewById(R.id.txt_title_mov)
            itemDetail = itemView.findViewById(R.id.txt_description_mov)
            itemValor = itemView.findViewById(R.id.txt_valor_mov)
            itemFecha = itemView.findViewById(R.id.txt_fecha_mov)
            itemTipoMov = itemView.findViewById(R.id.txt_tipo_mov)
        }
    }
}