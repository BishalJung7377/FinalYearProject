package com.bishaljung.vetementsfashionnepal.ui.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bishaljung.vetementsfashionnepal.Database.BuyerDb
import com.bishaljung.vetementsfashionnepal.Entity.NewItemModel
import com.bishaljung.vetementsfashionnepal.Entity.RecentItemsModel
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.Repository.DiscoverItemRepository
import com.bishaljung.vetementsfashionnepal.ui.ViewAllItemActivity
import com.bishaljung.vetementsfashionnepal.ui.adapter.NewItemAdapter
import com.bishaljung.vetementsfashionnepal.ui.adapter.RecentItemsAdapter
import com.bishaljung.vetementsfashionnepal.ui.adapter.SmalliconItemAdapter
import com.bishaljung.vetementsfashionnepal.ui.adapter.TestimonialAdapter
import com.bishaljung.vetementsfashionnepal.ui.model.SmallItemsIconModel
import com.bishaljung.vetementsfashionnepal.ui.model.TestimonialModel
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragments : Fragment() {
    private lateinit var recyclerViewrecentitem: RecyclerView
    private lateinit var recyclerViewTestimonial: RecyclerView
    private lateinit var recyclerViewSmallitem: RecyclerView
    private lateinit var recyclerViewnewitem: RecyclerView
    private lateinit var imgSlider: ImageSlider
    private lateinit var DiscountimgSlider: CarouselView
    private lateinit var txtTestimonials: TextView
    private lateinit var txtNewestProduct2: TextView
    private lateinit var txtrecentproduct: TextView
    private lateinit var txtViewallproduct: TextView
    private lateinit var txtnewproductviewall: TextView


    private var slideModel: MutableList<SlideModel> = ArrayList()
    private var recentitemlist = ArrayList<RecentItemsModel>()
    private var newtItemList = ArrayList<NewItemModel>()
    private var smallItemList = ArrayList<SmallItemsIconModel>()
    private var testimonialList = ArrayList<TestimonialModel>()

    var sampleImages = intArrayOf(
        R.drawable.smmr,
        R.drawable.smur
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(
            R.layout.activity_fragment_home, container, false
        )

        txtViewallproduct = view.findViewById(R.id.txtViewallproduct)
        txtnewproductviewall = view.findViewById(R.id.txtnewproductviewall)

        txtViewallproduct.setOnClickListener {
            val intent = Intent(context, ViewAllItemActivity::class.java)
            startActivity(intent)
        }
        txtnewproductviewall.setOnClickListener {
            val intent = Intent(context, ViewAllItemActivity::class.java)
            startActivity(intent)
        }

        txtTestimonials = view.findViewById(R.id.txtTestimonials)
        txtNewestProduct2 = view.findViewById(R.id.txtNewestProduct2)
        txtrecentproduct = view.findViewById(R.id.txtrecentproduct)
        txtViewallproduct = view.findViewById(R.id.txtViewallproduct)
        txtnewproductviewall = view.findViewById(R.id.txtnewproductviewall)

        imgSlider = view.findViewById(R.id.imgSlider)
         DiscountimgSlider = view.findViewById(R.id.DiscountimgSlider)
        recyclerViewTestimonial = view.findViewById(R.id.recyclerViewTestimonial)
        recyclerViewrecentitem = view.findViewById(R.id.recyclerViewrecentitem)
        recyclerViewSmallitem = view.findViewById(R.id.recyclerViewSmallitem)
        recyclerViewnewitem = view.findViewById(R.id.recyclerViewnewitem)

        getRecentItemsData()
        SmallItemIcon()


        val adapter = context?.let { RecentItemsAdapter(recentitemlist, it) }
        recyclerViewrecentitem.adapter = adapter


        val adapter1 = context?.let { SmalliconItemAdapter(smallItemList, it) }
        val mLayoutManager1 = LinearLayoutManager(context)
        mLayoutManager1.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewSmallitem.layoutManager = mLayoutManager1
        recyclerViewSmallitem.adapter = adapter1

        val newitemadapter = context?.let { NewItemAdapter(newtItemList, it) }
        recyclerViewnewitem.adapter = newitemadapter



        testimonialData()
        val testimonialadapter = context?.let { TestimonialAdapter(testimonialList, it) }
        val newtestimonialManager = LinearLayoutManager(context)
        newtestimonialManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerViewTestimonial.layoutManager = newtestimonialManager
        recyclerViewTestimonial.adapter = testimonialadapter


        imageslide()
        imgSlider.setImageList(slideModel, true)

        DiscountimgSlider.setPageCount(sampleImages.size);

        DiscountimgSlider.setImageListener(imageListener);
        return view
    }
    var imageListener =
        ImageListener { position, imageView -> imageView.setImageResource(sampleImages[position]) }



    ////////==========================function for getting recent items===============///////////
    private fun getRecentItemsData() {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val recentItemRepository = DiscoverItemRepository()
                val response = recentItemRepository.getAllRecentItems()
                if (response.success == true) {
                    val recentitemlist = response.message
                    ///////////=============adding items to room database=========//////////

                    BuyerDb.getInstance(requireContext()).getItemListDAO().deleteItem()
                    BuyerDb.getInstance(requireContext()).getItemListDAO()
                        .insertItemData(recentitemlist)
                    withContext(Main) {
                        val adapter = RecentItemsAdapter(
                            recentitemlist as ArrayList<RecentItemsModel>,
                            requireContext()
                        )
                        val itemadapter = NewItemAdapter(
                            recentitemlist as ArrayList<NewItemModel>,
                            requireContext()
                        )
                        recyclerViewrecentitem.layoutManager = LinearLayoutManager(requireContext())
                        (recyclerViewrecentitem.layoutManager as LinearLayoutManager).orientation =
                            LinearLayoutManager.HORIZONTAL
                        recyclerViewrecentitem.adapter = adapter

                        recyclerViewnewitem.layoutManager = LinearLayoutManager(requireContext())
                        (recyclerViewnewitem.layoutManager as LinearLayoutManager).orientation =
                            LinearLayoutManager.HORIZONTAL
                        recyclerViewnewitem.adapter = adapter

                    }
                }
            }
        } catch (ex: Exception) {
            Toast.makeText(
                context,
                "Error : ${ex.toString()}", Toast.LENGTH_SHORT
            ).show()
        }
    }

