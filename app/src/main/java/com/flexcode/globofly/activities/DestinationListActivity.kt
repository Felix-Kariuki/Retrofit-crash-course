package com.flexcode.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flexcode.globofly.databinding.ActivityDestinyListBinding
import com.flexcode.globofly.adapters.DestinationAdapter
import com.flexcode.globofly.models.Destination
import com.flexcode.globofly.api.NetworkService
import com.flexcode.globofly.api.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDestinyListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDestinyListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = title

        binding.fab.setOnClickListener {
            val intent = Intent(this@DestinationListActivity, DestinationCreateActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        //reload from server after resume
        loadDestinations()
    }

    private fun loadDestinations() {
        //fetch the destinations from server
        //destination service

        val destinationService = ServiceBuilder.buildService(NetworkService::class.java)

        val requestCall:Call<List<Destination>> = destinationService.getDestinationList(null)

        requestCall.enqueue(object : Callback<List<Destination>> {
            override fun onResponse(
                call: Call<List<Destination>>,
                response: Response<List<Destination>>
            ) {

                if (response.isSuccessful) {
                    val destinationList : List<Destination> = response.body()!!

                    val destinyRecyclerView = binding.destinyRecyclerView
                    destinyRecyclerView.adapter = DestinationAdapter(destinationList)
                }else {
                    Toast.makeText(this@DestinationListActivity,
                        "Error${response.message()}",Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Destination>>, t: Throwable) {

            }


        })



    }

}
