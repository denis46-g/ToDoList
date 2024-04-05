package com.example.todolist

import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.databinding.ItemActionBinding
import android.content.pm.PackageManager.NameNotFoundException
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ActionAdapter // Объект Adapter
    private var app = App()
    private val actionService: ActionService = app.actionService


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityMainBinding.inflate(layoutInflater)

        val itembind = ItemActionBinding.inflate(layoutInflater)

        this.title = "To do list               Total: " + actionService.actions.size + " - Checked : 0"

     setContentView(binding.root)

        val manager = LinearLayoutManager(this) // LayoutManager
        adapter = ActionAdapter(this) // Создание объекта
        adapter.data = actionService.actions // Заполнение данными

        binding.recyclerView.layoutManager = manager // Назначение LayoutManager для RecyclerView
        binding.recyclerView.adapter = adapter // Назначение адаптера для RecyclerView




        //val navView: BottomNavigationView = binding.navView

        //val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        /*val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)*/
    }
}

data class Action(
    val id: Long, // Уникальный номер дела
    val text: String, // дело
    var isChecked: Boolean // Было ли выполнено дело
)

class ActionService {

    var actions = mutableListOf<Action>() // Все дела

    private val text_actions = mutableListOf<String>("Помыть посуду",
        "Сделать компьютерную графику",
        "Сделать искусственный интеллект",
        "Сделать список по андроиду",
        "Сделать серверное программирование",
        "Дело 6", "Дело 7", "Дело 8", "Дело 9", "Дело 10")

    init {
        for(i in 0..9){
            actions.add(Action(
                id = i.toLong(),
                text = text_actions[i],
                isChecked = false
            ))
        }
    }
}

class App : Application() {
    val actionService = ActionService()
}