package com.noori.openweathermapapi6

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.noori.openweathermapapi6.api.WeatherApi
import com.noori.openweathermapapi6.data.WeatherData
import com.noori.openweathermapapi6.databinding.ActivityMainBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    companion object{
        private const val BASE_URL="https://api.openweathermap.org/data/2.5/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        val retrofitBuilder = retrofit.create(WeatherApi::class.java)
        //??????????????????????????
        binding.searchButton.setOnClickListener {
            retrofitBuilder.getWeatherData(cityName = binding.userInputCityName.text.toString())
                .enqueue(object : Callback<WeatherData?> {
                    //???????????????
                    @SuppressLint("SetTextI18n")
                    override fun onResponse(
                        call: Call<WeatherData?>,
                        response: Response<WeatherData?>
                    ) {
                        try {
                            val responseBody = response.body()!!
                            binding.tempTv.text = "${responseBody.main.temp} ˚c"
                            binding.tempMin.text = "${responseBody.main.temp_min} ˚c"
                            binding.tempMax.text = "${responseBody.main.temp_max} ˚c"
                            binding.cityNameTV.text = responseBody.name
                            //??????????????????????????????
                            binding.cloudMode.text = responseBody.weather[0].main
                            binding.humidity.text = "${responseBody.main.humidity} %"
                            binding.countryCode.text = responseBody.sys.country
                            binding.errorTV.visibility = View.GONE
                        } catch (e: Exception) {
                            MaterialAlertDialogBuilder(this@MainActivity)
                                .setMessage(e.message.toString())
                                .show()
                            binding.errorTV.visibility = View.VISIBLE
                        } catch (IO: IOException) {
                            MaterialAlertDialogBuilder(this@MainActivity)
                                .setMessage(IO.message.toString())
                                .show()
                            binding.errorTV.visibility = View.VISIBLE
                        } catch (ex: HttpException) {
                            MaterialAlertDialogBuilder(this@MainActivity)
                                .setMessage(ex.message.toString())
                                .show()
                            binding.errorTV.visibility = View.VISIBLE
                        }
                    }

                    override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
                        MaterialAlertDialogBuilder(this@MainActivity)
                            .setMessage(t.message.toString())
                            .show()
                    }
                })
        }
    }
}
