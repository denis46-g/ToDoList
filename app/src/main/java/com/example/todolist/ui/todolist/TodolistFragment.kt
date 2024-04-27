package com.example.todolist.ui.todolist

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.data.Action
import com.example.todolist.databinding.FragmentTodolistBinding
import com.example.todolist.databinding.ItemActionBinding

class TodolistFragment : Fragment() {

private var _binding: FragmentTodolistBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

    private lateinit var todolistViewModel: TodolistViewModel

    private lateinit var adapter: ActionAdapter // Объект Adapter
    private var app = App()
    private val actionService: ActionService = app.actionService

    private val listener: ActionListener = {adapter.data = it}

  @SuppressLint("SetTextI18n")
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

      _binding = FragmentTodolistBinding.inflate(inflater, container, false)
    val root: View = binding.root

      val itembind = ItemActionBinding.inflate(layoutInflater)

      actionService.addListener(listener)

      todolistViewModel = ViewModelProvider(this)[TodolistViewModel::class.java]

      val manager = LinearLayoutManager(requireContext()) // LayoutManager
      adapter = ActionAdapter(todolistViewModel, binding.textStat, object : intActionListener {

          override fun onActionRemove(action: Action) = actionService.removeAction(action)

          override fun onActionCountChecked(): Int = actionService.countChecked()

      }) // Создание объекта

      //добавляем новое дело
      val new_act = arguments?.getStringArrayList("addnewaction")
      if(new_act != null) {
          actionService.actions.add(
              Action(
                  id = null,
                  act = new_act[0], isChecked = false, deadline = new_act[1],
                  difficulty = new_act[2], importance = new_act[3]
              )
          )
          todolistViewModel.insert(
              Action(
                  id = null,
                  act = new_act[0], isChecked = false, deadline = new_act[1],
                  difficulty = new_act[2], importance = new_act[3]
              )
          )
      }


      todolistViewModel.actions.observe(viewLifecycleOwner, Observer { action ->
          actionService.actions = action.toMutableList()
          adapter.data = actionService.actions
          binding.textStat.text = "Total: " + actionService.actions.size + " - Checked : " + actionService.countChecked()
          adapter.count_checked = actionService.countChecked()
      })

      binding.recyclerView.layoutManager = manager // Назначение LayoutManager для RecyclerView
      binding.recyclerView.adapter = adapter // Назначение адаптера для RecyclerView


      // переход с главной страницы на страницу добавления нового дела
      val buttonAddItem = root.findViewById<Button>(R.id.buttonAddItem)
      buttonAddItem?.setOnClickListener {
          buttonAddItem.findNavController().navigate(R.id.action_navigation_todolist_to_navigation_addnewaction)
      }

    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ActionService {

    var actions = mutableListOf<Action>() // Все дела

    fun countChecked(): Int {
        var count = 0
        for(i in actions.indices)
            if(actions[i].isChecked == true)
                count++
        return count
    }

    fun removeAction(action: Action) {
        val index = actions.indexOfFirst { it.id == action.id } // Находим индекс дела в списке
        if (index == -1) return // Останавливаемся, если не находим такого дела

        actions = ArrayList(actions) // Создаем новый список
        actions.removeAt(index) // Удаляем дело из списка

        notifyChanges()
    }

    private var listeners = mutableListOf<ActionListener>() // Все слушатели

    fun addListener(listener: ActionListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: ActionListener) {
        listeners.remove(listener)
        listener.invoke(actions)
    }

    private fun notifyChanges() = listeners.forEach { it.invoke(actions) }
}

//слушатель
typealias ActionListener = (actions: List<Action>) -> Unit

class App : Application() {
    val actionService = ActionService()
}