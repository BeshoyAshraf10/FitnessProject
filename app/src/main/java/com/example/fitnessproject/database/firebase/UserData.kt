package com.example.fitnessproject.database.firebase

import android.util.Log
import com.example.fitnessproject.model.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserData {
    private val database: DatabaseReference =  Firebase.database.reference
    private val db = Firebase.firestore

    fun writeNewUser(userId: String, fName: String,lName:String, email: String) {
        val user = User(email, fName, lName)
        db.collection("users")
            .document(userId)
            .set(user)
            .addOnFailureListener { e ->
                Log.d("UserData", "Inside_saveUser")
                Log.d("UserData", "Exception= ${e.message}")
            }
    }
    fun addUserData(userId: String,age: Int,height: Int,weight: Int,gender: String, bmi: Double, bmr: Double, activityLevel: String, calories: Int) {
        val userData = mapOf(
            "weight" to weight,
            "height" to height,
            "age" to age,
            "gender" to gender,
            "bmi" to bmi,
            "bmr" to bmr,
            "activityLevel" to activityLevel,
            "calories" to calories
        )
        db.collection("users")
            .document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    // Document exists, perform update
                    db.collection("users").document(userId).update(userData)
                        .addOnSuccessListener {
                            Log.d("UserData", "User data updated successfully!")
                        }
                        .addOnFailureListener { e ->
                            Log.e("UserData", "Error updating user data", e)
                        }
                } else {
                    // Document does not exist, create it
                    db.collection("users").document(userId).set(userData)
                        .addOnSuccessListener {
                            Log.d("UserData", "User data created successfully!")
                        }
                        .addOnFailureListener { e ->
                            Log.e("UserData", "Error creating user data", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.e("UserData", "Error fetching document", e)
            }


    }
}