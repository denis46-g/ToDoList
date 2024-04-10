package com.example.todolist.ui.todolist

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.databinding.FragmentTodolistBinding
import com.example.todolist.databinding.ItemActionBinding
import com.example.todolist.global_actions
import com.example.todolist.global_actionsinfo

class TodolistFragment : Fragment() {

private var _binding: FragmentTodolistBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

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
    val todolistViewModel =
        ViewModelProvider(this)[TodolistViewModel::class.java]

    _binding = FragmentTodolistBinding.inflate(inflater, container, false)
    val root: View = binding.root

      val itembind = ItemActionBinding.inflate(layoutInflater)

      actionService.addListener(listener)

      val manager = LinearLayoutManager(requireContext()) // LayoutManager
      adapter = ActionAdapter(binding.textStat, object : intActionListener {

          override fun onActionRemove(action: Action) = actionService.removeAction(action)

          override fun onActionCountChecked(): Int = actionService.countChecked()

      }) // Создание объекта


      if(global_actions != null && global_actions.size != 0)
          actionService.actions = global_actions.toMutableList()
      if(global_actionsinfo != null && global_actionsinfo.size != 0)
          actionService.actionsInfo = global_actionsinfo.toMutableList()

      //добавляем новое дело
      val new_act = arguments?.getStringArrayList("addnewaction")
      if(new_act != null){
          actionService.actions.add(Action(id=actionService.actions.size.toLong(),
              text= new_act[0], isChecked = false))
          actionService.actionsInfo.add(ActionInfo(
              id =actionService.actionsInfo.size.toLong(),
              deadline = new_act[1], difficulty = new_act[2], importance = new_act[3] ))
      }

      binding.textStat.text = "Total: " + actionService.actions.size + " - Checked : " + actionService.countChecked()


      adapter.data = actionService.actions // Заполнение данными
      adapter.datainfo = actionService.actionsInfo

      adapter.count_checked = actionService.countChecked()

      binding.recyclerView.layoutManager = manager // Назначение LayoutManager для RecyclerView
      binding.recyclerView.adapter = adapter // Назначение адаптера для RecyclerView


      // переход с главной страницы на страницу добавления нового дела
      val buttonAddItem = root.findViewById<Button>(R.id.buttonAddItem)
      buttonAddItem?.setOnClickListener {
          global_actions = actionService.actions.toMutableList()
          global_actionsinfo = actionService.actionsInfo.toMutableList()
          buttonAddItem.findNavController().navigate(R.id.action_navigation_todolist_to_navigation_addnewaction)
      }

    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

data class Action(
    val id: Long, // Уникальный номер дела
    val text: String, // дело
    var isChecked: Boolean // Было ли выполнено дело
)

data class ActionInfo(
    val id: Long, // Уникальный номер информации
    val deadline: String, // срок сдачи
    val difficulty: String, // сложность
    val importance: String // важность
)

class ActionService {

    var actions = mutableListOf<Action>() // Все дела

    var actionsInfo = mutableListOf(
        ActionInfo(0, "01.04.2024", "1", "1"),
        ActionInfo(1, "02.04.2024", "2", "2"),
        ActionInfo(2, "03.04.2024", "3", "3"),
        ActionInfo(3, "04.04.2024", "4", "4"),
        ActionInfo(4, "05.04.2024", "5", "5"),
        ActionInfo(5, "06.04.2024", "6", "6"),
        ActionInfo(6, "07.04.2024", "7", "7"),
        ActionInfo(7, "08.04.2024", "8", "8"),
        ActionInfo(8, "09.04.2024", "9", "9"),
        ActionInfo(9, "10.04.2024", "10", "10"),
    ) // Информация о всех делах

    private val text_actions = mutableListOf<String>("Помыть посуду",
        "Сделать компьютерную графику",
        "Сделать искусственный интеллект",
        "Сделать список по андроиду",
        "Сделать серверное программирование",
        "Дело 6", "Дело 7", "Дело 8", "Дело 9", "Дело 10")

    init {
        for(i in 0..<text_actions.size){
            actions.add(Action(
                id = i.toLong(),
                text = text_actions[i],
                isChecked = false
            ))
        }
    }

    fun countChecked(): Int {
        var count = 0
        for(i in actions.indices)
            if(actions[i].isChecked)
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