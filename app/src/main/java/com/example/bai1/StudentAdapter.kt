package com.example.bai1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(
    private var dataList: List<StudentModel>,
    private val listener: (StudentModel) -> Unit
) : RecyclerView.Adapter<StudentAdapter.MainViewHolder>() {

    inner class MainViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        private val labelName: TextView = root.findViewById(R.id.lblName)
        private val labelId: TextView = root.findViewById(R.id.lblId)
        
        fun bind(item: StudentModel) {
            labelName.text = item.hoTen
            labelId.text = item.mssv
            itemView.setOnClickListener { listener(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sinhvien, parent, false)
        return MainViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: MainViewHolder, index: Int) {
        holder.bind(dataList[index])
    }

    override fun getItemCount(): Int = dataList.size

    fun refreshData(newList: List<StudentModel>) {
        this.dataList = newList
        notifyDataSetChanged()
    }
}
