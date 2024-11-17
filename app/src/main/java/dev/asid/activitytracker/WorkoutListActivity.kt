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
import com.google.gson.Gson

class WorkoutListActivity : AppCompatActivity() {
    private var mSerializer: JSONListSerializer? = null
    private var workoutList: ArrayList<Workout> = ArrayList()
    private var recycler: RecyclerView? = null
    private var adapter: WorkoutListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_workout_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //loadWorkoutListFromJSON()

        var workout:Workout = Workout()
        val extraString = intent.getStringExtra("workout")
        workout = Gson().fromJson(extraString, Workout::class.java)

        recycler = findViewById(R.id.workoutListRecyclerView)
        var goToMainBtn = findViewById<Button>(R.id.goToMain)
        goToMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        adapter = WorkoutListAdapter(this, workoutList)
        recycler!!.layoutManager = LinearLayoutManager(applicationContext)
        recycler!!.itemAnimator = DefaultItemAnimator()
        recycler!!.adapter = adapter
        recycler!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        loadWorkoutListFromJSON()
        addWorkout(workout)
        saveWorkoutList(workoutList)

        adapter!!.notifyDataSetChanged()

    }

    private fun loadWorkoutListFromJSON() {
        mSerializer = JSONListSerializer("WorkoutList.json", applicationContext)
        try {
            workoutList = mSerializer!!.load()
        } catch (e: Exception) {
            Log.e("Error loading workoutList", "", e)
        }
    }
    fun addWorkout(workout: Workout) {
        workoutList.add(workout)
        adapter!!.notifyDataSetChanged()
        saveWorkoutList(workoutList)
        Log.i("Adapter", "notify data set changed")

    }

    fun saveWorkoutList(workoutList: ArrayList<Workout>) {
        try {
            mSerializer!!.save(workoutList)
        } catch (e: Exception) {
            Log.e("Error saving workoutList", "", e)
        }
    }

    fun showWorkout(adapterPosition: Int) {
        val dialog = DialogShowWorkout()
        dialog.setWorkout(workoutList!![adapterPosition])
        dialog.show(supportFragmentManager, "")
    }
}