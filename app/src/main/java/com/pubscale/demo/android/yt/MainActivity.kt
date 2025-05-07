package com.pubscale.demo.android.yt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.pubscale.demo.android.yt.databinding.ActivityMainBinding
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var user: User
    private lateinit var wallet: Wallet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        user = User(context = this@MainActivity)
        wallet = Wallet(user = user)
        getWalletBalance()
        binding.tvTitle.text = HtmlCompat.fromHtml(
            getString(R.string.title_offerwall),
            HtmlCompat.FROM_HTML_MODE_LEGACY
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
