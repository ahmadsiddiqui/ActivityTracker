package dev.asid.activitytracker

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.Writer

class JSONSerializer(
    private val filename:String, private val context: Context
    ) {
    @Throws(IOException::class)
    fun save(workout:List<Exercise>){
        val jArray = JSONArray()
        for (ex in workout){
            jArray.put(ex.convertToJSON())
        }

        var writer: Writer? = null
        try{
            val out = context.openFileOutput(filename, Context.MODE_PRIVATE)
            writer = OutputStreamWriter(out)
            writer.write(jArray.toString())
        }finally {
            writer?.close()
        }
    }

    @Throws(IOException::class, JSONException::class)
    fun load():ArrayList<Exercise>{
        val workout = ArrayList<Exercise>()
        var reader: BufferedReader? = null

        try{
            val inFile = context.openFileInput(filename)
            reader = BufferedReader(InputStreamReader(inFile))
            val jsonString = StringBuilder()

            for (line in reader.readLine()){
                jsonString.append(line)
            }
            val jArray = JSONTokener(jsonString.toString()).nextValue() as JSONArray

            for (i in 0 until jArray.length()){
                workout.add(Exercise(jArray.getJSONObject(i)))
            }
        }

        catch (e: FileNotFoundException) {
            Log.i("Error", "JSON File not found, data not loaded")
        }

        finally {
            reader?.close()
        }

        return workout
    }
}