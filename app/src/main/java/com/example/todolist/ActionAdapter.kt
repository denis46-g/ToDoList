package com.example.todolist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemActionBinding

class ActionAdapter(main_act: MainActivity): RecyclerView.Adapter<ActionAdapter.ActionViewHolder>() {

    var count_checked = 0;
    var mm = main_act

     var data: List<Action> = emptyList()
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    class ActionViewHolder(val binding: ItemActionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(action: ViewGroup, viewType: Int): ActionViewHolder {
        val inflater = LayoutInflater.from(action.context)
        val binding = ItemActionBinding.inflate(inflater, action, false)

        return ActionViewHolder(binding)
    }
    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        val action = data[position] // Получение дела из списка данных по позиции
        val context = holder.itemView.context

        with(holder.binding) {
            
            actionTextView.text = action.text

            myCheckBox.setOnClickListener{
                data[position].isChecked = !data[position].isChecked
                if(data[position].isChecked){
                    count_checked++
                    actionTextView.paintFlags = actionTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                else {
                    count_checked--
                    actionTextView.paintFlags = actionTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                mm.title = "To do list               Total: $itemCount - Checked : $count_checked"
            }
        }
    }
}