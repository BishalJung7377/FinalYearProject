package com.bishaljung.vetementsfashionnepal.ui.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bishaljung.vetementsfashionnepal.Database.BuyerDb
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Repository.DiscoverItemRepository
import com.bishaljung.vetementsfashionnepal.ui.adapter.FavouriteItemAdapter
import com.bishaljung.vetementsfashionnepal.ui.adapter.RecentItemsAdapter
import com.bishaljung.vetementsfashionnepal.Entity.FavouriteItemModel
import com.bishaljung.vetementsfashionnepal.Repository.BuyerFavouriteItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavouritesFragment : Fragment() {
    private var favouriteItemList = ArrayList<FavouriteItemModel>()
    private lateinit var favouriterecyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_fragment_favourite, container, false)

        favouriterecyclerView = view.findViewById(R.id.favouriterecyclerView)


        val adapter = context?.let { FavouriteItemAdapter(favouriteItemList, it) }
        favouriterecyclerView.layoutManager = LinearLayoutManager(null)
        favouriterecyclerView.adapter = adapter

        favouriteItems()
        return view
    }


    ////////////////============function for calling favourite items data from API -----------------////////////
    private fun favouriteItems() {
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val favouriteItemRepository = BuyerFavouriteItemRepository()
                val response = favouriteItemRepository.getBuyerFavouriteItem()
                if(response.success==true){
                    val favouriteItemList = response.message

                    ///////////=============adding items to room database=========//////////
                    BuyerDb.getInstance(requireContext()).getFavouriteItemDAO().deleteFavouriteItem()
                    BuyerDb.getInstance(requireContext()).getFavouriteItemDAO().insertFavouriteItem(favouriteItemList as List<FavouriteItemModel>)
                    withContext(Dispatchers.Main){
                        val adapter = FavouriteItemAdapter(favouriteItemList as  ArrayList<FavouriteItemModel>, requireContext())
                        favouriterecyclerView.layoutManager = LinearLayoutManager(context)
                        favouriterecyclerView.adapter = adapter
                    }
                }
                else{

                }
            }
        }catch (ex: Exception){
            Toast.makeText(
                context,
                "Error : ${ex.toString()}", Toast.LENGTH_SHORT
            ).show()
        }
    }
}