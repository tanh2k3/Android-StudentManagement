package com.example.bt_tren_lop_1211

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddEditStudentActivity : AppCompatActivity() {
    private lateinit var editTextName: EditText
    private lateinit var editTextId: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_student)

        editTextName = findViewById(R.id.edit_text_name)
        editTextId = findViewById(R.id.edit_text_id)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)

        // Nếu chỉnh sửa, hiển thị dữ liệu hiện tại
        val student = intent.getStringExtra("student")
        val position = intent.getIntExtra("position", -1)
        student?.let {
            val parts = it.split(" (")
            editTextName.setText(parts[0])
            editTextId.setText(parts[1].removeSuffix(")"))
        }

        btnSubmit.setOnClickListener {
            val name = editTextName.text.toString()
            val id = editTextId.text.toString()
            val resultIntent = intent
            resultIntent.putExtra("student", "$name ($id)")
            resultIntent.putExtra("position", position)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
