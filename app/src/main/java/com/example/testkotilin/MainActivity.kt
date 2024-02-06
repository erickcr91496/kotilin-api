package com.example.testkotilin

import android.annotation.SuppressLint
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
import androidx.compose.runtime.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Card
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
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import retrofit2.create


class MainActivity : ComponentActivity() {

    var weatherData by mutableStateOf<WeatherResponse?>(null)
    var isLoading by mutableStateOf(false)
    //val articulos by  mutableStateOf<DrupalData?>(null)
    //val articulos = mutableStateListOf<Article>()
    var articulosView = mutableStateOf<DrupalData?>(null)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configura Retrofit y realiza la solicitud

        //TEST CLIMA
        setContent {
            TestKotilinTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    //WeatherScreen(weatherData)
                    articulosView.value?.data?.let { data ->
                        Log.d("MainActivity", "CONTENT===###########${data}")

                        ArticulosList(articulos = data)
                    }
                }
            }
        }

        // ARTICLES
        cargarDatos()

//        setContent {
//            TestKotilinTheme {
//                    // Define tu interfaz de usuario aquí
//                    Scaffold(
//                        topBar = {
//                            // Aquí puedes definir una TopAppBar si es necesario
//                            TopAppBar(
//                                title = {
//                                    Text(text = "Mi Aplicación")
//                                }
//                            )
//                        },
//                        content = {
//                            articulosView.value?.data?.let { data ->
//                                Log.d("MainActivity", "CONTENT===###########${data}")
//
//                                ArticulosList(articulos = data)
//                            }
//                        }
//                    )
//
//            }
//        }


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
            .baseUrl("https://b20a-2800-bf0-2c4d-1082-ddb7-3c33-f3f7-6e00.ngrok-free.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.obtenerArticulos().enqueue(object : Callback<DrupalData> {
            override fun onResponse(call: Call<DrupalData>, response: Response<DrupalData>) {
                Log.d("MainActivity", "======================================================${response}")

                if (response.isSuccessful) {
                    Log.d("MainActivity", "Re%%%%%%%%%%#########!!!!!!!!!!!!!!!!!!!!!!!!!!: ${response.body()}")
                    articulosView.value = response.body()
//                    response.body()?.data?.let { data ->
//                        Log.d("MainActivity", "++===+++++++++datAAAAA=====================!: ${data}")
//                        // La respuesta es exitosa, puedes acceder a los datos
//                        val drupalData = response.body()
//
//                        // Asegúrate de que DrupalData no sea nulo
//                        if (drupalData != null) {
//                            val articles = drupalData.data
//                            for (article in articles) {
//                                val articleType = article.type // Obtiene el tipo de artículo
//                                val articleId = article.id // Obtiene el ID del artículo
//                                val articleAttributes = article.attributes // Obtiene los atributos del artículo
//                                val articleTitle = articleAttributes.title // Obtiene el título del artículo
//
//                                // Ahora puedes hacer lo que necesites con los datos del artículo
//                                Log.d("MainActivity", "Tipo de artículo: $articleType")
//                                Log.d("MainActivity", "ID del artículo: $articleId")
//                                Log.d("MainActivity", "Título del artículo: $articleTitle")
//                            }
//                        }
//
//                    }
                } else {
                    Log.d("MainActivity", "R**************espuesta no exitosa: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<DrupalData>, t: Throwable) {
                Log.d("MainActivity", "ERRORRRRRRRRRRRRRRespuesta no exitosa: ",t)
            }
        })
    }

    @Composable
    fun ArticulosList(articulos: List<Article>) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(articulos.size) { index ->
                val articulo = articulos[index]
                ArticuloItem(articulo = articulo)
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ArticuloItem(articulo: Article) {
        Log.d("ArticuloItem", "********&&&&&&&&&*8=========${articulo.attributes.title}")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = articulo.attributes.title,
                style = MaterialTheme.typography.bodyLarge
            )
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }

}



