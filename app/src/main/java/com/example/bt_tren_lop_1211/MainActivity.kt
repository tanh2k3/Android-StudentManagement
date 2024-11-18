package com.example.bt_tren_lop_1211

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var lastDeletedStudent: StudentModel? = null
    private var lastDeletedPosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val students = mutableListOf(
            StudentModel("Nguyễn Văn An", "SV001"),
            StudentModel("Trần Thị Bảo", "SV002"),
            StudentModel("Lê Hoàng Cường", "SV003"),
            StudentModel("Phạm Thị Dung", "SV004"),
            StudentModel("Đỗ Minh Đức", "SV005"),
            StudentModel("Vũ Thị Hoa", "SV006"),
            StudentModel("Hoàng Văn Hải", "SV007"),
            StudentModel("Bùi Thị Hạnh", "SV008"),
            StudentModel("Đinh Văn Hùng", "SV009"),
            StudentModel("Nguyễn Thị Linh", "SV010"),
            StudentModel("Phạm Văn Long", "SV011"),
            StudentModel("Trần Thị Mai", "SV012"),
            StudentModel("Lê Thị Ngọc", "SV013"),
            StudentModel("Vũ Văn Nam", "SV014"),
            StudentModel("Hoàng Thị Phương", "SV015"),
            StudentModel("Đỗ Văn Quân", "SV016"),
            StudentModel("Nguyễn Thị Thu", "SV017"),
            StudentModel("Trần Văn Tài", "SV018"),
            StudentModel("Phạm Thị Tuyết", "SV019"),
            StudentModel("Lê Văn Vũ", "SV020")
        )

        val studentAdapter = StudentAdapter(students)

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        findViewById<Button>(R.id.btn_add_new).setOnClickListener {
            showAddStudentDialog(studentAdapter, students)
        }
    }

    // Hàm hiển thị dialog để thêm sinh viên
    private fun showAddStudentDialog(adapter: StudentAdapter, students: MutableList<StudentModel>) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_student, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.input_student_name)
        val idInput = dialogView.findViewById<EditText>(R.id.input_student_id)

        AlertDialog.Builder(this)
            .setTitle("Add New Student")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = nameInput.text.toString()
                val id = idInput.text.toString()
                if (name.isNotBlank() && id.isNotBlank()) {
                    val newStudent = StudentModel(name, id)
                    students.add(newStudent)
                    adapter.notifyItemInserted(students.size - 1)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun showEditStudentDialog(students: MutableList<StudentModel>, position: Int, adapter: StudentAdapter) {
        val student = students[position]
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_student, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.input_student_name)
        val idInput = dialogView.findViewById<EditText>(R.id.input_student_id)

        nameInput.setText(student.studentName)
        idInput.setText(student.studentId)

        AlertDialog.Builder(this)
            .setTitle("Edit Student")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                val updatedName = nameInput.text.toString()
                val updatedId = idInput.text.toString()
                if (updatedName.isNotBlank() && updatedId.isNotBlank()) {
                    students[position] = StudentModel(updatedName, updatedId)
                    adapter.notifyItemChanged(position)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    fun showDeleteConfirmation(students: MutableList<StudentModel>, position: Int, adapter: StudentAdapter) {
        val student = students[position]
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete ${student.studentName}?")
            .setPositiveButton("Yes") { _, _ ->
                lastDeletedStudent = student
                lastDeletedPosition = position
                students.removeAt(position)
                adapter.notifyItemRemoved(position)

                Snackbar.make(findViewById(R.id.main), "${student.studentName} removed", Snackbar.LENGTH_LONG)
                    .setAction("Undo") {
                        lastDeletedStudent?.let {
                            students.add(lastDeletedPosition!!, it)
                            adapter.notifyItemInserted(lastDeletedPosition!!)
                            lastDeletedStudent = null
                            lastDeletedPosition = null
                        }
                    }
                    .show()
            }
            .setNegativeButton("No", null)
            .show()
    }


}