package ipvc.estg.cm_recurso

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class EditNota : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_nota)

        var editLocalView: EditText = findViewById(R.id.localInput)
        var editDescView: EditText = findViewById(R.id.descInput)

        var id = intent.getStringExtra(PARAM_ID)
        var local = intent.getStringExtra(PARAM1_LOCAL)
        var desc = intent.getStringExtra(PARAM2_DESC)
        editLocalView.setText(local.toString())
        editDescView.setText(desc.toString())

        val button = findViewById<Button>(R.id.editButton)
        button.setOnClickListener {
            val replyIntent = Intent()
            replyIntent.putExtra(EDIT_ID, id)
            if (TextUtils.isEmpty(editLocalView.text)  || TextUtils.isEmpty(editDescView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val edit_local = editLocalView.text.toString()
                replyIntent.putExtra(EDIT_LOCAL, edit_local)
                val edit_desc = editDescView.text.toString()
                replyIntent.putExtra(EDIT_DESC, edit_desc)
                replyIntent.putExtra(STATUS, "EDIT")
                setResult(Activity.RESULT_OK, replyIntent)
            }

            finish()
        }

        val button_delete = findViewById<Button>(R.id.deleteButton)
        button_delete.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editLocalView.text)  || TextUtils.isEmpty(editDescView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                replyIntent.putExtra(DELETE_ID, id)
                replyIntent.putExtra(STATUS, "DELETE")
                setResult(Activity.RESULT_OK, replyIntent)
            }

            finish()
        }
    }

    companion object {
        const val STATUS = ""
        const val DELETE_ID = "DELETE_ID"
        const val EDIT_ID = "EDIT_ID"
        const val EDIT_LOCAL = "EDIT_LOCAL"
        const val EDIT_DESC = "EDIT_DESC"
    }

}