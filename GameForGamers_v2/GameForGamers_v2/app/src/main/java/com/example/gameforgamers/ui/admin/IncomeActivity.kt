package com.example.gameforgamers.ui.admin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameforgamers.ui.admin.IncomeAdapter
import com.example.gameforgamers.data1.IncomeRepository
import com.example.gameforgamers.databinding.ActivityIncomeBinding

class IncomeActivity : AppCompatActivity() {

    private lateinit var b: ActivityIncomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityIncomeBinding.inflate(layoutInflater)
        setContentView(b.root)

        b.rvIncome.layoutManager = LinearLayoutManager(this)
        b.rvIncome.adapter = IncomeAdapter(IncomeRepository.all())
    }
}