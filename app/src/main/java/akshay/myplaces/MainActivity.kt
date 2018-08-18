package akshay.myplaces

import akshay.myplaces.bean.MainPlaceBean
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.location.places.ui.PlacePicker
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    var mylati : Double? = null
    var mylongi :Double? = null

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000.toLong(),1.toFloat(), object : LocationListener {
            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {

            }

            override fun onProviderEnabled(p0: String?) {

            }

            override fun onProviderDisabled(p0: String?) {

            }

            override fun onLocationChanged(p0: Location?) {
                mylati = p0!!.latitude
                mylongi = p0.longitude
                lati.text = mylati.toString()
                longi.text = mylongi.toString()
                lm.removeUpdates(this)
            }

        })

        place.setOnClickListener {
            var place = PlacePicker.IntentBuilder()
            startActivityForResult(place.build(this@MainActivity),1)
        }

        get.setOnClickListener {
            var r = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl("https://maps.googleapis.com/").build()
            var api = r.create(PlacesAPI::class.java)
            var call = api.getPlaces("$mylati,$mylongi",sp1.selectedItem.toString())
            call.enqueue(object : Callback<MainPlaceBean>{
                override fun onFailure(call: Call<MainPlaceBean>?, t: Throwable?) {
                    Toast.makeText(this@MainActivity,"Failed",Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<MainPlaceBean>?, response: Response<MainPlaceBean>?) {
                    var resp = response!!.body()
                    var result = resp!!.results
                    var list = mutableListOf<String>()
                    for (item in result!!){
                        list.add("Name: ${item.name}"+System.lineSeparator()+"Address: ${item.vicinity}")
                    }

                    var mya = ArrayAdapter<String>(this@MainActivity,android.R.layout.simple_list_item_1,list)
                    lv.adapter = mya
                }

            })
        }

    } ///////////////oncreate

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var p = PlacePicker.getPlace(this@MainActivity,data)
        mylati = p.latLng.latitude
        mylongi = p.latLng.longitude
        lati.text = mylati.toString()
        longi.text = mylongi.toString()
    }

}///////main class
