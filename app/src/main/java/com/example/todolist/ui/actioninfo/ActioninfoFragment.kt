package com.example.todolist.ui.actioninfo

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentActioninfoBinding

class ActioninfoFragment : Fragment() {

private var _binding: FragmentActioninfoBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  @RequiresApi(Build.VERSION_CODES.TIRAMISU)
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val actioninfoViewModel =
        ViewModelProvider(this)[ActioninfoViewModel::class.java]

    _binding = FragmentActioninfoBinding.inflate(inflater, container, false)
    val root: View = binding.root

      //получаем из страницы добавления все данные
      var actioninfo = arguments?.getCharSequenceArrayList("actioninfo")

      root.findViewById<TextView>(R.id.TextNewActionInfo).text = actioninfo?.get(0)
      root.findViewById<TextView>(R.id.TextDeadLineInfo).text = actioninfo?.get(1)
      root.findViewById<TextView>(R.id.TextDifficultyInfo).text = actioninfo?.get(2)
      root.findViewById<TextView>(R.id.TextImportanceInfo).text = actioninfo?.get(3)

      // переход со страницы описания дела на главную
      val buttonBack2 = root.findViewById<Button>(R.id.buttonBack2)
      buttonBack2?.setOnClickListener {
          buttonBack2.findNavController().navigate(R.id.action_navigation_actioninfo_to_navigation_todolist)
      }

    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}