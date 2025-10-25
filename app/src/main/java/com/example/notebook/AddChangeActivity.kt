package com.example.notebook

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged

class AddChangeActivity : MyBaseActivity() {
    private lateinit var editText: EditText
    private lateinit var saveButton: Button
    private var itemId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_change)

        editText = findViewById(R.id.editText)
        saveButton = findViewById(R.id.saveButton)

        // получаем данные, если редактирование
        val text = intent.getStringExtra(EXTRA_TEXT)
        itemId = intent.getIntExtra(EXTRA_ID, -1)
        if (text != null) {
            editText.setText(text)
        }

        saveButton.isEnabled = false
        editText.doAfterTextChanged {
            saveButton.isEnabled = it?.isNotEmpty() == true
        }
    }

    fun saveNote(view: View) {
        val result = Intent()
        result.putExtra(EXTRA_TEXT, editText.text.toString())
        if (itemId != -1) {
            result.putExtra(EXTRA_ID, itemId)
        }
        setResult(RESULT_OK, result)
        finish()
    }

    fun cancelNote(view: View) {
        setResult(RESULT_CANCELED)
        finish()
    }
}