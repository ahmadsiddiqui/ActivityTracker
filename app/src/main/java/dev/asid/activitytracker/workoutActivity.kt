package dev.asid.activitytracker


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson

class workoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("Workout Activity", "Created")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_workout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val mainLayout = findViewById<LinearLayout>(R.id.linearLayout)
        var workout: ArrayList<Exercise> = ArrayList()
        val extraString = intent.getStringArrayListExtra("workout")
        for (string in extraString!!){
            var exercise = Gson().fromJson(string, Exercise::class.java)
            workout.add(exercise)
        }

        var currentExerciseTitle = findViewById<TextView>(R.id.currentExerciseTitle)
        var targetReps = findViewById<TextView>(R.id.targetReps)
        var targetSets = findViewById<TextView>(R.id.targetSets)
        var targetWeight = findViewById<TextView>(R.id.targetWeight)

        var actualReps = findViewById<TextView>(R.id.actualReps)
        var actualSets = findViewById<TextView>(R.id.actualSets)
        var actualWeight = findViewById<TextView>(R.id.actualWeight)

        currentExerciseTitle.visibility = View.INVISIBLE
        targetReps.visibility = View.INVISIBLE
        targetSets.visibility = View.INVISIBLE
        targetWeight.visibility = View.INVISIBLE


        var completedExercises: ArrayList<Exercise> = ArrayList()

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener {
            currentExerciseTitle.visibility = View.VISIBLE
            targetReps.visibility = View.VISIBLE
            targetSets.visibility = View.VISIBLE
            targetWeight.visibility = View.VISIBLE


            if (completedExercises.size < workout.size) {
                var currentExercise = workout[completedExercises.size]
                currentExerciseTitle.text = currentExercise.title
                targetReps.text = currentExercise.reps.toString()
                targetSets.text = currentExercise.sets.toString()
                targetWeight.text = currentExercise.weight.toString()
                actualReps.text = targetReps.text
                actualSets.text = targetSets.text
                actualWeight.text = targetWeight.text

                currentExercise.reps = actualReps.text.toString().toInt()
                currentExercise.sets = actualSets.text.toString().toInt()
                currentExercise.weight = actualWeight.text.toString().toInt()

                completedExercises.add(currentExercise)
            }
            else{
                btnNext.text = "FINISHED"
                var workout: Workout = Workout()
                workout.setWorkout(completedExercises)
                workout.setDate()


                val extraString:String = Gson().toJson(workout).toString()

                val intent = Intent(this, WorkoutListActivity::class.java)
                intent.putExtra("workout", extraString)
                startActivity(intent)
            }

            }

    }
}