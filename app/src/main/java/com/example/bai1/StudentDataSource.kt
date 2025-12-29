package com.example.bai1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class StudentDataSource(context: Context) {

    private val dbHelper = DatabaseHelper(context)
    private var database: SQLiteDatabase? = null

    fun openConnection() {
        Log.i("StudentDB", "Opening database...")
        database = dbHelper.writableDatabase
    }

    fun closeConnection() {
        Log.i("StudentDB", "Closing database...")
        dbHelper.close()
    }

    fun insertStudent(student: StudentModel) {
        val contentValues = ContentValues().apply {
            put(DatabaseHelper.STUDENT_ID_KEY, student.mssv)
            put(DatabaseHelper.FULLNAME_KEY, student.hoTen)
            put(DatabaseHelper.PHONE_KEY, student.soDienThoai)
            put(DatabaseHelper.ADDRESS_KEY, student.diaChi)
        }
        database?.insert(DatabaseHelper.TABLE_NAME, null, contentValues)
    }

    fun getAllStudents(): List<StudentModel> {
        val resultList = mutableListOf<StudentModel>()
        val queryCursor = database?.query(
            DatabaseHelper.TABLE_NAME, 
            null, null, null, null, null, 
            "${DatabaseHelper.FULLNAME_KEY} ASC"
        )
        
        queryCursor?.use { 
            while (it.moveToNext()) {
                resultList.add(parseStudentFromCursor(it))
            }
        }
        return resultList
    }

    fun modifyStudent(oldId: String, newInfo: StudentModel) {
        val updateValues = ContentValues().apply {
            put(DatabaseHelper.STUDENT_ID_KEY, newInfo.mssv)
            put(DatabaseHelper.FULLNAME_KEY, newInfo.hoTen)
            put(DatabaseHelper.PHONE_KEY, newInfo.soDienThoai)
            put(DatabaseHelper.ADDRESS_KEY, newInfo.diaChi)
        }
        database?.update(
            DatabaseHelper.TABLE_NAME, 
            updateValues, 
            "${DatabaseHelper.STUDENT_ID_KEY} = ?", 
            arrayOf(oldId)
        )
    }

    fun deleteById(sid: String) {
        database?.delete(
            DatabaseHelper.TABLE_NAME, 
            "${DatabaseHelper.STUDENT_ID_KEY} = ?", 
            arrayOf(sid)
        )
    }

    fun existsInDb(sid: String): Boolean {
        val checkCursor = database?.query(
            DatabaseHelper.TABLE_NAME,
            arrayOf(DatabaseHelper.STUDENT_ID_KEY),
            "${DatabaseHelper.STUDENT_ID_KEY} = ?",
            arrayOf(sid), null, null, null
        )
        val hasData = checkCursor?.use { it.count > 0 } ?: false
        return hasData
    }

    private fun parseStudentFromCursor(c: Cursor): StudentModel {
        return StudentModel(
            mssv = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.STUDENT_ID_KEY)),
            hoTen = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.FULLNAME_KEY)),
            soDienThoai = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.PHONE_KEY)),
            diaChi = c.getString(c.getColumnIndexOrThrow(DatabaseHelper.ADDRESS_KEY))
        )
    }
}
