package hu.bme.aut.hataridonaplo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.hataridonaplo.R
import hu.bme.aut.hataridonaplo.data.exercise.Exercise


class ToDoListAdapater(private val listener: ToDoItemClickListener) : RecyclerView.Adapter<ToDoListAdapater.ToDoViewHolder>(){

    private val toDos  = mutableListOf<Exercise>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.to_do_row, parent, false)

        return ToDoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return toDos.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        holder.initialize(toDos.get(position), listener)
    }

    fun addToDo(exercise: Exercise) {
        toDos.add(exercise)
        notifyItemInserted(toDos.size - 1)
    }

    fun removeToDo(exercise: Exercise){
        toDos.remove(exercise)
    }

    fun update(exercise: List<Exercise>) {
        toDos.clear()
        toDos.addAll(exercise)
        notifyDataSetChanged()
    }

    interface ToDoItemClickListener{
        fun onToDoClick(item: Exercise, position: Int)
        fun deleteToDo(item: Exercise)
    }

    inner class ToDoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val toDoText: TextView
        val btnDelete : ImageButton

        init{
            toDoText = itemView.findViewById(R.id.tvToDoText)
            btnDelete = itemView.findViewById(R.id.btnDeleteToDo)
        }

        fun initialize(todo: Exercise, action: ToDoItemClickListener) {
            toDoText.text = todo.task
            itemView.setOnClickListener{
                action.onToDoClick(todo, adapterPosition)
            }
            btnDelete.setOnClickListener(){
                action.deleteToDo(todo)
            }
        }
    }
}