package dev.asid.activitytracker

import org.json.JSONException
import org.json.JSONObject

class Excercise {
    enum EXCERCISETYPE {WEIGHTS, CARDIO}
    var title:String? = null
    var type:EXCERCISETYPE? = null

    private val JSON_TITLE = "title"
    private val JSON_TYPE = "type"
    @Throws(JSONException::class)
    constructor(json: JSONObject) {
        title = json.getString(JSON_TITLE)
        type = EXCERCISETYPE.valueOf(json.getString(JSON_TYPE))
    }
    constructor() {

    }
    @Throws(JSONException::class)
    fun convertToJSON(): JSONObject {
        val json = JSONObject()
        json.put(JSON_TITLE, title)
        json.put(JSON_TYPE, type.toString())
        return json
    }


}