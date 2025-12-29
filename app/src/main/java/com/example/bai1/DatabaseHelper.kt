package com.example.bai1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    companion object {
        private const val DB_VERSION = 2
        private const val DB_NAME = "AppDatabase_V2.db"

        const val TABLE_NAME = "tbl_students"
        const val ID_PK = "id_internal"
        const val STUDENT_ID_KEY = "sid"
        const val FULLNAME_KEY = "name_val"
        const val PHONE_KEY = "phone_val"
        const val ADDRESS_KEY = "addr_val"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createQuery = """
            CREATE TABLE $TABLE_NAME (
                $ID_PK INTEGER PRIMARY KEY AUTOINCREMENT,
                $STUDENT_ID_KEY TEXT UNIQUE,
                $FULLNAME_KEY TEXT NOT NULL,
                $PHONE_KEY TEXT,
                $ADDRESS_KEY TEXT
            )
        """
        db?.execSQL(createQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}
