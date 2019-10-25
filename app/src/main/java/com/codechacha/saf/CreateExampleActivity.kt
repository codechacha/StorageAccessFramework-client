package com.codechacha.saf

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_open_example.*
import kotlinx.coroutines.*
import java.io.FileOutputStream

class CreateExampleActivity : AppCompatActivity() {
    private val TAG = "CreateExampleActivity"
    private val WRITE_REQUEST_CODE: Int = 43

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        createFile()
    }

    private fun createFile() {
        val fileName = "NewImage.jpg"
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/jpg"
            putExtra(Intent.EXTRA_TITLE, fileName)
        }

        startActivityForResult(intent, WRITE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        
        if (requestCode == WRITE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                Log.i(TAG, "Uri: $uri")
                writeImage(uri)
            }
        }
    }

    private fun writeImage(uri: Uri) {
        GlobalScope.launch {
            contentResolver.openFileDescriptor(uri, "w").use {
                FileOutputStream(it!!.fileDescriptor).use { it ->
                    writeFromRawDataToFile(it)
                    it.close()
                }
            }
            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Done writing an image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun writeFromRawDataToFile(outStream: FileOutputStream) {
        val imageInputStream = resources.openRawResource(R.raw.my_image)
        while (true) {
            val data = imageInputStream.read()
            if (data == -1) {
                break
            }
            outStream.write(data)
        }
        imageInputStream.close()
    }

}
