package dev.asid.activitytracker

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.DialogFragment

class DialogShowWorkout : DialogFragment(){
    private var workout:Workout? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_show_workout, null)
        builder.setView(dialogView)
        val dateTextView = dialogView.findViewById<TextView>(R.id.dateTextView)
        val workoutScrollView = dialogView.findViewById<LinearLayout>(R.id.workoutScrollView)
        val deleteBtn = dialogView.findViewById<TextView>(R.id.deleteBtn)
        deleteBtn.setOnClickListener {
            deleteWorkout()
        }

        dateTextView.text = workout!!.date

        for (exercise in workout!!.workoutArray){
            val exerciseTextView = TextView(context)
            exerciseTextView.textSize = 16f
            exerciseTextView.setPadding(5, 5, 5, 5)
            exerciseTextView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            exerciseTextView.text = StringBuilder().append(exercise.title.toString())
            if(exercise.weight > 0){
                exerciseTextView.append(" | " + exercise.weight.toString() + " lbs")
            }
            if(exercise.reps > 0){
                exerciseTextView.append(" | " + exercise.reps.toString() + " reps")
            }
            if(exercise.sets > 0){
                exerciseTextView.append(" | " + exercise.sets.toString() + " sets")
            }
            if(exercise.distance > 0){
                exerciseTextView.append(" | " + exercise.distance.toString() + " miles")
            }
            workoutScrollView.addView(exerciseTextView)
        }
        return builder.create()
    }
    fun deleteWorkout(){
        val callingActivity = activity as WorkoutListActivity?
        val alertDialog = AlertDialog.Builder(requireActivity())
            .setTitle("Delete Workout")
            .setMessage("Are you sure you want to delete this workout? \n\nTHIS ACTION CANNOT BE UNDONE.")
            .setPositiveButton("Yes") { _, _ ->
                callingActivity!!.deleteWorkout(workout!!)
                dismiss()
            }
            .setNegativeButton("No", null)
            .show()


    }
    fun setWorkout(workout: Workout){
        this.workout = workout
    }


}