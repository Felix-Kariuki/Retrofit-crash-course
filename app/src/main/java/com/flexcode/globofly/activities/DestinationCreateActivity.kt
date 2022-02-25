package com.flexcode.globofly.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flexcode.globofly.R
import com.flexcode.globofly.databinding.ActivityDestinyCreateBinding
import com.flexcode.globofly.helpers.SampleData
import com.flexcode.globofly.models.Destination
import com.flexcode.globofly.services.NetworkService
import com.flexcode.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Response

class DestinationCreateActivity : AppCompatActivity() {

	private lateinit var binding: ActivityDestinyCreateBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityDestinyCreateBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.toolbar)
		val context = this

		// Show the Up button in the action bar.
		supportActionBar?.setDisplayHomeAsUpEnabled(true)

		binding.btnAdd.setOnClickListener {
			val newDestination = Destination()
			newDestination.city = binding.etCity.text.toString()
			newDestination.description = binding.etDescription.text.toString()
			newDestination.country = binding.etCountry.text.toString()

			val destinationService = ServiceBuilder.buildService(NetworkService::class.java)
			val requestCall = destinationService.addDestination(newDestination)

			requestCall.enqueue(object : retrofit2.Callback<Destination> {
				override fun onResponse(call: Call<Destination>, response: Response<Destination>) {

					if (response.isSuccessful){
						finish()
						var newlyCreatedDestination =response.body()
						Toast.makeText(context,"Added Successfully",Toast.LENGTH_LONG).show()
					}else{
						Toast.makeText(context,"ERROR${response.message()}",Toast.LENGTH_LONG).show()
					}
				}

				override fun onFailure(call: Call<Destination>, t: Throwable) {
					Toast.makeText(context,"Error${t.message}",Toast.LENGTH_LONG).show()
				}
			})

			// To be replaced by retrofit code
//			SampleData.addDestination(newDestination)
//            finish() // Move back to DestinationListActivity
		}
	}
}
