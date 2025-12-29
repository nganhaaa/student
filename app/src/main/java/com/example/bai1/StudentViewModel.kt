package com.example.bai1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val dataRepo = StudentDataSource(application)

    private val _studentList = MutableLiveData<List<StudentModel>>()
    val students: LiveData<List<StudentModel>> = _studentList

    private val _activeStudent = MutableLiveData<StudentModel?>()
    val selectedStudent: LiveData<StudentModel?> = _activeStudent

    init {
        dataRepo.openConnection()
        val initialData = dataRepo.getAllStudents()
        if (initialData.isEmpty()) {
            dataRepo.insertStudent(StudentModel("20228349", "Hoàng Phan Hữu Đức", "0987654321", "Nha Trang"))
            dataRepo.insertStudent(StudentModel("20228438", "Nguyễn Kiều Duyên", "0912345678", "Đà Nẵng"))
            dataRepo.insertStudent(StudentModel("20229496", "Ngô Mai Phương", "0999999999", "TP. Hồ Chí Minh"))
        }
        syncData()
    }

    private fun syncData() {
        _studentList.postValue(dataRepo.getAllStudents())
    }

    fun addNewStudent(item: StudentModel) {
        dataRepo.insertStudent(item)
        syncData()
    }

    fun updateInfo(oldId: String, newInfo: StudentModel) {
        dataRepo.modifyStudent(oldId, newInfo)
        syncData()
    }

    fun removeStudentById(id: String) {
        dataRepo.deleteById(id)
        syncData()
    }

    fun checkIdExists(id: String): Boolean {
        return dataRepo.existsInDb(id)
    }

    fun setDetailStudent(item: StudentModel) {
        _activeStudent.value = item
    }

    fun resetSelection() {
        _activeStudent.value = null
    }

    override fun onCleared() {
        dataRepo.closeConnection()
        super.onCleared()
    }
}
