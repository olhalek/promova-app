package com.interview.promova_app.ui.main.model

data class Movie (
    val id:Long,
    val title: String,
    val overview:String,
    val releaseDate: String,
    val posterPath:String,
    val rate:Double,
    val isFavourite:Boolean
)