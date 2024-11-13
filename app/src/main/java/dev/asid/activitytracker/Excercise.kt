package dev.asid.activitytracker

import org.json.JSONException
import org.json.JSONObject

class Exercise {
    enum class EXERCISETYPE {WEIGHTS, CARDIO}

    var distance: Int? = null
    var title:String? = null
    var reps:Int? = null
    var sets:Int? = null
    var weight:Int? = null
    var type:EXERCISETYPE? = null

    private val JSON_TITLE = "title"
    private val JSON_TYPE = "type"
    private val JSON_REPS = "reps"
    private val JSON_SETS = "sets"
    private val JSON_WEIGHT = "weight"
    private val JSON_DISTANCE = "distance"

    @Throws(JSONException::class)
    constructor(json: JSONObject) {
        title = json.getString(JSON_TITLE)
        type = EXERCISETYPE.valueOf(json.getString(JSON_TYPE))
        reps = json.getInt(JSON_REPS)
        sets = json.getInt(JSON_SETS)
        weight = json.getInt(JSON_WEIGHT)
        distance = json.getInt(JSON_DISTANCE)
    }
    constructor() {

    }
    @Throws(JSONException::class)
    fun convertToJSON(): JSONObject {
        val json = JSONObject()
        json.put(JSON_TITLE, title)
        json.put(JSON_TYPE, type.toString())
        json.put(JSON_REPS, reps)
        json.put(JSON_SETS, sets)
        json.put(JSON_WEIGHT, weight)
        json.put(JSON_DISTANCE, distance)
        return json
    }


}