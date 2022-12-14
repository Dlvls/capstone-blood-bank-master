package com.example.capstone_blood_bank.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone_blood_bank.R
import com.example.capstone_blood_bank.database.OnBoardingItem

class OnBoardingAdapter( private val onBoardingItems: List<OnBoardingItem> ): RecyclerView.Adapter<OnBoardingAdapter.OnBoardingItemViewHolder>() {

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): OnBoardingItemViewHolder {
        return OnBoardingItemViewHolder(
            LayoutInflater.from( parent.context ).inflate( R.layout.onboarding_item, parent, false )
        )
    }

    override fun onBindViewHolder( holder: OnBoardingItemViewHolder, position: Int ) {
        holder.bind( onBoardingItems[position] )
    }

    override fun getItemCount(): Int {
        return onBoardingItems.size
    }

    inner class OnBoardingItemViewHolder( view: View ): RecyclerView.ViewHolder( view ) {
        private val imageOnBoarding = view.findViewById<ImageView>( R.id.iv_onbrd )
        private val textTitle       = view.findViewById<TextView>( R.id.tv_title )
        private val textDescription = view.findViewById<TextView>( R.id.tv_descr )

        fun bind( onBoardingItem: OnBoardingItem ) {
            imageOnBoarding.setImageResource( onBoardingItem.onBoardingImage )
            textTitle.text       = onBoardingItem.title
            textDescription.text = onBoardingItem.description
        }
    }
}