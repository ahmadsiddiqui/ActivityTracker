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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var a:Exercise = Exercise()
        var b:Exercise = Exercise()
        var c:Exercise = Exercise()
        var d:Exercise = Exercise()
        var e:Exercise = Exercise()
        a.title = "Test"
        a.reps = 1
        a.sets = 1
        a.weight = 1
        a.distance = 1
        a.type = Exercise.EXERCISETYPE.WEIGHTS
        b.title = "Test2"
        b.reps = 2
        b.sets = 2
        b.weight = 2
        b.distance = 2
        b.type = Exercise.EXERCISETYPE.WEIGHTS
        c.title = "Test3"
        c.reps = 3
        c.sets = 3
        c.weight = 3
        c.distance = 3
        c.type = Exercise.EXERCISETYPE.WEIGHTS
        d.title = "Test4"
        d.reps = 4
        d.sets = 4
        d.weight = 4
        d.distance = 4
        d.type = Exercise.EXERCISETYPE.WEIGHTS
        e.title = "Test5"
        e.reps = 5
        e.sets = 5
        e.weight = 5
        e.distance = 5
        e.type = Exercise.EXERCISETYPE.WEIGHTS
        workout.add(a)
        workout.add(b)
        workout.add(c)
        workout.add(d)
        workout.add(e)
        saveWorkout(workout)
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

        //loadWorkoutFromJSON()
        adapter!!.notifyDataSetChanged()
        Log.i("Adapter", "notify data set changed")
        recycler!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        var btnGo : Button = findViewById(R.id.startWorkout)
        var extraString:ArrayList<String> = ArrayList()
        for (exercise in workout){
            //Toast.makeText(this, exercise.convertToJSON().toString(), Toast.LENGTH_SHORT).show()
            extraString.add(exercise.convertToJSON().toString())
        }

        btnGo.setOnClickListener {
            val intent = Intent(this, workoutActivity::class.java)
            intent.putExtra("workout", extraString)
            startActivity(intent)
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
        Log.i("Adapter", "notify data set changed")
        workoutToLog()
    }
}