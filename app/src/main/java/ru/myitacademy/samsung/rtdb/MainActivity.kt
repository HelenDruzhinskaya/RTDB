package ru .myitacademy.samsung.rtdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class MainActivity : AppCompatActivity(){
    lateinit var db: DatabaseReference
var key = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun add(view: View){
        db = Firebase.database.getReference("friend")
        val id = key++
        val name = (findViewById<EditText>(R.id.editTextTextPersonName)).text.toString()
        val tel = (findViewById<EditText>(R.id.editTextTextPersonName2)).text.toString()
        val newFriend = Friend(id,name,tel)
        db.push().setValue(newFriend)

    }
    fun read(view:View){
        db = Firebase.database.getReference("friend")
        val lw = findViewById<ListView>(R.id.lw)
        var listFriend = ArrayList<String>()
        val adapter = ArrayAdapter(applicationContext,android.R.layout.simple_list_item_1,listFriend)
        lw.adapter = adapter
    val velistener = object: ValueEventListener{
    override fun onDataChange(snapshot: DataSnapshot) {
        val x = snapshot.children.iterator()
        if(listFriend.size>0) listFriend.clear()
           do {
               listFriend.add(x.next().getValue(Friend::class.java).toString())
           } while(x.hasNext())
        adapter.notifyDataSetChanged()
        }
    override fun onCancelled(error: DatabaseError) {}
    }
        db.addValueEventListener(velistener)
    }
}

class Friend {
var id = 0
    var name = ""
    var tel = ""
constructor()
    constructor(id: Int, name: String, tel: String) {
        this.id = id
        this.name = name
        this.tel = tel
    }
    override fun toString(): String {
        return "name: " + name + "\t\ttelno: " + tel
    }
}