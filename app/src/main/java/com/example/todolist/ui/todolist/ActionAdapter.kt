package com.example.todolist.ui.todolist

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.Action
import com.example.todolist.databinding.ItemActionBinding

class ActionAdapter(viewModel: TodolistViewModel, text: TextView, private val intActListen: intActionListener): RecyclerView.Adapter<ActionAdapter.ActionViewHolder>(), View.OnClickListener {

    var count_checked = 0;
    var t = text
    var vm = viewModel

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {

        val action = data[position] // Получение дела из списка данных по позиции
        val context = holder.itemView.context

        //кладём в tag каждого view, на которую будет происходить нажатие, нужное дело
        holder.binding.del.tag = action

        with(holder.binding) {
            
            actionTextView.text = action.act

            actionTextView.setOnClickListener {

                var arrList: ArrayList<CharSequence?> = arrayListOf(actionTextView.text,
                    data[position].deadline, data[position].difficulty,
                    data[position].importance)

                var bundle = bundleOf("actioninfo" to arrList)

                actionTextView.findNavController().navigate(R.id.action_navigation_todolist_to_navigation_actioninfo,
                    bundle)
            }

            myCheckBox.isChecked = data[position].isChecked == true
            if(myCheckBox.isChecked)
                actionTextView.paintFlags = actionTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else
                actionTextView.paintFlags = actionTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

            myCheckBox.setOnClickListener{
                count_checked = intActListen.onActionCountChecked()
                data[position].isChecked = !data[position].isChecked!!
                if(data[position].isChecked == true){
                    count_checked++
                    myCheckBox.isChecked = true
                    actionTextView.paintFlags = actionTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                }
                else {
                    count_checked--
                    myCheckBox.isChecked = false
                    actionTextView.paintFlags = actionTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
                t.text = "Total: $itemCount - Checked : $count_checked"

                //update database
                val updateAction = Action(id = data[position].id,
                    act = data[position].act, isChecked = data[position].isChecked == true,
                    deadline = data[position].deadline, difficulty = data[position].difficulty,
                    importance = data[position].importance)
                vm.update(updateAction)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View) {
        val action: Action = view.tag as Action // Получаем из тэга дело

        when (view.id) {
            R.id.del -> {
                count_checked = intActListen.onActionCountChecked()
                intActListen.onActionRemove(action)
                if(action.isChecked == true)
                    count_checked--
                t.text = "Total: $itemCount - Checked : $count_checked"

                //delete element
                vm.delete(action)
            }
        }
    }
}

interface intActionListener {
    fun onActionRemove(action: Action)
    fun onActionCountChecked() : Int
}