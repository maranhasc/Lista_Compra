package net.azarquiel.listacompra.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import net.azarquiel.listacompra.R
import net.azarquiel.listacompra.model.Producto
import net.azarquiel.recyclerviewProductos.adapter.CustomAdapter
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var productos: ArrayList<Producto>
    private lateinit var adapter: CustomAdapter
    private var contador: Int = 0
    private lateinit var contadorSH: SharedPreferences
    private lateinit var listaSH: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{onclickfab()}





        listaSH = getSharedPreferences("listacompra", Context.MODE_PRIVATE)
        contadorSH = getSharedPreferences("contador", Context.MODE_PRIVATE)
        getcontador()

        initRV()
        getLista()
        adapter.setProductos(productos)
    }

    private fun onclickfab() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("New Product")
        val ll = LinearLayout(this)
        ll.setPadding(30,30,30,30)
        ll.orientation = LinearLayout.VERTICAL

        val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        lp.setMargins(0,50,0,50)

        val textInputLayoutNombre = TextInputLayout(this)
        textInputLayoutNombre.layoutParams = lp
        val etnombre = EditText(this)
        etnombre.setPadding(0, 80, 0, 80)
        etnombre.textSize = 20.0F
        etnombre.hint = "Producto"
        textInputLayoutNombre.addView(etnombre)

        val textInputLayoutCantidad = TextInputLayout(this)
        textInputLayoutCantidad.layoutParams = lp
        val etcantidad = EditText(this)
        etcantidad.setPadding(0, 80, 0, 80)
        etcantidad.textSize = 20.0F
        etcantidad.hint = "Cantidad"
        textInputLayoutCantidad.addView(etcantidad)
        ll.addView(textInputLayoutNombre)
        ll.addView(textInputLayoutCantidad)
        builder.setView(ll)

        builder.setPositiveButton("Aceptar") { dialog, which ->
            addProducto(Producto(incrementaContador(), etnombre.text.toString(),etcantidad.text.toString() ))
        }

        builder.setNegativeButton("Cancelar") { dialog, which ->
        }

        builder.show()

    }

    private fun initRV() {
        val rvlista = findViewById<RecyclerView>(R.id.rvlista)
        adapter= CustomAdapter(this, R.layout.rowproducto)
        rvlista.adapter = adapter
        rvlista.layoutManager = LinearLayoutManager(this)
    }

    private fun getcontador() {
        contador = contadorSH.getInt("c", -1)
        if (contador == -1) contador = 0
    }
    private fun incrementaContador():Int{
        contador++
        val edit = contadorSH.edit()
        edit.putInt("c", contador)
        edit.commit()
        return contador

    }

    private fun getLista() {
        val listaAll = listaSH.all
        productos = ArrayList<Producto>()
        for ((key,value) in listaAll) {
            val jsonProducto = value.toString()
            val producto = Gson().fromJson(jsonProducto, Producto::class.java)
            productos.add(producto)
        }


    }



    private fun addProducto(producto: Producto) {
        //save SH
        val editor = listaSH.edit()
        val productojson = Gson().toJson(producto)
        editor.putString("${producto.id.toString()}", productojson)
        editor.commit()
        //ponerlo en el RV
        productos.add(0, producto)
        adapter.setProductos(productos)
    }
}