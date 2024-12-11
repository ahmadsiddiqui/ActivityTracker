package dev.asid.activitytracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
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
        loadWorkoutListFromJSON()
        recycler = findViewById(R.id.workoutListRecyclerView)
        var goToMainBtn: Button? = null
        goToMainBtn = findViewById(R.id.goToMain)
        goToMainBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent = Intent(this@WorkoutListActivity, MainActivity::class.java)
                startActivity(intent)
            }

        })
    }


    override fun onStart() {
        super.onStart()
        adapter = WorkoutListAdapter(this, workoutList)
        recycler!!.layoutManager = LinearLayoutManager(applicationContext)
        recycler!!.itemAnimator = DefaultItemAnimator()
        recycler!!.adapter = adapter
        recycler!!.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        var workout: Workout
        val extraString = intent.getStringExtra("workout")
        if(extraString != null){
            workout = Gson().fromJson(extraString, Workout::class.java)
            loadWorkoutListFromJSON()
            addWorkout(workout)
            adapter!!.notifyDataSetChanged()
        }
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
        saveWorkoutList(workoutList)

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
    fun deleteWorkout(workout: Workout) {
        val pos = workoutList!!.indexOf(workout)
        workoutList!!.removeAt(pos)
        adapter!!.notifyItemRemoved(pos)
        saveWorkoutList(workoutList)

    }


}