package com.pubscale.demo.android.yt

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class Wallet(private val user: User) {

    private val db: FirebaseFirestore = Firebase.firestore

    fun getBalance(listener: (Double) -> Unit) {
        db.collection("apps").document("my_app").collection("users")
            .document(user.getUserId())
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    listener.invoke(snapshot.getDouble("balance") ?: 0.0)
                } else {
                    setInitialBalance()
                    listener.invoke(0.0)
                }
            }
    }

    private fun setInitialBalance() {
        db.collection("apps").document("my_app").collection("users").document(user.getUserId()).set(
            mapOf(
                "balance" to 0.0
            )
        )
    }
}