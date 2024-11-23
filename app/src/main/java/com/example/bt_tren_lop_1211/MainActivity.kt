package com.example.bt_tren_lop_1211

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {
    private val students = mutableListOf(
        "Nguyễn Văn An (SV001)",
        "Trần Thị Bảo (SV002)",
        "Lê Hoàng Cường (SV003)"
        // Thêm dữ liệu nếu cần
    )
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar) // Set Toolbar as ActionBar

        listView = findViewById(R.id.list_view_students)
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, students)
        listView.adapter = adapter

        // Đăng ký ContextMenu cho ListView
        registerForContextMenu(listView)
    }


    // Tạo OptionMenu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add_new -> {
                // Chuyển đến Activity thêm sinh viên mới
                val intent = Intent(this, AddEditStudentActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD_STUDENT)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Tạo ContextMenu cho ListView
    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        val position = info.position
        when (item.itemId) {
            R.id.menu_edit -> {
                // Chuyển đến Activity chỉnh sửa sinh viên
                val intent = Intent(this, AddEditStudentActivity::class.java)
                intent.putExtra("student", students[position])
                intent.putExtra("position", position)
                startActivityForResult(intent, REQUEST_EDIT_STUDENT)
                return true
            }

            R.id.menu_remove -> {
                // Hiển thị hộp thoại xác nhận xóa
                AlertDialog.Builder(this)
                    .setTitle("Xóa sinh viên")
                    .setMessage("Bạn có chắc chắn muốn xóa sinh viên này không?")
                    .setPositiveButton("Xóa") { _, _ ->
                        students.removeAt(position)
                        adapter.notifyDataSetChanged()
                    }
                    .setNegativeButton("Hủy", null)
                    .show()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    // Xử lý kết quả trả về từ com.example.bt_tren_lop_1211.AddEditStudentActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val student = data.getStringExtra("student") ?: return
            val position = data.getIntExtra("position", -1)
            when (requestCode) {
                REQUEST_ADD_STUDENT -> {
                    students.add(student)
                    adapter.notifyDataSetChanged()
                }

                REQUEST_EDIT_STUDENT -> {
                    if (position != -1) {
                        students[position] = student
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    companion object {
        const val REQUEST_ADD_STUDENT = 1
        const val REQUEST_EDIT_STUDENT = 2
    }
}
