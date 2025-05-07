package com.pubscale.demo.android.yt

import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class Wallet(private val user: User) {

    private val db: FirebaseDatabase = Firebase.database
    private val dbRef: DatabaseReference = db.getReference("app_demo_yt_android")

    fun getBalance(listener: (Double) -> Unit) {
        dbRef.child("users").child(user.getUserId()).child("balance")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val balance = snapshot.getValue(Double::class.java) ?: 0.0
                    if (balance == 0.0) {
                        setInitialBalance()
                        listener.invoke(2400.0)
                    } else {
                        listener.invoke(balance)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    listener.invoke(2400.0)
                }
            })
    }

    private fun setInitialBalance() {
        dbRef.child("users").child(user.getUserId()).child("balance").setValue(2400)
    }
}