package com.example.testkotilin

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("jsonapi/views/list_articles/page_1")
    fun obtenerArticulos(): Call<DrupalData>
}