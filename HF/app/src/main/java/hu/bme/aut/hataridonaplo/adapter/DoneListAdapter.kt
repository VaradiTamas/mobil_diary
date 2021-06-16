package hu.bme.aut.hataridonaplo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.hataridonaplo.R
import hu.bme.aut.hataridonaplo.adapter.DoneListAdapater.DoneViewHolder
import hu.bme.aut.hataridonaplo.data.exercise.Exercise

class DoneListAdapater(private val listener: DoneItemClickListener) : RecyclerView.Adapter<DoneViewHolder>(){

    private val dones  = mutableListOf<Exercise>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoneViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.done_row, parent, false)
        //return DoneListAdapater.DoneViewHolder(itemView)
        return DoneViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dones.size
    }

    override fun onBindViewHolder(holder: DoneViewHolder, position: Int) {
        holder.initialize(dones.get(position), listener)
    }

    fun addDone(done: Exercise) {
        dones.add(done)
        notifyItemInserted(dones.size - 1)
    }

    fun removeDone(exercise: Exercise){
        dones.remove(exercise)
    }

    fun update(done: List<Exercise>) {
        dones.clear()
        dones.addAll(done)
        notifyDataSetChanged()
    }

    interface DoneItemClickListener{
        fun onDoneClick(item: Exercise, position: Int)
        fun deleteDone(item: Exercise)
    }

    inner class DoneViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val doneText: TextView
        val btnDelete: ImageButton

        init{
            doneText = itemView.findViewById(R.id.tvDoneText)
            btnDelete = itemView.findViewById(R.id.btnDeleteDone)
        }

        fun initialize(done: Exercise, action: DoneListAdapater.DoneItemClickListener) {
            doneText.text = done.task
            itemView.setOnClickListener{
                action.onDoneClick(done, adapterPosition)
            }
            btnDelete.setOnClickListener(){
                action.deleteDone(done)
            }
        }
    }
}