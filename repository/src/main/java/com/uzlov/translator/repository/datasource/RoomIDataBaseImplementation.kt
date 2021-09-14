package com.uzlov.translator.repository.datasource

import com.uzlov.translator.repository.room.HistoryDao
import com.uzlov.translator.repository.room.HistoryEntity


class RoomIDataBaseImplementation(private val historyDao: HistoryDao) : IDataSourceLocal<List<HistoryEntity>> {

    override suspend fun getData(word: String): List<HistoryEntity> {
        return listOf(historyDao.getDataByWord(word))
    }

    override suspend fun save(historyEntity: HistoryEntity) {
        historyDao.insert(historyEntity)
    }

    override suspend fun getAllSavedWord(): List<HistoryEntity> {
        return historyDao.allWord()
    }
}
