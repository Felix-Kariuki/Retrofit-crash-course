package com.flexcode.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flexcode.globofly.databinding.ActivityDestinyDetailBinding
import com.flexcode.globofly.helpers.SampleData
import com.flexcode.globofly.models.Destination
import com.flexcode.globofly.services.NetworkService
import com.flexcode.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DestinationDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDestinyDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestinyDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.detailToolbar)
        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {

            val id = intent.getIntExtra(ARG_ITEM_ID, 0)

            loadDetails(id)

            initUpdateButton(id)

            initDeleteButton(id)
        }
    }

    private fun loadDetails(id: Int) {


        // To be replaced by retrofit code
//		val destination = SampleData.getDestinationById(id)
//
//		destination?.let {
//			binding.etCity.setText(destination.city)
//			binding.etDescription.setText(destination.description)
//			binding.etCountry.setText(destination.country)
//
//			binding.collapsingToolbar.title = destination.city
//		}

        val destinationService = ServiceBuilder.buildService(NetworkService::class.java)
        val requestCall = destinationService.getDestination(id)

        requestCall.enqueue(object : Callback<Destination> {
            override fun onResponse(call: Call<Destination>, response: Response<Destination>) {

                if (response.isSuccessful) {
                    val destination = response.body()

                    destination?.let {
                        binding.etCity.setText(destination.city)
                        binding.etDescription.setText(destination.description)
                        binding.etCountry.setText(destination.country)

                        binding.collapsingToolbar.title = destination.city
                    }
                }
            }

            override fun onFailure(call: Call<Destination>, t: Throwable) {

            }
        })
    }

    private fun initUpdateButton(id: Int) {

        binding.btnUpdate.setOnClickListener {

            val city = binding.etCity.text.toString()
            val description = binding.etDescription.text.toString()
            val country = binding.etCountry.text.toString()

            val destinationService = ServiceBuilder.buildService(NetworkService::class.java)
            val requestCall = destinationService.updateDestination(id, city, description, country)

            requestCall.enqueue(object : Callback<Destination> {
                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful) {
                        finish()
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Success", Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Error!${response.message()}", Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Toast.makeText(
                        this@DestinationDetailActivity,
                        "Error!${t.message}", Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    private fun initDeleteButton(id: Int) {

        binding.btnDelete.setOnClickListener {

            val destinationService = ServiceBuilder.buildService(NetworkService::class.java)
            val requestCall = destinationService.deleteDestination(id)

            requestCall.enqueue(object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        finish()
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Success", Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            this@DestinationDetailActivity,
                            "Error!${response.message()}", Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(
                        this@DestinationDetailActivity,
                        "Error!${t.message}", Toast.LENGTH_LONG
                    ).show()
                }

            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, DestinationListActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        const val ARG_ITEM_ID = "item_id"

    }
}
