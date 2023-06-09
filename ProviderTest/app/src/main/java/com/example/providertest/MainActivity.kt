package com.example.providertest

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.contentValuesOf
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var bookId:String? = null
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addData.setOnClickListener {
            val uri = Uri.parse("content://com.example.sqlite.provider/Book")
            val values = contentValuesOf("name" to "A Clash of Kings",
                "author" to "George Martin","pages" to 1040,"price" to 22.85
            )
            val newUri = contentResolver.insert(uri,values)
            bookId = newUri?.pathSegments?.get(1)
        }
        queryData.setOnClickListener {
            val uri = Uri.parse("content://com.example.sqlite.provider/Book")
            contentResolver.query(uri,null,null,null,null)?.apply {
                while (moveToNext()){
                    val name = getString(getColumnIndex("name"))
                    val author = getString(getColumnIndex("author"))
                    val pages = getString(getColumnIndex("pages"))
                    val price = getString(getColumnIndex("price"))
                    Log.d("MainActivity","book name is $name")
                    Log.d("MainActivity","book author is $author")
                    Log.d("MainActivity","book pages is $pages")
                    Log.d("MainActivity","book price is $price")
                }
                close()
            }
        }
        updateData.setOnClickListener {
            bookId?.let {
                val uri = Uri.parse("content://com.example.sqlite.provider/Book/$it")
                val values = contentValuesOf("name" to "A Storm of Swords","pages" to 1216,"price" to 24.05)
                contentResolver.update(uri,values,null,null)
            }
        }
        deleteData.setOnClickListener {
            bookId?.let {
                val uri = Uri.parse("content://com.example.sqlite.provider/Book/$it")
                contentResolver.delete(uri,null,null)
            }
        }
    }
}