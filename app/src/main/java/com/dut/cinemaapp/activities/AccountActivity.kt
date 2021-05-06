package com.dut.cinemaapp.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dut.cinemaapp.R
import kotlinx.android.synthetic.main.tool_bar.*

class AccountActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_page)

        tool_bar.title = "Account"
        setActionBar(tool_bar)

        tool_bar_btn.text = "Main"
        tool_bar_btn.setOnClickListener {
            this.startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )
        }
    }
}