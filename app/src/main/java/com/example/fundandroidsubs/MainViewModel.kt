package com.example.fundandroidsubs

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import kotlin.Exception

class MainViewModel : ViewModel() {


    val listUsers = MutableLiveData<ArrayList<User>>()

    val listDetailUser = MutableLiveData<User>()

    fun setUserDetail(user: String) {
        val url = "https://api.github.com/users/$user"
        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token d0fcc16d677ac5c4de4a1177cda19e336dbfdc11")
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
                    val user = User()
                    user.username = responseObject.getString("login")
                    user.name = responseObject.getString("name")
                    user.avatar = responseObject.getString("avatar_url")
                    user.company = responseObject.getString("company")
                    user.location = responseObject.getString("location")
                    user.repository = responseObject.getInt("public_repos")
                    user.following = responseObject.getInt("following")
                    user.followers = responseObject.getInt("followers")
                    listDetailUser.postValue(user)
                } catch (e: Exception) {
                    Log.d("onSuccess fail", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    fun setFollowing(username: String) {
        val listItems = ArrayList<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/following"
        client.addHeader("Authorization", "token d0fcc16d677ac5c4de4a1177cda19e336dbfdc11")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseArray = JSONArray(result)

                    for (i in 0 until responseArray.length()) {
                        val item = responseArray.getJSONObject(i)
                        val user = User()
                        user.username = item.getString("login")
                        user.avatar = item.getString("avatar_url")
                        listItems.add(user)
                    }
                    listUsers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("onSuccess fail", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    fun setFollower(username: String) {
        val listItems = ArrayList<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/followers"
        client.addHeader("Authorization", "token d0fcc16d677ac5c4de4a1177cda19e336dbfdc11")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseArray = JSONArray(result)

                    for (i in 0 until responseArray.length()) {
                        val item = responseArray.getJSONObject(i)
                        val user = User()
                        user.username = item.getString("login")
                        user.avatar = item.getString("avatar_url")
                        listItems.add(user)
                    }
                    listUsers.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("onSuccess fail", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }


    fun setUser(usersearch: String) {
        val listItems = ArrayList<User>()
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$usersearch"
        client.addHeader("Authorization", "token d0fcc16d677ac5c4de4a1177cda19e336dbfdc11")
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

    fun getDetailUser(): LiveData<User> {
        return listDetailUser
    }

    fun getFollower() : LiveData<ArrayList<User>>{
        return listUsers
    }

    fun getFollowing() : LiveData<ArrayList<User>>{
        return listUsers
    }

}