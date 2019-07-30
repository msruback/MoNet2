package com.mattrubacky.monet2.backend.api

import android.os.AsyncTask
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

abstract class ApiConnector(private val requests: ArrayList<Request<Any>>, val lambda:(ArrayList<Request<Any>>) -> Unit) : AsyncTask<Void, Void, Void>() {

    override fun onPreExecute(){
    }

    override fun doInBackground(vararg params: Void?): Void? {
        val okHttpClient = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
        val retrofit = setUp(okHttpClient)
        requests.forEach { request ->
            request.run()
        }
        return null
    }

    override fun onPostExecute(result: Void?) {
        super.onPostExecute(result)
        lambda(requests)
    }

    abstract fun setUp(okHttpClient: OkHttpClient):Retrofit
}