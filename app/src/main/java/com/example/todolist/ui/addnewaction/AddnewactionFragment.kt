package com.example.todolist.ui.addnewaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.todolist.R
import com.example.todolist.databinding.FragmentAddnewactionBinding

class AddnewactionFragment : Fragment() {

private var _binding: FragmentAddnewactionBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val addnewactionViewModel =
        ViewModelProvider(this)[AddnewactionViewModel::class.java]

    _binding = FragmentAddnewactionBinding.inflate(inflater, container, false)
    val root: View = binding.root

      //переходы со страницы добавления нового дела на главную
      val buttonAdd = root.findViewById<Button>(R.id.buttonAdd)
      buttonAdd?.setOnClickListener {
          var newAction: ArrayList<String> = arrayListOf(
              root.findViewById<EditText>(R.id.editTextNewAction).text.toString(),
              root.findViewById<EditText>(R.id.editTextDeadLine).text.toString(),
              root.findViewById<EditText>(R.id.editTextDifficulty).text.toString(),
              root.findViewById<EditText>(R.id.editTextImportance).text.toString()
          )
          var bundle = bundleOf( "addnewaction" to newAction)
          buttonAdd.findNavController().navigate(R.id.action_navigation_addnewaction_to_navigation_todolistAdd, bundle)
      }

      val buttonBack1 = root.findViewById<Button>(R.id.buttonBack1)
      buttonBack1?.setOnClickListener {
          buttonBack1.findNavController().navigate(R.id.action_navigation_addnewaction_to_navigation_todolistBack)
      }

    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}