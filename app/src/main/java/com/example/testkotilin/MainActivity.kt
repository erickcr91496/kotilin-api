package com.example.testkotilin

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.testkotilin.ui.theme.TestKotilinTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import retrofit2.create

//var weatherData by mutableStateOf<WeatherResponse?>(null)

class MainActivity : ComponentActivity() {

    var weatherData by mutableStateOf<WeatherResponse?>(null)
    var isLoading by mutableStateOf(false)
    val articulos = mutableStateListOf<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura Retrofit y realiza la solicitud

        //TEST CLIMA
//        setContent {
//            TestKotilinTheme {
//                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    //WeatherScreen(weatherData)
//                    WeatherApp()
//                }
//            }
//        }

        // ARTICLES
        setContent {
            TestKotilinTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    //WeatherScreen(weatherData)
                    MiApp(articulos)
                }
            }
        }

        cargarDatos()
    }

    @Composable
    fun MiApp(articulos: List<Article>) {
        LazyColumn {
            items(articulos.size) { index ->
                ArticuloItem(articulos[index])
            }
        }
    }

    @Composable
    fun ArticuloItem(articulo: Article) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Usa MaterialTheme.typography.headlineSmall para h6 en Material3
                Text(text = articulo.attributes.title, style = MaterialTheme.typography.headlineSmall)
                // Otros elementos que quieras mostrar
            }
        }
    }



//
@Composable
fun WeatherApp() {
    var cityName by remember { mutableStateOf("") }

    Column{
        OutlinedTextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Ciudad") },
            keyboardActions = KeyboardActions.Default
        )

        //Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { loadWeatherData(cityName) }) {
            Text("Obtener Clima")
        }

        //Spacer(modifier = Modifier.height(16.dp))

        WeatherScreen(weatherData)
    }
}

    private fun loadWeatherData(city: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherService = retrofit.create(WeatherService::class.java)

        weatherService.getCurrentWeatherData(city, "dc5ea9f36d7b9fb499e56fb483fcae67").enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {
                    weatherData = response.body()
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                // Manejar el error
            }
        })
    }

    @Composable
    fun WeatherScreen(weatherResponse: WeatherResponse?) {
        if (weatherResponse != null) {
            Text(text = "Temperatura en ${weatherResponse.name}: ${weatherResponse.main.temp}°C")
        } else {
            Text(text = "Ingrese una ciudad y presione 'Obtener Clima'")
        }
    }

    private fun cargarDatos() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://drupal-site.ddev.site/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.obtenerArticulos().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    Log.d("MainActivity", "RespuAAAAAAAAAAAAAAaestaa!!!!!!!!!!!!!!!!!!!!!!!!!!: ${response.body()?.data}")

                    response.body()?.data?.let { data ->
                        articulos.clear()
                        articulos.addAll(data)
                    }
                } else {
                    Log.d("MainActivity", "R**************espuesta no exitosa: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("MainActivity", "ERRORRRRRRRRRRRRRRespuesta no exitosa: ")
            }
        })
    }

}






    fun main() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://drupal-site.ddev.site/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        // Realizar la solicitud...
        apiService.obtenerArticulos().enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    apiResponse?.data?.forEach { article ->
                        println("Título del artículo: ${article.attributes.title}")
                        // Procesar cada artículo como sea necesario
                    }
                } else {
                    println("Respuesta no exitosa: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

/*
@Composable
fun WeatherScreen(weatherResponse: WeatherResponse?) {
    if (weatherResponse != null) {
        // Mostrar los datos del clima
        Text(text = "Temperatura en ${weatherResponse.name}: ${weatherResponse.main.temp}°C")
    } else {
        // Mostrar algún estado de carga o texto predeterminado
        Text(text = "Cargando datos del clima...")
    }
}*/


//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    TestKotilinTheme {
////        Greeting("Android")
//        WeatherScreen(weatherData)
//    }
//}




