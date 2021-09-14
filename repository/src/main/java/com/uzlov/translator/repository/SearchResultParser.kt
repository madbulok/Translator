package com.uzlov.translator.repository

import com.uzlov.translator.model.data.AppState
import com.uzlov.translator.model.data.Meanings
import com.uzlov.translator.model.data.WordModel
import com.uzlov.translator.repository.room.HistoryEntity


fun parseSearchResults(state: AppState): AppState {
    val newSearchResults = arrayListOf<WordModel>()
    when (state) {
        is AppState.Success -> {
            val searchResults = state.data
            if (!searchResults.isNullOrEmpty()) {
                for (searchResult in searchResults) {
                    parseResult(searchResult, newSearchResults)
                }
            }
        }
    }

    return AppState.Success(newSearchResults)
}

private fun parseResult(dataModel: WordModel, newDataModels: ArrayList<WordModel>) {
    if (!dataModel.text.isNullOrBlank() && !dataModel.meanings.isNullOrEmpty()) {
        val newMeanings = arrayListOf<Meanings>()
        for (meaning in dataModel.meanings ?: emptyList()) {
            if (meaning.translation != null && !meaning.translation?.translation.isNullOrBlank()) {
                newMeanings.add(Meanings(meaning.translation, meaning.imageUrl))
            }
        }
        if (newMeanings.isNotEmpty()) {
            newDataModels.add(WordModel(dataModel.text, newMeanings))
        }
    }
}

fun mapHistoryEntityToSearchResult(list: List<HistoryEntity>): List<WordModel> {
    val searchResult = ArrayList<WordModel>()
    if (!list.isNullOrEmpty()) {
        for (entity in list) {
            searchResult.add(WordModel(entity.word, null))
        }
    }
    return searchResult
}

fun convertMeaningsToString(meanings: List<Meanings>): String {
    var meaningsSeparatedByComma = String()
    for ((index, meaning) in meanings.withIndex()) {
        meaningsSeparatedByComma += if (index + 1 != meanings.size) {
            String.format("%s%s", meaning.translation?.translation, ", ")
        } else {
            meaning.translation?.translation
        }
    }
    return meaningsSeparatedByComma
}
