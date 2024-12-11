package dev.asid.activitytracker

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView

class ExerciseAdapter(
    private val mainActivity: MainActivity,
    private val workout: List<Exercise>)
    : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    inner class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        internal var title = view.findViewById<TextView>(R.id.exerciseTitle)
        internal var type = view.findViewById<TextView>(R.id.exerciseType)
        internal var sets = view.findViewById<TextView>(R.id.sets)
        internal var reps = view.findViewById<TextView>(R.id.reps)
        internal var weightOrDistance = view.findViewById<TextView>(R.id.weightOrDistance)

        init {
            view.isClickable = true
            view.setOnClickListener(this)
        }
        override fun onClick(view: View) {
            mainActivity.showExercise(adapterPosition)
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ExerciseViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = workout[position]
        holder.title.text = exercise.title
        holder.type.text = exercise.type.toString()
        if (exercise.sets > 0) {
            holder.sets.text = buildString {
                append(exercise.sets.toString())
                append(" sets")
            }
        }

        if (exercise.reps > 0) {
            holder.reps.text = buildString {
                append(exercise.reps.toString())
                append(" reps")
            }
        }
        when (exercise.type) {
            Exercise.EXERCISETYPE.WEIGHTS -> {
                holder.weightOrDistance.text = buildString {
                    append(exercise.weight.toString())
                    append(mainActivity.getString(R.string.weight_unit))
                }
            }
            Exercise.EXERCISETYPE.CARDIO -> {
                holder.weightOrDistance.text = buildString {
                    append(exercise.distance.toString())
                    append(mainActivity.getString(R.string.distance_unit))
                }
            }
            else -> {
                Log.i("Error", "Exercise type not recognized in OnBindViewHolder by ExerciseAdapter")
            }
        }

    }

    override fun getItemCount(): Int {
        return workout.size ?: -1

    }
}

