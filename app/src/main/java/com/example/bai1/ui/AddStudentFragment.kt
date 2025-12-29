package com.example.bai1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.bai1.StudentModel
import com.example.bai1.StudentViewModel
import com.example.bai1.databinding.FragmentAddStudentBinding

class AddStudentFragment : Fragment() {
    private var uiBinding: FragmentAddStudentBinding? = null
    private val binding get() = uiBinding!!
    private val vm: StudentViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        uiBinding = FragmentAddStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnActionSave.setOnClickListener { processSave() }
    }

    private fun processSave() {
        val sid = binding.edtMssv.text.toString().trim()
        val name = binding.edtHoten.text.toString().trim()
        val tel = binding.edtPhone.text.toString().trim()
        val loc = binding.edtAddress.text.toString().trim()

        if (sid.isEmpty() || name.isEmpty()) {
            notifyUser("Vui lòng điền các thông tin bắt buộc")
            return
        }

        if (vm.checkIdExists(sid)) {
            notifyUser("MSSV này đã có chủ")
            binding.edtMssv.requestFocus()
        } else {
            val newItem = StudentModel(sid, name, tel, loc)
            vm.addNewStudent(newItem)
            notifyUser("Lưu thành công")
            findNavController().popBackStack()
        }
    }

    private fun notifyUser(text: String) {
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        uiBinding = null
    }
}
