package com.lyf.common.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//WordDao接口在编译时，当它被数据库引用时，Room会生成此类的实现，
// 这也是为什么可以通过接口来调用。
//
// 因为通过Room操作数据库不可以在主线程，所以把增删查的方法都写成了挂起函数， 通过协程来调用，

@Dao
interface WordDao {
    //插入多个数据
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: MutableList<WordEntity>)

    //
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(words: WordEntity)

    //获取所有数据
    @Query("SELECT * FROM word_table")
    fun queryAll(): MutableList<WordEntity>

    //根据id获取一个数据
    @Query("SELECT * FROM word_table WHERE id = :id")
    fun getWordById(id: Int): WordEntity?

    //删除表中所有数据
    @Query("DELETE FROM word_table")
    suspend fun deleteAll()

    //通过id修改数据
    @Query("UPDATE word_table SET word=:word WHERE id=:id")
    suspend fun updateData(id: Long, word: String)

    //根据Id删除数据
    @Query("DELETE FROM word_table WHERE id=:id")
    suspend fun deleteById(id: Long)

    //根据属性值删除数据
    @Query("DELETE FROM word_table WHERE word=:word")
    suspend fun deleteByName(word: String)
}