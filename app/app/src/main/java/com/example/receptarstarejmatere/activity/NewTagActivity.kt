package com.example.receptarstarejmatere.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.receptarstarejmatere.R
import com.example.receptarstarejmatere.application.App
import com.example.receptarstarejmatere.database.model.Tag
import kotlinx.coroutines.Dispatchers
import kotlin.concurrent.thread
import kotlin.random.Random

class NewTagActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_tag)

        val editText = findViewById<EditText>(R.id.new_tag_name)
        val saveButton = findViewById<Button>(R.id.new_tag_save_button)

        saveButton.setOnClickListener {
            saveNewTag(editText.text.toString())

            Toast.makeText(this, "Nový tag bol pridaný", Toast.LENGTH_SHORT).show()
            
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveNewTag(name: String) {
        val newTag = Tag(name)

        thread {
            App.tagRepository.insert(newTag)
        }
    }
}