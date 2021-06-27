package ipvc.estg.cm_recurso

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<ImageView>(R.id.imageView3)
        button.setOnClickListener{
            val intent = Intent(this, Feed::class.java)
            startActivity(intent)
        }

        val sharedPref: SharedPreferences = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )

        val notasValue = sharedPref.getBoolean(getString(R.string.notas), false)

        if(notasValue){

        }
    }
}