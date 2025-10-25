package com.example.notebook

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView

//сохранение и загрузка
import android.content.Context
import org.json.JSONArray

class MainActivity : MyBaseActivity() {
    private lateinit var listView: ListView
    private val items = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //загрузка
        loadNotes()

        listView = findViewById(R.id.listView)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, AddChangeActivity::class.java)
            intent.putExtra(EXTRA_ID, position)
            intent.putExtra(EXTRA_TEXT, items[position])
            startActivityForResult(intent, EDIT_ACTION)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            val text = data.getStringExtra(EXTRA_TEXT) ?: return

            when (requestCode) {
                CREATE_ACTION -> {
                    items.add(text)
                    adapter.notifyDataSetChanged()
                    saveNotes()
                }

                EDIT_ACTION -> {
                    val pos = data.getIntExtra(EXTRA_ID, -1)
                    if (pos != -1) {
                        items[pos] = text
                        adapter.notifyDataSetChanged()
                        saveNotes()
                    }
                }
            }
        }
    }

    fun addNote(view: View) {
        val intent = Intent(this, AddChangeActivity::class.java)
        startActivityForResult(intent, CREATE_ACTION)
    }

    //сохранение
    private fun saveNotes() {
        val prefs = getSharedPreferences("notes_prefs", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        val jsonArray = JSONArray()
        for (note in items) {
            jsonArray.put(note)
        }
        editor.putString("notes", jsonArray.toString())
        editor.apply()
    }

    //загрузка
    private fun loadNotes() {
        val prefs = getSharedPreferences("notes_prefs", Context.MODE_PRIVATE)
        val json = prefs.getString("notes", "[]")
        val jsonArray = JSONArray(json)

        for (i in 0 until jsonArray.length()) {
            items.add(jsonArray.getString(i))
        }
    }
}