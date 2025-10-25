package com.example.notebook

import androidx.appcompat.app.AppCompatActivity

open class MyBaseActivity : AppCompatActivity() {
    protected val CREATE_ACTION = 0x000312
    protected val EDIT_ACTION = 0x000313

    protected val EXTRA_TEXT = "text"
    protected val EXTRA_ID = "id"
}