package net.azarquiel.recyclerviewProductos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.azarquiel.listacompra.R
import net.azarquiel.listacompra.model.Producto

/**
 * Created by pacopulido on 9/10/18.
 */
class CustomAdapter(val context: Context,
                    val layout: Int
                    ) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    private var dataList: List<Producto> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewlayout = layoutInflater.inflate(layout, parent, false)
        return ViewHolder(viewlayout, context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
//setProductos con productos dentro minuscula porq es un objeto
    internal fun setProductos(productos: List<Producto>) {
        this.dataList =productos
        notifyDataSetChanged()
    }


    class ViewHolder(viewlayout: View, val context: Context) : RecyclerView.ViewHolder(viewlayout) {
        fun bind(dataItem: Producto){
            // itemview es el item de diseño
            // al que hay que poner los datos del objeto dataItem
            val tvrowproducto = itemView.findViewById(R.id.tvrowproducto) as TextView
            val tvrowcantidad = itemView.findViewById(R.id.tvrowcantidad) as TextView

            tvrowproducto.text = dataItem.nombre
            tvrowcantidad.text = dataItem.cantidad

            //siempre debe aparecer esta linea para que aparezca con el onclick
            itemView.tag = dataItem

        }

    }
}