package com.example.fitnessproject.viewModel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fitnessproject.database.localDB.RoomDBHelper
import com.example.fitnessproject.model.Activity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityViewModel(app: Application): AndroidViewModel(app) {
    var selectedActivity by mutableStateOf<Activity?>(null)
        private set

    fun selectActivity(activity: Activity) {
        selectedActivity = activity
    }

    private val db: RoomDBHelper = RoomDBHelper.getInstance(app)

    fun upsertActivity(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO){
            db.activityDao.upsertActivity(activity)
        }
    }

    fun deleteActivity(activity: Activity) {
        viewModelScope.launch(Dispatchers.IO){
            db.activityDao.deleteActivity(activity)
        }
    }

    fun getActivities() = db.activityDao.getAllActivities()
}