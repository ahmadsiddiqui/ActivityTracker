package dev.asid.activitytracker

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
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
        val workoutScrollView = dialogView.findViewById<ScrollView>(R.id.workoutScrollView)

        dateTextView.text = workout!!.date

        for (exercise in workout!!.workoutArray){
            val exerciseTextView = TextView(context)
            exerciseTextView.text = StringBuilder().append(exercise.title.toString())
                .append(" ").append(exercise.reps.toString()).append(" reps")
                .append(" ").append(exercise.sets.toString()).append(" sets")
                .append(" ").append(exercise.weight.toString()).append(" lbs")
            workoutScrollView.addView(exerciseTextView)
        }
        return builder.create()
    }
    fun setWorkout(workout: Workout){
        this.workout = workout
    }


}