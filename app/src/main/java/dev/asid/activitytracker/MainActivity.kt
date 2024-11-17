package dev.asid.activitytracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private var mSerializer: JSONSerializer? = null
    private var workout: ArrayList<Exercise> = ArrayList()
    private var recycler: RecyclerView? = null
    private var adapter: ExerciseAdapter? = null
    private var extraString:ArrayList<String> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        loadWorkoutFromJSON()

        val createBtn = findViewById<Button>(R.id.createActivity)
        createBtn.setOnClickListener {
            DialogNewExercise().show(supportFragmentManager, "dialog")
        }

        recycler = findViewById(R.id.recyclerView)
        adapter = ExerciseAdapter(this, workout)
        recycler!!.layoutManager = LinearLayoutManager(applicationContext)
        recycler!!.itemAnimator = DefaultItemAnimator()
        recycler!!.adapter = adapter


        adapter!!.notifyDataSetChanged()
        Log.i("Adapter", "notify data set changed")
        recycler!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        val btnGo : Button = findViewById(R.id.startWorkout)
        buildExtraString()

        btnGo.setOnClickListener {
            val intent = Intent(this, workoutActivity::class.java)
            intent.putExtra("workout", extraString)
            startActivity(intent)
        }

    }

    private fun buildExtraString() {
        extraString.clear()
        for (exercise in workout) {
            //Toast.makeText(this, exercise.convertToJSON().toString(), Toast.LENGTH_SHORT).show()
            extraString.add(exercise.convertToJSON().toString())
        }
    }

    private fun loadWorkoutFromJSON() {

        mSerializer = JSONSerializer("ActivityTracker.json", applicationContext)
        try {
            workout = mSerializer!!.load()
        } catch (e: Exception) {
            Log.e("Error loading workout", "", e)
        }

    }

    override fun onPause() {
        super.onPause()
        saveWorkout(workout)
        workoutToLog()
    }

    override fun onResume() {
        super.onResume()
        //loadWorkoutFromJSON()
        recycler!!.adapter = adapter
        adapter!!.notifyDataSetChanged()
        Log.i("Adapter", "notify data set changed")
        workoutToLog()
    }

    private fun saveWorkout(workout: ArrayList<Exercise>) {
        try{
            mSerializer!!.save(workout)
        } catch (e:Exception) {
            Log.e("Error saving workout","",e)
        }
    }

    fun addExercise(exercise: Exercise) {
        //Toast.makeText(this, exercise.convertToJSON().toString(), Toast.LENGTH_SHORT).show()
        workout.add(exercise)
        buildExtraString()
        adapter!!.notifyItemInserted(workout.size - 1)
        saveWorkout(workout)
        Log.i("Adapter", "notify data set changed")
        workoutToLog()

    }

    fun showExercise(adapterPosition: Int) {
        val dialog = DialogShowExercise()
        dialog.setExercise(workout!![adapterPosition])
        dialog.show(supportFragmentManager, "")

    }
    fun workoutToLog(){
        for (exercise in workout!!){
            Log.i("Exercise", exercise.title.toString())
        }
    }

    fun deleteExercise(exercise: Exercise) {
        val pos = workout!!.indexOf(exercise)
        workout!!.removeAt(pos)
        adapter!!.notifyItemRemoved(pos)
        saveWorkout(workout)
        buildExtraString()
        Log.i("Adapter", "notify data set changed")
        workoutToLog()
    }

    fun updateExercise(exercise: Exercise, newExercise: Exercise) {
        val pos = workout!!.indexOf(exercise)
        workout!![pos] = newExercise
        adapter!!.notifyItemChanged(pos)
        saveWorkout(workout)
        buildExtraString()
    }
}