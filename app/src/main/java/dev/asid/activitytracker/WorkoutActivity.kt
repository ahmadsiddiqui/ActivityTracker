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

class WorkoutActivity : AppCompatActivity() {


    var workout: ArrayList<Exercise> = ArrayList()
    val completedExercises: ArrayList<Exercise> = ArrayList()
    var currentExercise:Exercise = Exercise()

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

        val extraString = intent.getStringArrayListExtra("workout")

        for (string in extraString!!) {
            workout.add(Gson().fromJson(string, Exercise::class.java))
        }

        var currentExerciseTitle = findViewById<TextView>(R.id.currentExerciseTitle)
        var targetReps = findViewById<TextView>(R.id.targetReps)
        var targetSets = findViewById<TextView>(R.id.targetSets)
        var targetWeight = findViewById<TextView>(R.id.targetWeight)

        var actualReps = findViewById<TextView>(R.id.actualReps)
        var actualSets = findViewById<TextView>(R.id.actualSets)
        var actualWeight = findViewById<TextView>(R.id.actualWeight)

        var exerciseScrollView = findViewById<LinearLayout>(R.id.exerciseScrollView)
        var completedExerciseScrollView = findViewById<LinearLayout>(R.id.completedExerciseScrollView)

        updateScrollViews(exerciseScrollView, completedExerciseScrollView)

        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnPrev = findViewById<Button>(R.id.btnPrev)

        currentExercise = workout[0]

        currentExerciseTitle.text = currentExercise.title.uppercase()
        targetReps.text = currentExercise.reps.toString()
        targetSets.text = currentExercise.sets.toString()
        targetWeight.text = currentExercise.weight.toString()

        actualReps.text = currentExercise.reps.toString()
        actualSets.text = currentExercise.sets.toString()
        actualWeight.text = currentExercise.weight.toString()

        btnNext.setOnClickListener {

            if (workout.size > 0) {
                currentExercise.sets = actualSets.text!!.toString().toInt()
                currentExercise.reps = actualReps.text!!.toString().toInt()
                currentExercise.weight = actualWeight.text!!.toString().toInt()
                workout.removeAt(0)
                completedExercises.add(currentExercise)

                if (workout.size > 0) {currentExercise = workout[0]}

                currentExerciseTitle.text = currentExercise.title.uppercase()
                targetReps.text = currentExercise.reps.toString()
                targetSets.text = currentExercise.sets.toString()
                targetWeight.text = currentExercise.weight.toString()

                actualReps.text = currentExercise.reps.toString()
                actualSets.text = currentExercise.sets.toString()
                actualWeight.text = currentExercise.weight.toString()

            }
            if (workout.size == 0) {
                btnNext.text = "FINISH"
                btnNext.setOnClickListener {
                    val intent = Intent(this, WorkoutListActivity::class.java)
                    val newWorkout:Workout = Workout()
                    newWorkout.setWorkout(completedExercises)
                    intent.putExtra("workout", Gson().toJson(newWorkout))

                    startActivity(intent)
                }
                //var intent = Intent(this, WorkoutListActivity::class.java)
                //intent.putExtra("workout", Gson().toJson(completedExercises))
                //startActivity(intent)
            }
            updateScrollViews(exerciseScrollView, completedExerciseScrollView)

        }
        btnPrev.setOnClickListener {
            if (completedExercises.size > 0) {
                workout.add(0,completedExercises[completedExercises.size - 1])
                completedExercises.removeAt(completedExercises.size - 1)
                currentExercise = workout[0]
                currentExerciseTitle.text = currentExercise.title.uppercase()
                targetReps.text = currentExercise.reps.toString()
                targetSets.text = currentExercise.sets.toString()
                targetWeight.text = currentExercise.weight.toString()

                actualReps.text = currentExercise.reps.toString()
                actualSets.text = currentExercise.sets.toString()
                actualWeight.text = currentExercise.weight.toString()
            }
            updateScrollViews(exerciseScrollView, completedExerciseScrollView)
        }

    }

    private fun updateScrollViews(
        exerciseScrollView: LinearLayout,
        completedExerciseScrollView: LinearLayout
    ) {
        exerciseScrollView.removeAllViews()
        completedExerciseScrollView.removeAllViews()
        for (exercise in workout) {
            val tv = TextView(this)
            tv.text = exercise.title.uppercase()
            tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
            exerciseScrollView.addView(tv)
        }
        for (exercise in completedExercises) {
            val tv = TextView(this)
            tv.text = exercise.title.uppercase()
            tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
            completedExerciseScrollView.addView(tv)
        }
    }
}