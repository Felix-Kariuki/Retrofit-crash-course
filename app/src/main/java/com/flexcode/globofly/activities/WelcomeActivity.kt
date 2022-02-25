package com.flexcode.globofly.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flexcode.globofly.databinding.ActivityWelcomeBinding
import com.flexcode.globofly.services.NetworkService
import com.flexcode.globofly.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeActivity : AppCompatActivity() {
	private lateinit var binding: ActivityWelcomeBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityWelcomeBinding.inflate(layoutInflater)
		setContentView(binding.root)

		getMessage()
		// To be replaced by retrofit code
		//binding.message.text = "Black Friday! Get 50% cash back on saving your first spot."
	}

	private fun getMessage() {
		/*val messageService = ServiceBuilder.buildService(NetworkService::class.java)
		//use original url pass string

		val requestCall = messageService.getMessage("http://10.0.2.2:9000/messages")
		requestCall.enqueue(object :Callback<String>{
			override fun onResponse(call: Call<String>, response: Response<String>) {
				val message = response.body()
				if (response.isSuccessful){
					message?.let{
						binding.message.text = message
					}
				}else{
					Toast.makeText(this@WelcomeActivity,"ERROR${response.message()}",Toast.LENGTH_LONG).show()
				}
			}

			override fun onFailure(call: Call<String>, t: Throwable) {
				Toast.makeText(this@WelcomeActivity,"ERROR${t.message}",Toast.LENGTH_LONG).show()
			}
		})*/
		val messageService = ServiceBuilder.buildService(NetworkService::class.java)
		val requestCall = messageService.getMessages()

		requestCall.enqueue(object : Callback<String>{
			override fun onResponse(call: Call<String>, response: Response<String>) {
				val message = response.body()
				if (response.isSuccessful){
					message?.let{
						binding.message.text = message
					}
				}else{
					Toast.makeText(this@WelcomeActivity,"ERROR${response.message()}",Toast.LENGTH_LONG).show()
				}
			}

			override fun onFailure(call: Call<String>, t: Throwable) {
				Toast.makeText(this@WelcomeActivity,"ERROR${t.message}",Toast.LENGTH_LONG).show()
			}
		})
		/*val requestCall:Call<Message> = messageService.getMessages()

		requestCall.enqueue(object : Callback<Message> {
			override fun onResponse(call: Call<Message>, response: Response<Message>) {
				val message = response.body()
				if (response.isSuccessful){
					message?.let{
						binding.message.text = message.message.toString()
					}
				}else{
					Toast.makeText(this@WelcomeActivity,"ERROR${response.message()}",Toast.LENGTH_LONG).show()
				}
			}

			override fun onFailure(call: Call<Message>, t: Throwable) {
				Toast.makeText(this@WelcomeActivity,"ERROR${t.message}",Toast.LENGTH_LONG).show()
			}
		})*/
	}

	fun getStarted(view: View) {
		val intent = Intent(this, DestinationListActivity::class.java)
		startActivity(intent)
		finish()
	}
}
