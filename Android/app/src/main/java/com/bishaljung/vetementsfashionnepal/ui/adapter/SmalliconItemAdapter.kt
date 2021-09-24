package com.bishaljung.vetementsfashionnepal.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bishaljung.vetementsfashionnepal.R
import com.bishaljung.vetementsfashionnepal.ui.ItemDisplayActivity
import com.bishaljung.vetementsfashionnepal.ui.StartPageActivity
import com.bishaljung.vetementsfashionnepal.ui.ViewAllItemActivity
import com.bishaljung.vetementsfashionnepal.ui.model.SmallItemsIconModel
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class SmalliconItemAdapter(
    val smallItemList: ArrayList<SmallItemsIconModel>,
    val context: Context
) :RecyclerView.Adapter<SmalliconItemAdapter.SmalliconItemViewHolder>(){
   class SmalliconItemViewHolder(view: View): RecyclerView.ViewHolder(view){
       val smallItemImage: CircleImageView
       val smallItemname: TextView
       init {
           smallItemImage= view.findViewById(R.id.smallItemImage)
           smallItemname = view.findViewById(R.id.smallItemname)
       }
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmalliconItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_small_icon_item,parent,false)

        return SmalliconItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SmalliconItemViewHolder, position: Int) {

        val smallItemIcon = smallItemList[position]
        Glide.with(context).load(smallItemIcon.smallItemImage)
            .into(holder.smallItemImage)
        holder.smallItemname.text=smallItemIcon.smallItemName

        holder.smallItemImage.setOnClickListener{
            val intent = Intent(context, ViewAllItemActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return smallItemList.size
    }
}