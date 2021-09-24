package com.bishaljung.vetementsfashionnepal.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bishaljung.vetementsfashionnepal.Database.BuyerDb
import com.bishaljung.vetementsfashionnepal.Entity.ViewAllItemModel
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Repository.DiscoverItemRepository
import com.bishaljung.vetementsfashionnepal.ui.adapter.ViewAllItemAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class ViewAllItemActivity : AppCompatActivity() {
    private lateinit var recyclerViewAllItem: RecyclerView
    private var viewAllItemList = mutableListOf<ViewAllItemModel>()
    var displayList = ArrayList<ViewAllItemModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_item)

        recyclerViewAllItem = findViewById(R.id.recyclerViewAllItem)

        getRecentItemsData()
        displayList.addAll(viewAllItemList)


    }


private fun loadData(){
    try {
        CoroutineScope(Dispatchers.IO).launch {
            val viewallItemRepository = DiscoverItemRepository()
            val response = viewallItemRepository.ViewAllItems()
            if (response.success == true) {
                val allDa = response.message
            }
        }
    } catch (ex: Exception) {
        Toast.makeText(
            this@ViewAllItemActivity,
            "Error : ${ex.toString()}", Toast.LENGTH_SHORT
        ).show()
    }
}



    /////recent item data getting From API
    private fun getRecentItemsData() {

        try {
            CoroutineScope(Dispatchers.IO).launch {
                val viewallItemRepository = DiscoverItemRepository()
                val response = viewallItemRepository.ViewAllItems()
                if (response.success == true) {
                    viewAllItemList = response.message!!
                    BuyerDb.getInstance(this@ViewAllItemActivity).getAllItemDAO().deleteAllItem()
                    BuyerDb.getInstance(this@ViewAllItemActivity).getAllItemDAO()
                        .insertAlItemData(viewAllItemList)
                    withContext(Dispatchers.Main) {

                        val adapter = ViewAllItemAdapter(
                            viewAllItemList as ArrayList<ViewAllItemModel>,
                            this@ViewAllItemActivity
                        )
                        recyclerViewAllItem.layoutManager =
                            GridLayoutManager(this@ViewAllItemActivity, 2)
                        recyclerViewAllItem.adapter = adapter


                    }
                }
            }
        } catch (ex: Exception) {
            Toast.makeText(
                this@ViewAllItemActivity,
                "Error : ${ex.toString()}", Toast.LENGTH_SHORT
            ).show()
        }
    }


    ////////////Search bar code for searching in activity
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_navbar, menu)
        val menuItem = menu!!.findItem(R.id.action_search)
        if (menuItem != null) {
            val searchView = menuItem.actionView as androidx.appcompat.widget.SearchView

            searchView.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d("Changed","Data")
                    if (newText!!.isNotEmpty()) {
                        Log.d("Value",newText)
                        Log.d("Before",displayList.size.toString())
                        displayList.clear()
                        Log.d("After",viewAllItemList.size.toString())
                        val search = newText.toLowerCase(Locale.getDefault())
                        viewAllItemList.forEach {
                            Log.d("Itemname",it.ProductName!!)
                            if (it.ProductName?.toLowerCase(Locale.getDefault())!!
                                    .contains(search)
                            ) {
                                displayList.add(it)

                            }
                            val newAdapter = ViewAllItemAdapter(displayList, this@ViewAllItemActivity)

                            recyclerViewAllItem.adapter=newAdapter

                        }
                    } else {
                        displayList.clear()
                        displayList.addAll(viewAllItemList)
                        recyclerViewAllItem.adapter!!.notifyDataSetChanged()
                    }
                    return true
                }

            })
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

}
