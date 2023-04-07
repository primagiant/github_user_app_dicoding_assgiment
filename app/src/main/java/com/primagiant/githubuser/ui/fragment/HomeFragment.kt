package com.primagiant.githubuser.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.primagiant.githubuser.R
import com.primagiant.githubuser.data.remote.response.ItemsItem
import com.primagiant.githubuser.databinding.FragmentHomeBinding
import com.primagiant.githubuser.model.UserViewModel
import com.primagiant.githubuser.ui.DetailUserActivity
import com.primagiant.githubuser.ui.adapter.UserListAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        userViewModel.userList.observe(requireActivity()) { user ->
            setUser(user)
        }

        userViewModel.isLoading.observe(requireActivity()) { loading ->
            showLoading(loading)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        @Suppress("DEPRECATION") setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        @Suppress("DEPRECATION") super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))

        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                userViewModel.setQuery(query)
                userViewModel.userList.observe(requireActivity()) { user ->
                    setUser(user)
                }

                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }

        })
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.fav -> {
                if (parentFragmentManager.findFragmentByTag(
                        FavoriteFragment::class.java.simpleName
                    ) !is FavoriteFragment
                ) {
                    parentFragmentManager.beginTransaction().replace(
                        R.id.frame_container,
                        FavoriteFragment(),
                        FavoriteFragment::class.java.simpleName
                    ).addToBackStack(null).commit()
                }
            }
            R.id.theme_change -> {
                if (parentFragmentManager.findFragmentByTag(
                        ThemeFragment::class.java.simpleName
                    ) !is ThemeFragment
                ) {
                    parentFragmentManager.beginTransaction().replace(
                        R.id.frame_container, ThemeFragment(), ThemeFragment::class.java.simpleName
                    ).addToBackStack(null).commit()
                }
            }
        }
        @Suppress("DEPRECATION") return super.onOptionsItemSelected(item)
    }


    private fun setUser(userList: List<ItemsItem>) {
        val adapter = UserListAdapter(userList, requireActivity())
        binding.rvUser.adapter = adapter

        adapter.setOnUserListClickCallback(object : UserListAdapter.OnUserListClickCallback {
            override fun onItemClicked(user: ItemsItem) {
                // Fragment to Activity
                val intent = Intent(requireActivity(), DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.USERNAME, user.login)
                startActivity(intent)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}