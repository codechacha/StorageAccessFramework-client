package com.codechacha.saf

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.DocumentsContract
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.*
import java.io.FileOutputStream

class DeleteExampleActivity : AppCompatActivity() {
    private val TAG = "DeleteExampleActivity"
    private val READ_REQUEST_CODE: Int = 42

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        openFile()
    }

    private fun openFile() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        }

        startActivityForResult(intent, READ_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                Log.i(TAG, "Uri: $uri")
                deleteFile(uri)
            }
        }
    }

    private fun deleteFile(uri: Uri) {
        DocumentsContract.deleteDocument(contentResolver, uri)
        Toast.makeText(applicationContext, "Done deleting an image", Toast.LENGTH_SHORT).show()
    }

}
