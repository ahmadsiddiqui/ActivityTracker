package dev.asid.activitytracker

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.radiobutton.MaterialRadioButton
import androidx.fragment.app.DialogFragment

import dev.asid.activitytracker.Exercise.EXERCISETYPE

class DialogNewExercise: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.new_exercise_title)
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_new_exercise, null)

        builder.setView(dialogView)

        val weightLayout = dialogView.findViewById<ConstraintLayout>(R.id.weights)
        val title = dialogView.findViewById<TextView>(R.id.title_input)
        val weight = dialogView.findViewById<TextView>(R.id.weight_input)
        val reps = dialogView.findViewById<TextView>(R.id.reps_input)
        val sets = dialogView.findViewById<TextView>(R.id.sets_input)
        val distance_label = dialogView.findViewById<TextView>(R.id.distance_label)
        val distance = dialogView.findViewById<TextView>(R.id.distance_input)
        var radioGroup : RadioGroup = dialogView.findViewById(R.id.radioGroup)
        val weightsRadio = dialogView.findViewById<MaterialRadioButton>(R.id.weights_radio)
        val cardioRadio = dialogView.findViewById<MaterialRadioButton>(R.id.cardio_radio)
        val type: EXERCISETYPE? = null
        val incompleteWarning = dialogView.findViewById<TextView>(R.id.incompleteWarning)
        incompleteWarning.visibility = View.INVISIBLE


        val btnOK = dialogView.findViewById<TextView>(R.id.createActivityButton)

        weightsRadio.setOnClickListener {
            weightLayout.visibility = View.VISIBLE
            distance_label.visibility = View.INVISIBLE
            distance.visibility = View.INVISIBLE
        }
        cardioRadio.setOnClickListener {
            weightLayout.visibility = View.INVISIBLE
            distance_label.visibility = View.VISIBLE
            distance.visibility = View.VISIBLE

        }

        btnOK.setOnClickListener {
            val exercise = Exercise()
            exercise.title = title.text!!.toString()


            exercise.weight = weight.text!!.toString().toIntOrNull() ?: 0
            exercise.reps = reps.text!!.toString().toIntOrNull() ?: 0
            exercise.sets = sets.text!!.toString().toIntOrNull() ?: 0
            exercise.distance = distance.text!!.toString().toIntOrNull() ?: 0
            val intSelectedButton: Int = radioGroup.checkedRadioButtonId
            val radioButton = dialogView.findViewById<MaterialRadioButton>(intSelectedButton)
            if (radioButton == weightsRadio){
                exercise.type = EXERCISETYPE.WEIGHTS
                //Toast.makeText(requireContext(), "Weights Selected", Toast.LENGTH_SHORT).show()
                //dismiss()
            } else if (radioButton == cardioRadio) {
                exercise.type = EXERCISETYPE.CARDIO
                exercise.weight = -1
                exercise.reps = -1
                exercise.sets = -1
                //Toast.makeText(requireContext(), "Cardio Selected", Toast.LENGTH_SHORT).show()
                //dismiss()
            }
            val callingActivity = activity as MainActivity?
            if(exercise.title != ""
                && exercise.type != null
                && (exercise.weight != 0 || exercise.distance != 0)
                && exercise.reps != 0
                && exercise.sets != 0){
                callingActivity!!.addExercise(exercise)
                dismiss()
            } else {
                incompleteWarning.visibility = View.VISIBLE
            }
        }
        return builder.create()


    }

}