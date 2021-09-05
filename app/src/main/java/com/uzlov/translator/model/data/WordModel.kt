package com.uzlov.translator.model.data

import com.google.gson.annotations.SerializedName

class WordModel(
    @field:SerializedName("text") val text: String?,
    @field:SerializedName("meanings") val meanings: List<Meanings>?
)
