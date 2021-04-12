package com.eseo.firstapp.ui.test_recycler

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eseo.firstapp.R
import com.eseo.firstapp.ui.test_recycler.adapter.TestAdapter

class RecyclerActivity : AppCompatActivity() {

    companion object{
        fun getStartIntent(context: Context): Intent {
            return Intent(context, RecyclerActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

       val rv = findViewById<RecyclerView>(R.id.recycler)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = TestAdapter(arrayOf<String>("test","test2","mon_texte")) {
            Toast.makeText(this@RecyclerActivity, getString(R.string.your_entry)+it, Toast.LENGTH_LONG).show()
        }
    }
}