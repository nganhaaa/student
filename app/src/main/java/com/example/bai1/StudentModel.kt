package com.example.bai1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StudentModel(
    val mssv: String,
    val hoTen: String,
    val soDienThoai: String,
    val diaChi: String
) : Parcelable
