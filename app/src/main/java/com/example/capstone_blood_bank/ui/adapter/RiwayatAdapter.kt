package com.example.capstone_blood_bank.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_blood_bank.database.BloodBankResponses
import com.example.capstone_blood_bank.databinding.HistoryItemBinding

class RiwayatAdapter(context: Context, modelInputList: MutableList<BloodBankResponses>, adapterCallback: RiwayatAdapterCallback) :
    RecyclerView.Adapter<RiwayatAdapter.MyViewHolder>() {

    var modelList : MutableList<BloodBankResponses>
    var mAdapterCallback: RiwayatAdapterCallback

    fun setUpData (modelList : List<BloodBankResponses>) {
        this.modelList.clear()
        this.modelList.addAll(modelList)
    }

    inner class MyViewHolder (private var binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(model: BloodBankResponses) {
            binding.apply {
                name.text = model.name
                email.text = model.email
                bloodType.text = model.bloodType
                dayEstimation.text = model.dayEstimation

                imageDelete.setOnClickListener {
                    val modelLaundry: BloodBankResponses = modelList[adapterPosition]
                    mAdapterCallback.onDelete(modelLaundry)
                }
            }

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(modelList[position])

    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    init {
        modelList = modelInputList
        mAdapterCallback = adapterCallback
    }

    interface RiwayatAdapterCallback {
        fun onDelete(modelDatabase: BloodBankResponses?)
    }
}