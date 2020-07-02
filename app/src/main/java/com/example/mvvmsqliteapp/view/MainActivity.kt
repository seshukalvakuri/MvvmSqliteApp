package com.example.mvvmsqliteapp.view

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmsqliteapp.R
import com.example.mvvmsqliteapp.model.EmpModelClass
import com.example.mvvmsqliteapp.viewmodel.DataViewModel
import com.example.mvvmsqliteapp.viewmodel.DatabaseHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: DataViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(DataViewModel::class.java)
    }

    fun saveRecord(view: View){
        val insertReturn = viewModel.saveRecord(this, u_id, u_name, u_email)
        if(insertReturn > -1){
            Toast.makeText(this,"record insert", Toast.LENGTH_LONG).show()
            u_id.text.clear()
            u_name.text.clear()
            u_email.text.clear()
        } else {
            Toast.makeText(this,"id or name or email cannot be blank", Toast.LENGTH_LONG).show()
        }
    }

    fun viewRecord(view: View){
        viewModel.viewRecord(this, listView)
    }

    fun updateRecord(view: View){
        viewModel.updateRecord(this)
    }

    fun deleteRecord(view: View){
        viewModel.deleteRecord(this)
    }
}