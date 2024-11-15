package dev.asid.activitytracker

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
        exerciseTitle.text = exercise!!.title

        deleteButton.setOnClickListener {
            val mainActivity = activity as MainActivity
            mainActivity!!.deleteExercise(exercise!!)
            dismiss()
        }
        return builder.create()
    }
    fun setExercise(exercise: Exercise) {
        this.exercise = exercise
    }


}