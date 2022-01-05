package com.example.practicewithcoroutines

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    var customProgressDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val backgroundExecutionBtn: Button = findViewById(R.id.backgroundExecutionBtn)
        backgroundExecutionBtn.setOnClickListener {
            lifecycleScope.launch{
                execute("Task Executed Successfully")
            }

        }
    }

    private suspend fun execute(result: String) {
        showProgressDialog()
        withContext(Dispatchers.IO) {
            for (i in 1..100000) {
                Log.e("delay: ", "" + i)
            }
            runOnUiThread {
                cancelProgressDialog()
                Toast.makeText(this@MainActivity, "Done", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun cancelProgressDialog() {
        if (customProgressDialog != null) {
            customProgressDialog?.dismiss()
            customProgressDialog = null
        }

    }

    private fun showProgressDialog(){
        customProgressDialog = Dialog(this@MainActivity)

        customProgressDialog?.setContentView(R.layout.dialog_custom_progress)

        customProgressDialog?.show()
    }
}