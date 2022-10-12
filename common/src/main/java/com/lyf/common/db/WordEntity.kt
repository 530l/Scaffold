package com.lyf.common.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity注解是标识实体类，并通过tableName指定该实体类指向的表名，

// @PrimaryKey注解标识主键，这个跟greenDao这些数据库基本是一样的，
// autoGenerate = true则表示主键自增，

// @ColumnInfo注解标识列参数的信息，name =是指定数据库字段名称，不指定默认是自定义的值。

@Entity(tableName = "word_table")
data class WordEntity(
    @ColumnInfo(name = "word")
    val word:String?,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
)