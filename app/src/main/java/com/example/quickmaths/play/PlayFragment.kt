package com.example.quickmaths.play

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quickmaths.R
import com.example.quickmaths.databinding.PlayFragmentBinding

class PlayFragment : Fragment() {

    companion object {
        fun newInstance() = PlayFragment()
    }

    private lateinit var viewModel: PlayViewModel
    private lateinit var binding: PlayFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.play_fragment, container, false)
    }



}