package dev.asid.activitytracker

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.Writer

class JSONListSerializer (
    private val filename:String, private val context: Context){
    @Throws(IOException::class)
    fun save(WorkoutList:List<Workout>) {
        val jArray = JSONArray()
        for (workout in WorkoutList) {
            jArray.put(workout.convertToJSON())
        }
        var writer: Writer? = null
        try {
            val out = context.openFileOutput(filename, Context.MODE_PRIVATE)
            writer = OutputStreamWriter(out)
            writer.write(jArray.toString())
        } finally {
            writer?.close()
        }
    }
    fun load(): ArrayList<Workout> {
        val workoutList = ArrayList<Workout>()
        var reader: BufferedReader? = null

        try {
            val inFile = context.openFileInput(filename)
            reader = BufferedReader(InputStreamReader(inFile))
            val jsonString = StringBuilder()

            for (line in reader.readLine()) {
                jsonString.append(line)
            }
            val jArray = JSONTokener(jsonString.toString()).nextValue() as JSONArray

            for (i in 0 until jArray.length()) {
                workoutList.add(Workout(jArray.getJSONObject(i)))
            }
        } catch (e: FileNotFoundException) {
            Log.i("Error", "JSON File not found, data not loaded")
        } finally {
            reader?.close()
        }

        return workoutList
    }




}