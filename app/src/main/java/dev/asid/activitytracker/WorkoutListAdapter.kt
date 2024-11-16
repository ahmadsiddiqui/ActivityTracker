package dev.asid.activitytracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutListAdapter(
    private val mainActivity: WorkoutListActivity,
    private val workoutList: List<Workout>)
    : RecyclerView.Adapter<WorkoutListAdapter.WorkoutListViewHolder>() {
        inner class WorkoutListViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
            internal var date = view.findViewById<TextView>(R.id.date)
            override fun onClick(v: View?) {
                mainActivity.showWorkout(adapterPosition)

            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutListViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.workout_list_item, parent, false)
        return WorkoutListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return workoutList.size ?: -1
    }

    override fun onBindViewHolder(holder: WorkoutListViewHolder, position: Int) {
        val workout = workoutList[position]
        holder.date.text = workout.date

    }


}