package com.bishaljung.vetementsfashionnepal.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.ui.model.SmallItemsIconModel
import com.bishaljung.vetementsfashionnepal.ui.model.TestimonialModel
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class TestimonialAdapter (
    val testimonialList: ArrayList<TestimonialModel>,
    val context: Context
    ) :RecyclerView.Adapter<TestimonialAdapter.TestimonialViewHolder>(){
        class TestimonialViewHolder(view: View): RecyclerView.ViewHolder(view){
            val testimonialUserProfile: CircleImageView
            val testimonialusername: TextView
            val tvTestimonial: TextView

            init {
                testimonialUserProfile= view.findViewById(R.id.testiUserProfile)
                testimonialusername = view.findViewById(R.id.testiusername)
                tvTestimonial = view.findViewById(R.id.tvTestimonial)

            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestimonialViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_testimonal_display,parent,false)

            return TestimonialViewHolder(view)
        }

        override fun onBindViewHolder(holder: TestimonialViewHolder, position: Int) {

            val testimonialText = testimonialList[position]
            Glide.with(context).load(testimonialText.testimonialuserImage)
                .into(holder.testimonialUserProfile)
            holder.testimonialusername.text=testimonialText.testimonialuserName
            holder.tvTestimonial.text=testimonialText.testimonialDescription

        }

        override fun getItemCount(): Int {
            return testimonialList.size
        }

    }
