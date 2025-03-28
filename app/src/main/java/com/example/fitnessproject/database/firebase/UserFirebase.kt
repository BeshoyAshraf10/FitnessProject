package com.example.fitnessproject.database.firebase

import android.util.Log
import com.example.fitnessproject.model.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class UserFirebase {
    private val database: DatabaseReference = Firebase.database.reference
    private val db = Firebase.firestore

    fun writeNewUser(userId: String, fName: String, lName: String, email: String) {
        val user = User(email, fName, lName)
        db.collection("users")
            .document(userId)
            .set(user)
            .addOnFailureListener { e ->
                Log.d("UserData", "Inside_saveUser")
                Log.d("UserData", "Exception= ${e.message}")
            }
    }

    fun addUserData(
        userId: String,
        age: Int,
        height: Int,
        weight: Int,
        gender: String,
        bmi: Float,
        bmr: Int,
        calories: Int,
        bwb: String
    ) {
        val userData = mapOf(
            "weight" to weight,
            "height" to height,
            "age" to age,
            "gender" to gender,
            "bmi" to bmi,
            "bmr" to bmr,
            "calories" to calories,
            "bwb" to bwb
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

    suspend fun updateUserData (
        userId: String,
        firstName: String,
        lastName: String,
        age: Int,
        height: Int,
        weight: Int,
        goalWeight: Int,
    ): Boolean= suspendCancellableCoroutine{ continuation ->
        var flag = false
        val userData = mapOf(
            "firstName" to firstName,
            "lastName" to lastName,
            "age" to age,
            "height" to height,
            "weight" to weight,
            "goalWeight" to goalWeight
        )
        // Firestore update operation
        db.collection("users").document(userId).update(userData)
            .addOnSuccessListener {
                Log.d("UserData", "User data updated successfully!")
                if (continuation.isActive) {
                    continuation.resume(true) // Resume with true if success
                }
            }
            .addOnFailureListener { e ->
                Log.e("UserData", "Error updating user data", e)
                if (continuation.isActive) {
                    continuation.resumeWithException(e) // Resume with exception if failure
                }
            }
    }

    suspend fun getUserData(userId: String): User? {
        return try {
            val documentSnapshot = db.collection("users")
                .document(userId)
                .get()
                .await() // Requires kotlinx-coroutines-play-services
            documentSnapshot.toObject(User::class.java)
        } catch (e: Exception) {
            Log.e("UserData", "Error getting document", e)
            null
        }
    }
}