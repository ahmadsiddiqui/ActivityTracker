package dev.asid.activitytracker

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var mSerializer: JSONSerializer? = null
    private var workout: ArrayList<Exercise>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val createBtn = findViewById<Button>(R.id.createActivity)
        createBtn.setOnClickListener {
            DialogNewExercise().show(supportFragmentManager, "dialog")
        }
        mSerializer = JSONSerializer("ActivityTracker.json",applicationContext)
        try{
            workout = mSerializer!!.load()
        } catch (e:Exception) {
            workout = ArrayList()
            Log.e("Error loading workout","",e)

        }
    }
    fun createExercise(exercise: Exercise) {
        workout!!.add(exercise)
        saveWorkout()
    }

    private fun saveWorkout() {
        try{
            mSerializer!!.save(this.workout!!)
        } catch (e:Exception) {
            Log.e("Error saving workout","",e)
        }
    }
}