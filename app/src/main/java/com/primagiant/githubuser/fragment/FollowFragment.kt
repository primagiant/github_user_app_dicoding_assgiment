package com.primagiant.githubuser.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.primagiant.githubuser.adapter.FollowAdapter
import com.primagiant.githubuser.databinding.FragmentFollowBinding
import com.primagiant.githubuser.model.UserViewModel
import com.primagiant.githubuser.response.ItemsItem

class FollowFragment : Fragment() {

    private var position: Int? = 0
    private var username: String? = null

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)

        if (position == 1){
            viewFollower()
        } else {
            viewFollowing()
        }

        userViewModel.isLoading.observe(requireActivity()) { loading ->
            showLoading(loading)
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun viewFollower() {
        username?.let { userViewModel.getFollowers(it) }
        userViewModel.follower.observe(requireActivity()) {
            setData(it)
        }
    }

    private fun viewFollowing() {
        username?.let { userViewModel.getFollowing(it) }
        userViewModel.following.observe(requireActivity()) {
            setData(it)
        }
    }

    private fun setData(data : List<ItemsItem>) {
        val adapter = FollowAdapter(data, requireActivity())
        binding.rvFollow.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val ARG_USERNAME = "param1"
        const val ARG_POSITION = "param2"
    }
}