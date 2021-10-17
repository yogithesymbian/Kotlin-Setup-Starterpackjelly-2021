package id.scodeid.kotlin_setup_starterpackjelly2021.ui.auth.signup.viewmodel

import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.auth.signup.User
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.google.gson.Gson
import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.api.EndPoint
import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.RegisterResponse
import id.scodeid.kotlin_setup_starterpackjelly2021.ui.auth.signup.view.RegisterView
import kotlinx.coroutines.*
import org.json.JSONObject


class RegisterViewModel(private val view: RegisterView) : ViewModel() {

    val mutableLiveData = MutableLiveData<User?>()

    companion object {
        val TAG_LOG: String = RegisterViewModel::class.java.simpleName
        private var mutableList: User? = null
    }

    fun showRegister(
        context: Context,
        email: String?,
        password: String?
    ) {

        view.showLoading()
        val gson = Gson()

        Log.d(
            TAG_LOG, """
               request api is in background
               server : ${EndPoint.AUTH_REGISTER}
           """.trimIndent()
        )

        AndroidNetworking.post(EndPoint.AUTH_REGISTER)
            .addBodyParameter("email", email)
            .addBodyParameter("password", password)
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{

                override fun onResponse(response: JSONObject?) {

                    GlobalScope.launch {

                        val jsonArrayData = async { response.toString() }
                        val responseJson = async {
                            gson.fromJson(
                                jsonArrayData.await(),
                                RegisterResponse::class.java
                            )
                        }
                        Log.d(TAG_LOG, """ ------
                            response ${responseJson.await()}
                        """.trimIndent())

                        val data = responseJson.await()?.data?.user

                        view.showRegisterView(data)

                        withContext(Dispatchers.Main) {
                            view.hideLoading()
                            Toast.makeText(context,"Registrasi Berhasil", Toast.LENGTH_LONG).show()
                        }

                        mutableLiveData.postValue(data)
                    }
                }

                override fun onError(anError: ANError?) {
                    if (anError?.errorCode != 0) {
                        Log.d(TAG_LOG, "onError errorCode : ${anError?.errorCode}")
                        Log.d(TAG_LOG, "onError errorBody : ${anError?.errorBody}")
                        Log.d(TAG_LOG, "onError errorDetail : ${anError?.errorDetail}")

                        if (anError?.errorCode == "404".toInt())
                            mutableList = null
                        mutableLiveData.postValue(mutableList)

                    } else {
                        Log.d(TAG_LOG, "onError errorDetail : $anError")
                    }
                }

            })
    }

    fun liveData(): LiveData<User?> {
        return mutableLiveData
    }

}