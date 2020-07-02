package com.example.mvvmsqliteapp.viewmodel

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import com.example.mvvmsqliteapp.R
import com.example.mvvmsqliteapp.model.EmpModelClass
import com.example.mvvmsqliteapp.view.MyListAdapter

class DataViewModel:ViewModel() {
    //method for saving records in database
    fun saveRecord(context: Context, u_id:TextView, u_email:TextView, u_name:TextView): Long {
        val id = u_id.text.toString()
        val name = u_name.text.toString()
        val email = u_email.text.toString()
        val databaseHandler: DatabaseHandler = DatabaseHandler(context)
        return if(id.trim()!="" && name.trim()!="" && email.trim()!=""){
             databaseHandler.addEmployee(EmpModelClass(Integer.parseInt(id),name, email))
        }
        else{
          -1
        }

    }
    //method for read records from database in ListView
    fun viewRecord(context: Context,listView: ListView){
        //creating the instance of DatabaseHandler class
        val databaseHandler: DatabaseHandler= DatabaseHandler(context)
        //calling the viewEmployee method of DatabaseHandler class to read the records
        val emp: List<EmpModelClass> = databaseHandler.viewEmployee()
        val empArrayId = Array<String>(emp.size){"0"}
        val empArrayName = Array<String>(emp.size){"null"}
        val empArrayEmail = Array<String>(emp.size){"null"}
        var index = 0
        for(e in emp){
            empArrayId[index] = e.userId.toString()
            empArrayName[index] = e.userName
            empArrayEmail[index] = e.userEmail
            index++
        }
        //creating custom ArrayAdapter
        val myListAdapter = MyListAdapter(context as Activity,empArrayId,empArrayName,empArrayEmail)
        listView.adapter = myListAdapter
    }
    //method for updating records based on user id
    fun updateRecord(context: Context){
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = inflater.inflate(R.layout.update_dialog, null)
        dialogBuilder.setView(dialogView)

        val edtId = dialogView.findViewById(R.id.updateId) as EditText
        val edtName = dialogView.findViewById(R.id.updateName) as EditText
        val edtEmail = dialogView.findViewById(R.id.updateEmail) as EditText

        dialogBuilder.setTitle("Update Record")
        dialogBuilder.setMessage("Enter data below")
        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->

            val updateId = edtId.text.toString()
            val updateName = edtName.text.toString()
            val updateEmail = edtEmail.text.toString()
            //creating the instance of DatabaseHandler class
            val databaseHandler: DatabaseHandler= DatabaseHandler(context)
            if(updateId.trim()!="" && updateName.trim()!="" && updateEmail.trim()!=""){
                //calling the updateEmployee method of DatabaseHandler class to update record
                val status = databaseHandler.updateEmployee(EmpModelClass(Integer.parseInt(updateId),updateName, updateEmail))
                if(status > -1){
                    Toast.makeText(context,"record update", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(context,"id or name or email cannot be blank", Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }
    //method for deleting records based on id
    fun deleteRecord(context: Context){
        //creating AlertDialog for taking user id
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView = inflater.inflate(R.layout.delete_dialog, null)
        dialogBuilder.setView(dialogView)

        val dltId = dialogView.findViewById(R.id.deleteId) as EditText
        dialogBuilder.setTitle("Delete Record")
        dialogBuilder.setMessage("Enter id below")
        dialogBuilder.setPositiveButton("Delete", DialogInterface.OnClickListener { _, _ ->

            val deleteId = dltId.text.toString()
            //creating the instance of DatabaseHandler class
            val databaseHandler: DatabaseHandler= DatabaseHandler(context)
            if(deleteId.trim()!=""){
                //calling the deleteEmployee method of DatabaseHandler class to delete record
                val status = databaseHandler.deleteEmployee(EmpModelClass(Integer.parseInt(deleteId),"",""))
                if(status > -1){
                    Toast.makeText(context,"record deleted", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(context,"id or name or email cannot be blank", Toast.LENGTH_LONG).show()
            }

        })
        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            //pass
        })
        val b = dialogBuilder.create()
        b.show()
    }
}