package com.codechacha.saf

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile



class TreeExampleActivity : AppCompatActivity() {
    private val TAG = "TreeExampleActivity"
    private val PREF_URI = "pref_uri5"
    private val REQUEST_OPEN_TREE = 44

    private val prefs: SharedPreferences by lazy {
        getSharedPreferences("test", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        val uriString = prefs.getString(PREF_URI, null)

        if (uriString == null) {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
            startActivityForResult(intent, REQUEST_OPEN_TREE)
        } else {
            val uri = Uri.parse(uriString)
            printDirFiles(uri)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        if (requestCode == REQUEST_OPEN_TREE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { uri ->
                val takeFlags: Int = intent.flags and
                        (Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                contentResolver.takePersistableUriPermission(uri, takeFlags)
                printDirFiles(uri)
                prefs.edit().putString(PREF_URI, uri.toString()).apply()
                Toast.makeText(this, "OK, run the activity again", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun printDirFiles(uri: Uri) {
        val docFile = DocumentFile.fromTreeUri(this, uri)!!
        if (docFile.canRead()) {
            val files = docFile.listFiles()
            for (file : DocumentFile in files) {
                Log.d(TAG, "List files: ${file.name}")
            }
        }
        Toast.makeText(
            this,
            "canRead: ${docFile.canRead()} canWrite: ${docFile.canWrite()}",
            Toast.LENGTH_LONG
        ).show()
    }
}