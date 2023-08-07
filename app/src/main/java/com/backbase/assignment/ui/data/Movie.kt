package com.backbase.assignment.ui.data

import android.icu.text.CaseMap
import java.util.*
import kotlin.collections.ArrayList

data class Movie(
    var movieId : String,
    var title: String,
    var overview : String,
    var rating : Int,
    var duration : String,
    var releaseDate : String,
    var genres : List<String>,
    var poster : MoviePoster
    )
