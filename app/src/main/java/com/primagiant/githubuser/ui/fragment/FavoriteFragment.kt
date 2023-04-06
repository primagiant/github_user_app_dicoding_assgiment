package com.primagiant.githubuser.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.primagiant.githubuser.data.Result
import com.primagiant.githubuser.data.local.entity.FavoriteUserEntity
import com.primagiant.githubuser.databinding.FragmentHomeBinding
import com.primagiant.githubuser.model.FavoriteUserViewModel
import com.primagiant.githubuser.model.FavoriteUserModelFactory
import com.primagiant.githubuser.ui.DetailUserActivity
import com.primagiant.githubuser.ui.adapter.FavoriteUserAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val favoriteUserViewModel by viewModels<FavoriteUserViewModel> {
        FavoriteUserModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteUserViewModel.getFavoriteUser().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        val favoriteUserList = result.data
                        setUser(favoriteUserList)
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

    }

    private fun setUser(favUserList: List<FavoriteUserEntity>) {
        val adapter = FavoriteUserAdapter(favUserList, requireActivity())
        binding.rvUser.adapter = adapter

        adapter.setOnUserListClickCallback(object : FavoriteUserAdapter.OnFavUserListClickCallback {
            override fun onItemClicked(user: FavoriteUserEntity) {
                // Fragment to Activity
                val intent = Intent(requireActivity(), DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.USERNAME, user.username)
                startActivity(intent)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}