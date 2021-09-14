package com.uzlov.translator.repository.room

import androidx.room.*

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity")
    suspend fun allWord(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE word LIKE :word")
    suspend fun getDataByWord(word: String): HistoryEntity

    // insert method's
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg entity: HistoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entities: List<HistoryEntity>)

    // update method's
    @Update
    suspend fun update(entity: HistoryEntity)

    @Update
    suspend fun update(vararg entity: HistoryEntity)

    @Update
    suspend fun update(entity: List<HistoryEntity>)


    // delete method's
    @Delete
    suspend fun delete(entity: HistoryEntity)

    @Delete
    suspend fun delete(vararg entity: HistoryEntity)

    @Delete
    suspend fun delete(entity: List<HistoryEntity>)
}
