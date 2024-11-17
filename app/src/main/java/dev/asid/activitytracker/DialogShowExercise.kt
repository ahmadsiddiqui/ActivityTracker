package dev.asid.activitytracker

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment

class DialogShowExercise : DialogFragment() {
    private var exercise: Exercise? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_show_exercise, null)
        builder.setView(dialogView)

        val exerciseTitle = dialogView.findViewById<TextView>(R.id.exerciseTitle)
        val deleteButton = dialogView.findViewById<Button>(R.id.deleteButton)
        val weights = dialogView.findViewById<ConstraintLayout>(R.id.weights)
        val distance = dialogView.findViewById<TextView>(R.id.distance_input2)
        val distance_label = dialogView.findViewById<TextView>(R.id.distance_label2)
        val reps = dialogView.findViewById<TextView>(R.id.reps_input)
        val weight = dialogView.findViewById<TextView>(R.id.weight_input)
        val sets = dialogView.findViewById<TextView>(R.id.sets_input)
        val btnOK = dialogView.findViewById<TextView>(R.id.btnOK)
        exerciseTitle.text = exercise!!.title

        if (exercise!!.type == Exercise.EXERCISETYPE.WEIGHTS) {
            weights.visibility = ConstraintLayout.VISIBLE
            distance.visibility = TextView.INVISIBLE
            distance_label.visibility = TextView.INVISIBLE
        }
        else {
            weights.visibility = ConstraintLayout.INVISIBLE
            distance.visibility = TextView.VISIBLE
            distance_label.visibility = TextView.VISIBLE
        }

        reps.text = exercise?.reps.toString()
        weight.text = exercise?.weight.toString()
        sets.text = exercise?.sets.toString()
        distance.text = exercise?.distance.toString()


        exercise?.reps.let { reps.text = it.toString() }
        exercise?.weight.let { weight.text = it.toString() }
        exercise?.sets.let { sets.text = it.toString() }
        exercise?.distance.let { distance.text = it.toString() }


        deleteButton.setOnClickListener {
            val mainActivity = activity as MainActivity
            mainActivity!!.deleteExercise(exercise!!)
            dismiss()
        }
        btnOK.setOnClickListener {
            var newExercise = Exercise()
            newExercise.reps = reps.text.toString().toIntOrNull() ?: 0
            newExercise.weight = weight.text.toString().toIntOrNull() ?: 0
            newExercise.sets = sets.text.toString().toIntOrNull() ?: 0
            newExercise.distance = distance.text.toString().toIntOrNull() ?: 0
            newExercise.title = exerciseTitle.text.toString()
            if (exercise!!.type == Exercise.EXERCISETYPE.WEIGHTS) {
                newExercise.type = Exercise.EXERCISETYPE.WEIGHTS
            }
            else {
                newExercise.type = Exercise.EXERCISETYPE.CARDIO
            }
            val mainActivity = activity as MainActivity
            mainActivity!!.updateExercise(exercise!!, newExercise)

            dismiss()
        }
        return builder.create()
    }
    fun setExercise(exercise: Exercise) {
        this.exercise = exercise
    }


}