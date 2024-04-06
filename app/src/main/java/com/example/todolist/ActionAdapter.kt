package com.example.todolist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemActionBinding

class ActionAdapter(main_act: MainActivity, private val intActListen: intActionListener): RecyclerView.Adapter<ActionAdapter.ActionViewHolder>(), View.OnClickListener {

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

        // инициализация слушателей при нажатии
        binding.del.setOnClickListener(this)

        return ActionViewHolder(binding)
    }
    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {

        val action = data[position] // Получение дела из списка данных по позиции
        val context = holder.itemView.context

        //кладём в tag каждого view, на которую будет происходить нажатие, нужное дело
        holder.binding.del.tag = action

        with(holder.binding) {
            
            actionTextView.text = action.text
            myCheckBox.isChecked = data[position].isChecked
            if(myCheckBox.isChecked)
                actionTextView.paintFlags = actionTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else
                actionTextView.paintFlags = actionTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            myCheckBox.setOnClickListener{
                data[position].isChecked = !data[position].isChecked
                if(data[position].isChecked){
                    count_checked++
                    myCheckBox.isChecked = true
                    actionTextView.paintFlags = actionTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                else {
                    count_checked--
                    myCheckBox.isChecked = false
                    actionTextView.paintFlags = actionTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                mm.title = "To do list              Total: $itemCount - Checked : $count_checked"
            }
        }
    }

    override fun onClick(view: View) {
        val action: Action = view.tag as Action // Получаем из тэга дело

        when (view.id) {
            R.id.del -> {
                intActListen.onActionRemove(action)
                if(action.isChecked)
                    count_checked--
                mm.title = "To do list              Total: $itemCount - Checked : $count_checked"
            }
        }
    }
}

interface intActionListener {
    //fun onActionGetId(action: Action)
    fun onActionRemove(action: Action)
}