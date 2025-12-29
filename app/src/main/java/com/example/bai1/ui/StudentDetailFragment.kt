package com.example.bai1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bai1.StudentModel
import com.example.bai1.StudentViewModel
import com.example.bai1.databinding.FragmentStudentDetailBinding

class StudentDetailFragment : Fragment() {
    private var detailBinding: FragmentStudentDetailBinding? = null
    private val binding get() = detailBinding!!
    private val appViewModel: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        detailBinding = FragmentStudentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        appViewModel.selectedStudent.observe(viewLifecycleOwner) { info ->
            info?.let { displayData(it) }
        }

        binding.btnModify.setOnClickListener { onUpdateClicked() }
        binding.btnRemove.setOnClickListener { onDeleteClicked() }
    }

    private fun displayData(item: StudentModel) {
        binding.editMssv.setText(item.mssv)
        binding.editHoten.setText(item.hoTen)
        binding.editPhone.setText(item.soDienThoai)
        binding.editAddr.setText(item.diaChi)
    }

    private fun onUpdateClicked() {
        val oldData = appViewModel.selectedStudent.value ?: return

        val mssv = binding.editMssv.text.toString().trim()
        val name = binding.editHoten.text.toString().trim()
        val phone = binding.editPhone.text.toString().trim()
        val addr = binding.editAddr.text.toString().trim()

        if (mssv.isEmpty() || name.isEmpty()) {
            showToast("Vui lòng điền đủ thông tin")
            return
        }

        if (mssv != oldData.mssv && appViewModel.checkIdExists(mssv)) {
            showToast("MSSV đã tồn tại trên hệ thống")
        } else {
            val updated = StudentModel(mssv, name, phone, addr)
            appViewModel.updateInfo(oldData.mssv, updated)
            showToast("Đã lưu thông tin mới")
            findNavController().navigateUp()
        }
    }

    private fun onDeleteClicked() {
        val target = appViewModel.selectedStudent.value ?: return

        AlertDialog.Builder(requireContext())
            .setTitle("Xóa sinh viên")
            .setMessage("Gỡ bỏ ${target.hoTen}?")
            .setPositiveButton("Xóa") { _, _ ->
                appViewModel.removeStudentById(target.mssv)
                showToast("Đã xóa")
                findNavController().popBackStack()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    private fun showToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        appViewModel.resetSelection()
        detailBinding = null
    }
}
