package com.example.fundandroidsubs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {


    val listUsers = MutableLiveData<ArrayList<User>>()

    fun setUser(usersearch: String) {
        val listItems =ArrayList<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$usersearch"
        client.addHeader("Authorization", "token 6447ad7e7ec2c45a49f8829268063992583153ad")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val items = responseObject.getJSONArray("items")

                    for (i in 0 until items.length()) {
                        val item = items.getJSONObject(i)
                        val username = item.getString("login")
                        val avatar = item.getString("avatar_url")
                        val user = User()
                        user.username = username
                        user.avatar = avatar
                        listItems.add(user)
                    }

                    listUsers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("onSuccess fail", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getUser(): LiveData<ArrayList<User>> {
        return listUsers
    }

}