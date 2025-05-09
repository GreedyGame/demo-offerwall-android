package com.pubscale.demo.android.yt

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.pubscale.demo.android.yt.databinding.ActivityMainBinding
import com.pubscale.sdkone.offerwall.OfferWall
import com.pubscale.sdkone.offerwall.OfferWallConfig
import com.pubscale.sdkone.offerwall.models.OfferWallInitListener
import com.pubscale.sdkone.offerwall.models.OfferWallListener
import com.pubscale.sdkone.offerwall.models.Reward
import com.pubscale.sdkone.offerwall.models.errors.InitError
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val user: User by lazy { User(context = this@MainActivity) }
    private val wallet: Wallet by lazy { Wallet(user = user) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()

        initOfferwall()

        binding.btnLaunch.setOnClickListener {
            val offerWallListener = object : OfferWallListener {

                override fun onOfferWallShowed() {
                    Log.d("PSOfferwall", "Offer wall showed.")
                }

                override fun onOfferWallClosed() {
                    Log.d("PSOfferwall", "Offer wall closed.")
                }

                override fun onRewardClaimed(reward: Reward) {
                    Log.d("PSOfferwall", "Offer wall reward claimed.")
                }

                override fun onFailed(message: String) {
                    Log.d("PSOfferwall", "onFailed: $message")
                }
            }

            OfferWall.launch(this@MainActivity, offerWallListener)

        }
    }

    private fun initOfferwall() {
        val offerWallConfig = OfferWallConfig.Builder(this@MainActivity, "13680145")
            .setUniqueId(user.getUserId())
            .build()
        OfferWall.init(offerWallConfig, object : OfferWallInitListener {
            override fun onInitSuccess() {
                Log.d("PSOfferwall", "Offer wall initialized")
            }

            override fun onInitFailed(error: InitError) {
                Log.d("PSOfferwall", "Offer wall init failed: ${error.message}")
            }
        })
    }

    private fun setupUI() {
        getWalletBalance()
        binding.tvTitle.text = HtmlCompat.fromHtml(
            getString(R.string.title_offerwall), HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    private fun getWalletBalance() {
        wallet.getBalance {
            binding.tvBalance.text = it.convert()
        }
    }

    private fun Double.convert(): String {
        val format = DecimalFormat("#,###")
        format.isDecimalSeparatorAlwaysShown = false
        return format.format(this).toString()
    }
}
