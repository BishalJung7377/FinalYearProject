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
import com.bishaljung.vetementsfashionnepal.Entity.FavouriteItemModel
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Repository.BuyerFavouriteItemRepository
import com.bishaljung.vetementsfashionnepal.ui.adapter.FavouriteItemAdapter
import com.bishaljung.vetementsfashionnepal.ui.adapter.ItemCartAdapter
import com.bishaljung.vetementsfashionnepal.Entity.ItemCartModel
import com.bishaljung.vetementsfashionnepal.Repository.BuyerCartItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CartFragments : Fragment() {
    private lateinit var recyclerView: RecyclerView

    private var cartItemlist = ArrayList<ItemCartModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_fragment_cart, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        cartItems()

        return view
    }
/////////////===========================function for adding items to cart========//////////////
    private fun cartItems() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val catItemRepository = BuyerCartItemRepository()
                val response = catItemRepository.getBuyercartItem()
                println(response.message)
                if (response.success == true) {
                    val cartItemList = response.message
                    println("this is $cartItemList")
                    BuyerDb.getInstance(requireContext()).getCartItemDAO().deleteCartItem()
                    BuyerDb.getInstance(requireContext()).getCartItemDAO().insertCartItem(cartItemList as List<ItemCartModel>)
                    withContext(Dispatchers.Main) {
                        println(response)
                        val adapter = ItemCartAdapter(
                            cartItemList as ArrayList<ItemCartModel>,
                            requireContext()
                        )
                        recyclerView.layoutManager = LinearLayoutManager(context)
                        recyclerView.adapter = adapter
                    }
                } else {

                }
            }
        } catch (ex: Exception) {
            Toast.makeText(
                context,
                "Error : ${ex.toString()}", Toast.LENGTH_SHORT
            ).show()
        }

    }

}
