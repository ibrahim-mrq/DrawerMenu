package com.mrq.drawer_menu_sample.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mrq.drawer_menu_sample.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            typeTitle = it.getString(TYPE_TITLE)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(typeTitle: String) = HomeFragment().apply {
            arguments = Bundle().apply {
                putString(TYPE_TITLE, typeTitle)
            }
        }
    }

    private val TYPE_TITLE = "type_title"
    private var typeTitle: String? = null
    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.title.text = typeTitle
    }

}