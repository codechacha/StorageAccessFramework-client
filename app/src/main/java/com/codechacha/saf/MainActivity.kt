package com.codechacha.saf

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startOpenExample(view: View) {
        val intent = Intent()
        intent.component = ComponentName(this, OpenExampleActivity::class.java)
        startActivity(intent)
    }

    fun startCreateExample(view: View) {
        val intent = Intent()
        intent.component = ComponentName(this, CreateExampleActivity::class.java)
        startActivity(intent)
    }

    fun startDeleteExample(view: View) {
        val intent = Intent()
        intent.component = ComponentName(this, DeleteExampleActivity::class.java)
        startActivity(intent)
    }

    fun startTreeExample(view: View) {
        val intent = Intent()
        intent.component = ComponentName(this, TreeExampleActivity::class.java)
        startActivity(intent)
    }
}
