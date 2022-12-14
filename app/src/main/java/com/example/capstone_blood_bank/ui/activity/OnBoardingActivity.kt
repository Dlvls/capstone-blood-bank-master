package com.example.capstone_blood_bank.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.capstone_blood_bank.R
import com.example.capstone_blood_bank.database.OnBoardingItem
import com.example.capstone_blood_bank.databinding.ActivityOnBoardingBinding
import com.example.capstone_blood_bank.ui.adapter.OnboardingItemAdapter

class OnBoardingActivity: AppCompatActivity() {

    private lateinit var binding:               ActivityOnBoardingBinding
    private lateinit var onboardingItemAdapter: OnboardingItemAdapter
    private lateinit var indicatorsContainer:   LinearLayout

    override fun onCreate( savedInstanceState: Bundle? ) {
        super.onCreate( savedInstanceState )

        binding = ActivityOnBoardingBinding.inflate( layoutInflater )
        setContentView( binding.root )

        setActionBtn()
        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator( 0 )
    }

    private fun setActionBtn() {
        binding.btnRegister.setOnClickListener {
            val intentRegister = Intent( this, SignUpActivity::class.java )

            startActivity( intentRegister )
            finishAffinity()
        }

        binding.btnLogin.setOnClickListener {
            val intentLogin = Intent( this, SignInActivity::class.java )

            startActivity( intentLogin )
            finishAffinity()
        }
    }

    private fun setOnboardingItems() {
        onboardingItemAdapter = OnboardingItemAdapter(
            listOf(
                OnBoardingItem(
                    R.drawable.img_onbrd1,
                    resources.getString( R.string.onboarding ),
                    resources.getString( R.string.onbrd_text_1 )
                ),
                OnBoardingItem(
                    R.drawable.img_onbrd2,
                    resources.getString( R.string.onboarding ),
                    resources.getString( R.string.onbrd_text_2 )
                ),
                OnBoardingItem(
                    R.drawable.img_onbrd3,
                    resources.getString( R.string.onboarding ),
                    resources.getString( R.string.onbrd_text_3 )
                ),
                OnBoardingItem(
                    R.drawable.img_onbrd4,
                    resources.getString( R.string.onboarding ),
                    resources.getString( R.string.onbrd_text_4 )
                )
            )
        )

        val onboardingViewPager = findViewById<ViewPager2>( R.id.vp_onbrd )

        onboardingViewPager.adapter = onboardingItemAdapter

        onboardingViewPager.registerOnPageChangeCallback( object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected( position: Int ) {
                super.onPageSelected( position )

                setCurrentIndicator( position )
            }
        } )
    }

    private fun setupIndicators() {
        indicatorsContainer = findViewById( R.id.indicCtr )

        val indicators                              = arrayOfNulls<ImageView>( onboardingItemAdapter.itemCount )
        val layoutParams: LinearLayout.LayoutParams = LinearLayout.LayoutParams( WRAP_CONTENT, WRAP_CONTENT )

        layoutParams.setMargins( 8, 0, 8, 0 )

        for( i in indicators.indices ) {
            indicators[i] = ImageView( applicationContext )
            indicators[i]?.let {
                it.setImageDrawable(
                    ContextCompat.getDrawable( applicationContext, R.drawable.indic_inactive )
                )
                it.layoutParams = layoutParams

                indicatorsContainer.addView( it )
            }
        }
    }

    private fun setCurrentIndicator( position: Int ) {
        val childCount = indicatorsContainer.childCount

        for( i in 0 until childCount ) {
            val imageView = indicatorsContainer.getChildAt( i ) as ImageView

            if( i == position ) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable( applicationContext, R.drawable.indic_active )
                )
            }
            else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable( applicationContext, R.drawable.indic_inactive )
                )
            }
        }
    }
}