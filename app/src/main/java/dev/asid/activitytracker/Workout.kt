package dev.asid.activitytracker

import org.json.JSONException
import org.json.JSONObject
import java.util.Date

class Workout {
    var workoutArray: ArrayList<Exercise> = ArrayList<Exercise>()
    var date: String

    private val JSON_DATE = "date"
    private val JSON_WORKOUT = "workout"

    @Throws(JSONException::class)
    constructor(json: JSONObject) {
        date = json.getString(JSON_DATE)
        val workoutArray = json.getJSONArray(JSON_WORKOUT)

    }
    constructor() {
        date = Date().toString()
    }
    @Throws(JSONException::class)
    fun convertToJSON(): JSONObject {
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