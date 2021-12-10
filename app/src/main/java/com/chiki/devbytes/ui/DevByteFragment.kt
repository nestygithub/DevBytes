package com.chiki.devbytes.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.chiki.devbytes.adapters.DevByteAdapter
import com.chiki.devbytes.viewmodels.DevByteViewModel
import com.chiki.devbytes.databinding.FragmentDevByteBinding
import com.chiki.devbytes.domain.Video
import com.chiki.devbytes.viewmodels.DevByteViewModelFactory

class DevByteFragment : Fragment() {

    //Binding
    private var _binding:FragmentDevByteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentDevByteBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val devByteViewModelFactory = DevByteViewModelFactory(requireActivity().application)
        val devByteViewModel = ViewModelProvider(this,devByteViewModelFactory).get(DevByteViewModel::class.java)

        binding.devByteViewModel = devByteViewModel
        binding.lifecycleOwner = this

        val adapter = DevByteAdapter {
            watchVideo(it)
        }
        binding.recyclerView.adapter = adapter

        //Observers
        devByteViewModel.playList.observe(viewLifecycleOwner){playList->
            playList?.apply {
                adapter.submitList(this)
            }
        }
    }

    private fun watchVideo(it: Video) {
        val packageManager = context?.packageManager ?: return
        var intent = Intent(Intent.ACTION_VIEW, it.launchUri)
        if (intent.resolveActivity(packageManager) == null) {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val Video.launchUri: Uri
        get(){
        val httpUri = Uri.parse(url)
        return Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v"))
    }
}