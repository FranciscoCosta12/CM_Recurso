package ipvc.estg.cm_recurso

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ipvc.estg.cm_recurso.adapters.NotaAdapter
import ipvc.estg.cm_recurso.entities.Notas
import ipvc.estg.cm_recurso.viewmodel.NotaViewModel

const val PARAM_ID: String = "id"
const val PARAM1_LOCAL: String = "local"
const val PARAM2_DESC: String = "desc"

class Notes : AppCompatActivity(), CellClickListener  {

    private lateinit var notaViewModel: NotaViewModel
    private val newNotaActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = NotaAdapter(this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        notaViewModel = ViewModelProvider(this).get(NotaViewModel::class.java)
        notaViewModel.allNotas.observe(this, Observer { notas ->
            notas?.let { adapter.setNotas(it) }
        })

        val fab = findViewById<Button>(R.id.addButton)
        fab.setOnClickListener {
            val intent = Intent(this,AddNota::class.java)
            startActivityForResult(intent, newNotaActivityRequestCode)
        }

    }

    override fun onCellClickListener(data: Notas) {
        val intent = Intent(this@Notes, EditNota::class.java)
        intent.putExtra(PARAM_ID, data.id.toString())
        intent.putExtra(PARAM1_LOCAL, data.local.toString())
        intent.putExtra(PARAM2_DESC, data.desc.toString())
        startActivityForResult(intent, newNotaActivityRequestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newNotaActivityRequestCode && resultCode == Activity.RESULT_OK) {
            var local = data?.getStringExtra(AddNota.LOCAL).toString()
            var desc = data?.getStringExtra(AddNota.DESC).toString()
            val notas = Notas(local = local, desc = desc)
            notaViewModel.insert(notas)
            Toast.makeText(this, "Erro em salvar!", Toast.LENGTH_SHORT).show()

        } else if (resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Erro em salvar!", Toast.LENGTH_SHORT).show()
        }

        if (requestCode == newNotaActivityRequestCode && resultCode == Activity.RESULT_OK) {
            var edit_local = data?.getStringExtra(EditNota.EDIT_LOCAL).toString()
            var edit_desc = data?.getStringExtra(EditNota.EDIT_DESC).toString()
            var id = data?.getStringExtra(EditNota.EDIT_ID)
            var id_delete = data?.getStringExtra(EditNota.DELETE_ID)
            if(data?.getStringExtra(EditNota.STATUS) == "EDIT"){
                notaViewModel.update(id?.toIntOrNull(), edit_local, edit_desc)
                Toast.makeText(this, "Nota editada!", Toast.LENGTH_SHORT).show()
            } else if(data?.getStringExtra(EditNota.STATUS) == "DELETE"){
                notaViewModel.delete(id_delete?.toIntOrNull())
                Toast.makeText(this, "Nota deletada!", Toast.LENGTH_SHORT).show()
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            if(data?.getStringExtra(EditNota.STATUS) == "EDIT"){
                Toast.makeText(this, "Nota não editou, espaços vazios", Toast.LENGTH_SHORT).show()
            } else if(data?.getStringExtra(EditNota.STATUS) == "DELETE"){
                Toast.makeText(this, "Nota não deletou, espaços vazios", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun postPag(view: View) {
        val intent = Intent(this, Posts::class.java)
        startActivity(intent)
    }

    fun mapPag(view: View) {
        val intent = Intent(this, MapsActivity::class.java)
        startActivity(intent)
    }

    fun addPostPag(view: View) {
        val intent = Intent(this, AddPost::class.java)
        startActivity(intent)
    }

    fun notasPag(view: View) {
        val intent = Intent(this, Notes::class.java)
        startActivity(intent)
    }
}