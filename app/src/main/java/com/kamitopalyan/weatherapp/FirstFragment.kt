package com.kamitopalyan.weatherapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.time.Instant
import java.time.ZoneId

import com.kamitopalyan.weatherapp.databinding.FragmentFirstBinding
import kotlinx.coroutines.*
import okhttp3.OkHttpClient;
import okhttp3.Request
import org.json.JSONObject

import kotlin.coroutines.CoroutineContext

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), CoroutineScope {
    val response = "-"
    private var apikey: String = "53639bc517d4a5f7b02e4c5a09ae6ef5"
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // We create the job in `onCreate`, and afterward, we can use coroutines
        // in the `Activity`.
        job = Job()
    }

    // This tells our coroutines about the context to use.
    override val coroutineContext: CoroutineContext
        // Make sure to use `Dispatchers.Main` so the Android UI thread
        // is used by default.
        get() = Dispatchers.Main + job

    override fun onDestroy() {
        super.onDestroy()
        // This will cancel all currently running coroutines.
        job.cancel()
    }

    fun onCreate() {
        launch {
            val data = NetworkData("Turkey", apikey,28.9784 ,41.0082 )
            val json = data?.let { JSONObject(it) };
            val jsonArray = json?.getJSONArray("list")
            val activity = this

            //Save day 1 data
            val day1data = jsonArray?.getJSONObject(0)
            val day1Main = day1data?.getJSONObject("main")
            val day1Temp = day1Main?.get("temp")
            val day1Hum = day1Main?.get("humidity")
            val day1Wind = day1data?.getJSONObject("wind")?.get("speed")
            val day1Sum = day1data?.getJSONArray("weather")?.getJSONObject(0)?.get("main")

            //Save day 2 data
            val day2data = jsonArray?.getJSONObject(9)
            val day2Main = day2data?.getJSONObject("main")
            val day2Temp = day2Main?.get("temp")
            val day2Hum = day2Main?.get("humidity")
            val day2Wind = day2data?.getJSONObject("wind")?.get("speed")
            val day2Sum = day2data?.getJSONArray("weather")?.getJSONObject(0)?.get("main")

            //Save day 3 data
            val day3data = jsonArray?.getJSONObject(17)
            val day3Main = day3data?.getJSONObject("main")
            val day3Temp = day3Main?.get("temp")
            val day3Hum = day3Main?.get("humidity")
            val day3Wind = day3data?.getJSONObject("wind")?.get("speed")
            val day3Sum = day3data?.getJSONArray("weather")?.getJSONObject(0)?.get("main")

            //Save day 4 data
            val day4data = jsonArray?.getJSONObject(26)
            val day4Main = day4data?.getJSONObject("main")
            val day4Temp = day4Main?.get("temp")
            val day4Hum = day4Main?.get("humidity")
            val day4Wind = day4data?.getJSONObject("wind")?.get("speed")
            val day4Sum = day4data?.getJSONArray("weather")?.getJSONObject(0)?.get("main")

            //Save day 5 data
            val day5data = jsonArray?.getJSONObject(35)
            val day5Main = day5data?.getJSONObject("main")
            val day5Temp = day5Main?.get("temp")
            val day5Hum = day5Main?.get("humidity")
            val day5Wind = day5data?.getJSONObject("wind")?.get("speed")
            val day5Sum = day5data?.getJSONArray("weather")?.getJSONObject(0)?.get("main")

            //Date Calculations for day 1
            val day1date: Int = day1data?.get("dt") as Int
            val day1epoch: Long = day1date.toLong()
            val day1dow = findDayOfWeek(day1epoch) //wof is short for dayofweek

            //Date Calculations for day 2
            val day2date: Int = day2data?.get("dt") as Int
            val day2epoch: Long = day2date.toLong()
            val day2dow = findDayOfWeek(day2epoch) //wof is short for dayofweek

            //Date Calculations for day 3
            val day3date: Int = day3data?.get("dt") as Int
            val day3epoch: Long = day3date.toLong()
            val day3dow = findDayOfWeek(day3epoch) //wof is short for dayofweek

            //Date Calculations for day 4
            val day4date: Int = day4data?.get("dt") as Int
            val day4epoch: Long = day4date.toLong()
            val day4dow = findDayOfWeek(day4epoch) //wof is short for dayofweek

            //Date Calculations for day 5
            val day5date: Int = day5data?.get("dt") as Int
            val day5epoch: Long = day5date.toLong()
            val day5dow = findDayOfWeek(day5epoch) //wof is short for dayofweek

            //Set Fields
            val MainWeather: TextView = view?.findViewById(R.id.MainWeather) as TextView
            val day1Weather: TextView = view?.findViewById(R.id.day1Weather) as TextView
            val day2Weather: TextView = view?.findViewById(R.id.day2Weather) as TextView
            val day3Weather: TextView = view?.findViewById(R.id.day3Weather) as TextView
            val day4Weather: TextView = view?.findViewById(R.id.day4Weather) as TextView
            val day5Weather: TextView = view?.findViewById(R.id.day5Weather) as TextView

            val MainDay: TextView = view?.findViewById(R.id.DayOfWeek) as TextView
            val day1: TextView = view?.findViewById(R.id.day1) as TextView
            val day2: TextView = view?.findViewById(R.id.day2) as TextView
            val day3: TextView = view?.findViewById(R.id.day3) as TextView
            val day4: TextView = view?.findViewById(R.id.day4) as TextView
            val day5: TextView = view?.findViewById(R.id.day5) as TextView

            MainWeather.text = day1Sum.toString()
            day1Weather.text = day1Sum.toString()
            day2Weather.text = day2Sum.toString()
            day3Weather.text = day3Sum.toString()
            day4Weather.text = day4Sum.toString()
            day5Weather.text = day5Sum.toString()

            MainDay.text = day1dow
            day1.text = day1dow
            day2.text = day2dow
            day3.text = day3dow
            day4.text = day4dow
            day5.text = day5dow


            println(day1dow)
            println(day1Sum)
            println(day2dow)
            println(day3dow)
            println(day4dow)
            println(day5dow)
            println(jsonArray)

        }
    }
    //Find the day of the week from the epoch date

    fun findDayOfWeek(epochTime: Long): String {
        val instant = Instant.ofEpochMilli((epochTime * 1000))
        val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
        return localDate.dayOfWeek.toString()
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCreate()
    }


    suspend fun NetworkData(city: String, apiKey: String, lat: Double, lon: Double): String? = withContext(Dispatchers.IO) {
        val url = "https://api.openweathermap.org/data/2.5/forecast?lat=$lat&lon=$lon&cnt=-1&appid=$apiKey"
        println(url)
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        val response = client.newCall(request).execute()
        return@withContext response.body?.string()
    }
}