/////////////////////------------------------- funtction for loading images in top sliders-------------------///////////////////
    private fun imageslide() {
        slideModel.add(
            SlideModel(
                "https://image.freepik.com/free-vector/summer-badge-collection_23-2148143015.jpg"
            )
        )
        slideModel.add(
            SlideModel(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbJDhz4h0geBxhKSBClJ5i17hKi45Lqz_BSg&usqp=CAU"
            )
        )
        slideModel.add(
            SlideModel(
                "https://image.freepik.com/free-vector/hello-summer-background_23-2148135266.jpg"
            )
        )
        slideModel.add(
            SlideModel(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR53_tw-KeueZdEbVHjY9HQTM56tbbdraVldw&usqp=CAU"
            )
        )

        slideModel.add(
            SlideModel(
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRiO5k_iM2bvfVzKOLU-lVfg9Tn5NsmP6Jf8A&usqp=CAU"
            )
        )
    }


    /////////////////////////------------------------- funtction for loading images in bottom  sliders-------------------///////////////////
    private fun testimonialData() {
        testimonialList.add(
            TestimonialModel(
                "Dayahang Rai",
                "Great experience with this company. Attentive and excellent communication. Up front pricing and excellent work completed. Would recommend for anyone.",
                "data:image/jpeg;base64,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"
            )
        )
        testimonialList.add(
            TestimonialModel(
                "Bipin Karki",
                " Attentive and excellent communication. Up front pricing and excellent work completed. Would recommend for anyone.",
                "https://pasadai.com/wp-content/uploads/2019/11/Bipin-Karki.jpg"
            )
        )
        testimonialList.add(
            TestimonialModel(
                "Rajesh Hamal",
                "Good deleviry Liked most Fine Attentive and excellent communication. Up front pricing and excellent work completed. Would recommend for anyone.",
                "https://pbs.twimg.com/profile_images/416942818053992448/ybxZ_hlU_400x400.jpeg"
                  )
        )
    }



    //////////////f---------------------------function for icons in in category recycler view-------------------////////////
    private fun SmallItemIcon() {
        smallItemList.add(
            SmallItemsIconModel(
                1,
                "Accessories",
                "https://img.icons8.com/dusk/50/000000/new.png"
            )
        )
        smallItemList.add(
            SmallItemsIconModel(
                2,
                "Glasses",
                "https://img.icons8.com/nolan/64/sun.png"
            )
        )
        smallItemList.add(
            SmallItemsIconModel(
                3,
                "Fashion",
                "https://img.icons8.com/clouds/100/000000/fashion-portal.png"
            )
        )
        smallItemList.add(
            SmallItemsIconModel(
                4,
                "Luggage",
                "https://img.icons8.com/cotton/64/000000/luggage--v1.png"
            )
        )
        smallItemList.add(
            SmallItemsIconModel(
                5,
                "Mens",
                "https://img.icons8.com/bubbles/50/000000/happy-man.png"
            )
        )
        smallItemList.add(
            SmallItemsIconModel(
                6,
                "Glasses",
                "https://img.icons8.com/nolan/64/sun.png"
            )
        )
        smallItemList.add(
            SmallItemsIconModel(
                7,
                "Women",
                "https://img.icons8.com/bubbles/50/000000/storm-lady.png"
            )
        )
        smallItemList.add(
            SmallItemsIconModel(
                8,
                "Shoes",
                "https://img.icons8.com/cotton/50/000000/sneakers.png"
            )
        )
    }

}