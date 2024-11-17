package dev.asid.activitytracker

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.Date

class Workout {
    var workoutArray: ArrayList<Exercise> = ArrayList<Exercise>()
    var date: String

    private val JSON_DATE = "date"
    private val JSON_WORKOUT = "workout"
    //private val gson: Gson = GsonBuilder().create()
    @Throws(JSONException::class)
    constructor(json: JSONObject) {
        date = json.getString(JSON_DATE)
        workoutArray = ArrayList<Exercise>()
        val workoutArrayJSON = json.getJSONArray(JSON_WORKOUT)
        for (i in 0 until workoutArrayJSON.length()) {
            workoutArray.add(Gson().fromJson(workoutArrayJSON.getString(i), Exercise::class.java))
        }

    }
    constructor() {
        date = Date().toString()
    }
    @Throws(JSONException::class)
    fun convertToJSON(): JSONObject {
        val workoutArray: JSONArray = JSONArray()
        for (exercise in (this.workoutArray)){
            workoutArray.put(Gson().toJson(exercise))
        }

        val json = JSONObject()
        json.put(JSON_DATE, date)
        json.put(JSON_WORKOUT, workoutArray)
        return json
    }
    fun setWorkout(workout: ArrayList<Exercise>) {
        workoutArray = workout
    }
    fun setDate() {
        this.date = Date().toString()
    }
}