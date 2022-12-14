package ni.edu.uca.listadoprod.dataadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import ni.edu.uca.listadoprod.dataclass.Producto
import ni.edu.uca.listadoprod.databinding.ItemlistaBinding


class ProductoAdapter(val listProd: List<Producto>,
                      private val onClickLst: (Producto) -> Unit,
                      private val onClickDlt: (Int) -> Unit,
                      private val onClickUpt: (Int) -> Unit) :

    RecyclerView.Adapter<ProductoAdapter.ProductoHolder>() {
    inner class ProductoHolder(val binding: ItemlistaBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun cargar(producto: Producto, onClickListener: (Producto) -> Unit,
                       onClickDelete: (Int) -> Unit,
                       onClickUpdate: (Int) -> Unit )  {

                with(binding){
                    tvCodProd.text = producto.id.toString()
                    tvNombreprod.text = producto.nombre
                    tvPrecio.text = producto.precio.toString()
                    itemView.setOnClickListener { onClickListener(producto) }
                    binding.btnEliminar.setOnClickListener { onClickDelete(adapterPosition) }
                    binding.btnEditar.setOnClickListener { onClickUpdate(adapterPosition) }

                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val binding = ItemlistaBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ProductoHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoHolder, position: Int) {
        holder.cargar(listProd[position],onClickLst, onClickDlt, onClickUpt)

    }

    override fun getItemCount(): Int = listProd.size

}