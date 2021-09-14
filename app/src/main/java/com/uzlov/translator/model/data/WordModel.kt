package com.uzlov.translator.model.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class WordModel(
    @field:SerializedName("text") val text: String?,
    @field:SerializedName("meanings") val meanings: List<Meanings>?
) : Serializable
