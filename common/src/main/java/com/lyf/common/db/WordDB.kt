package com.lyf.common.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//创建数据库的类是RoomDatabase，先创建一个类继承它，并@Database来标注这个自定义类，
// @Database注解里面还有几个参数需要理解一下：
//
//entities：指定添加进来的数据库表，这里数组形式添加，如果项目用到多个表可以用逗号隔开添加进来；
//version：当前数据库的版本号，当需要版本升级会用到；
//exportSchema：表示导出为文件模式，默认为true,这里要设置为false，不然会报警告。

@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
abstract class WordDB : RoomDatabase() {

    abstract fun getWordDao(): WordDao

    companion object {

        @Volatile
        private var instantce: WordDB? = null
        private const val DB_NAME = "jetpack_room.db"

        fun getInstance(context: Context): WordDB? {
            if (instantce == null) {
                synchronized(WordDB::class.java) {
                    if (instantce == null) {
                        instantce = createInstance(context)
                    }
                }
            }
            return instantce
        }

        private fun createInstance(context: Context): WordDB {
            return Room.databaseBuilder(context.applicationContext, WordDB::class.java, DB_NAME)
//                .addMigrations(MIGRATION_1_2)
                .allowMainThreadQueries()
                .build()
        }

        //实际开发中，我们总是会遇到因为新的业务需求要在已有数据表中添加新的参数，这个时候就要对数据升级了。数据库升级主要是以下几点：
        //
        //在Room构建数据库是通过addMigrations(Migration migrations...)方法进行版本升级，方法内饰一个可边长参数，可以实现处理多个版本升级迁移；
        //Migration(int startVersion, int endVersion)方法是指定从什么版本升级到哪一版本，每次迁移都可以在定义的两个版本之间移动；
        //在重写的migrate(database: SupportSQLiteDatabase)方法中执行更新的sql语句，相应的要在Entity增加字段或者增加一个新的Entity类。
        private val MIGRATION_1_2 = object : Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                //FRUIT 表  新增一列
                database.execSQL("ALTER TABLE word_table add COLUMN content text")
            }
        }
    }


}