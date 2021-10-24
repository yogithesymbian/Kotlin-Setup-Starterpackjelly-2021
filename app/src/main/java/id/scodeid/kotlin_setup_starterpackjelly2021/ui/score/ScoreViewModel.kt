package id.scodeid.kotlin_setup_starterpackjelly2021.ui.score
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
import id.scodeid.kotlin_setup_starterpackjelly2021.BuildConfig
import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.api.EndPoint
import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.score.ScoreResponse
import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.score.ScoresItem
import kotlinx.coroutines.*
import org.json.JSONObject


class ScoreViewModel(val view: ScoreView) : ViewModel() {

     val mutableLiveData = MutableLiveData<MutableList<ScoresItem>>()
    // val mutableLiveDataSingle = MutableLiveData<ScoresItem?>()

    companion object {
        val TAG_LOG: String = ScoreViewModel::class.java.simpleName
        private var mutableList: MutableList<ScoresItem> = mutableListOf()
        // private var mutableListSingle: ScoresItem? = null
    }

    fun showScoreViewModel(
            context: Context,
            page: String
    ) {
        
        view.showLoadingScoreView()
        val gson = Gson()
        
       Log.d(
               TAG_LOG, """
               request api is in background
               server : EndPoint.GET_SCORE
           """.trimIndent()
       )

        AndroidNetworking.get(EndPoint.GET_SCORE)
                .addHeaders("x-access-token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MywiaWF0IjoxNjM0NjE0NzE3LCJleHAiOjE3MjEwMTQ3MTd9.ApbRjb2nr1HFXgjYCcSpac87JR3ic4FbhDKANtFZX30")
                // .addPathParameter("page", page)
                .addQueryParameter("page", page)
                .addQueryParameter("size", "5")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {

                        GlobalScope.launch {

                            val jsonArrayData = async { response.toString() }
                            val responseJson = async {
                                gson.fromJson(
                                    jsonArrayData.await(),
                                    ScoreResponse::class.java
                                )
                            }
                            Log.d(TAG_LOG, """ ------
                                response ${responseJson.await()}
                            """.trimIndent())

                            val data = responseJson.await()?.results

                            data?.let {
//                                view.showScoreView(it)
                                mutableLiveData.postValue(it)
                            }

                            withContext(Dispatchers.Main) {
                                view.hideLoadingScoreView()
                                Toast.makeText(context,"get scores", Toast.LENGTH_LONG).show()
                            }


                            view.paginationTotalPageScoreView(response.optInt("total_pages"))
                        }

                    }

                    override fun onError(anError: ANError) {
                        view.hideLoadingScoreView()
                        Toast.makeText(context,"get scores failed", Toast.LENGTH_LONG).show()
                        if (anError.errorCode != 0) {
                            Log.d( TAG_LOG, "onError errorCode : ${anError.errorCode}")
                            Log.d( TAG_LOG, "onError errorBody : ${anError.errorBody}")
                            Log.d(TAG_LOG,  "onError errorDetail : ${anError.errorDetail}")

                            if (anError.errorCode == "404".toInt())
                                mutableList.clear()
                            mutableLiveData.postValue(mutableList)

                            // if (anError?.errorCode == "404".toInt())
                            //     mutableListSingle = null
                            // mutableLiveDataSingle.postValue(mutableListSingle)

                        } else {
                            Log.d( TAG_LOG, "onError errorDetail : " + anError.errorDetail)
                        }
                    }
                })
    }

    fun liveData(): LiveData<MutableList<ScoresItem>> {
        return mutableLiveData
    }

    // fun liveDataSingle(): LiveData<ScoresItem?> {
    //    return mutableLiveDataSingle
    // }

}