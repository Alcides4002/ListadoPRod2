package ni.edu.uca.listadoprod

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.listadoprod.dataadapter.ProductoAdapter
import ni.edu.uca.listadoprod.dataclass.Producto
import ni.edu.uca.listadoprod.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var listaProd = ArrayList<Producto>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()

    }

    private fun limpiar() {
        with(binding) {
            etID.setText("")
            etNombre.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarProd() {
        with(binding) {
            try {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombre.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)
            } catch (ex: Exception) {
                Toast.makeText(
                    this@MainActivity, "error:${ex.toString()}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listaProd,
                { producto -> seleccionar(producto) },
                { position -> borrar(position) },
                { position -> actualizar(position) })
            limpiar()
        }
    }

    private fun iniciar() {
        binding.btnAgregar.setOnClickListener {
            agregarProd()
        }
        binding.btnLimpiar.setOnClickListener {
            limpiar()
        }
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)


    fun borrar(position: Int) {
        try {

            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("Eliminar Producto")
            dialog.setMessage("¿Quiere eleminar el registro?")
            dialog.setPositiveButton("SI", DialogInterface.OnClickListener { _, _ ->
                with(binding) {
                    listaProd.removeAt(position)
                    rcvLista.adapter?.notifyItemRemoved(position)
                }

            })
            dialog.setNegativeButton("NO", DialogInterface.OnClickListener { _, _ ->
                Toast.makeText(this, "No se elemino", Toast.LENGTH_SHORT).show()

            })
            dialog.show()
        } catch (ex: Exception) {
            Toast.makeText(
                this@MainActivity, "Error: ${ex.toString()} ",
                Toast.LENGTH_LONG
            ).show()

        }
    }

    fun actualizar(position: Int) {
        try {
            with(binding) {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombre.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.set(position, prod)
                rcvLista.adapter?.notifyItemChanged(position)
            }
        } catch (ex: Exception) {
            Toast.makeText(
                this@MainActivity, "Error: ${ex.toString()} ",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun seleccionar(producto: Producto) {
        with(binding) {
            etID.text = producto.id.toString().toEditable()
            etNombre.text = producto.nombre.toEditable()
            etPrecio.text = producto.precio.toString().toEditable()
        }
    }
}


