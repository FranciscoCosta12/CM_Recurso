package ipvc.estg.cm_recurso

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import ipvc.estg.cm_recurso.api.EndPoints
import ipvc.estg.cm_recurso.api.OutputPost
import ipvc.estg.cm_recurso.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddPost : AppCompatActivity() {

    private lateinit var editLocalView : EditText
    private lateinit var editDescView: EditText
    private lateinit var editImagemView : EditText
    private lateinit var editLatView: EditText
    private lateinit var editLngView : EditText
    private lateinit var shared_preferences : SharedPreferences
    private var latitude : Double = 0.0
    private var longitude : Double = 0.0

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var lastLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        editLocalView = findViewById(R.id.localInput)
        editDescView = findViewById(R.id.descInput)
        editImagemView = findViewById(R.id.imgInput)
        editLatView = findViewById(R.id.latInput)
        editDescView = findViewById(R.id.descInput)
        shared_preferences = getSharedPreferences("shared_preferences", Context.MODE_PRIVATE)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult?) {
                super.onLocationResult(p0)
                lastLocation = p0?.lastLocation!!
                latitude = lastLocation.latitude
                longitude = lastLocation.longitude
            }
        }

        createLocationRequest()
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

    }

    fun post(view: View){
        val request = ServiceBuilder.buildService(EndPoints::class.java)
        //val latitude = latitude
        //val longitude = longitude
        val latitude = editLatView.text.toString()
        val longitude = editLngView.text.toString()
        val rue = editLocalView.text.toString()
        val desc = editDescView.text.toString()
        val imagem = editImagemView.text.toString()

        val call = request.post(latitude = latitude,
                longitude = longitude,
                rue = rue,
                desc = desc,
                imagem = imagem)

        call.enqueue(object : Callback<OutputPost> {
            override fun onResponse(call: Call<OutputPost>, response: Response<OutputPost>){
                if (response.isSuccessful){
                    val c: OutputPost = response.body()!!
                    Toast.makeText(this@AddPost, c.MSG, Toast.LENGTH_LONG).show()
                    val intent = Intent(this@AddPost, MapsActivity::class.java)
                    startActivity(intent);
                    finish()

                }
            }
            override fun onFailure(call: Call<OutputPost>, t: Throwable){
                Toast.makeText(this@AddPost,"${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)

            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)

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