package com.vr.personalchef.HomeFragmentRecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vr.personalchef.R

class MyAdapterHomeFragment(private val recipeList: ArrayList<Recipe>,var clickListener: onRecipeItemClickListener) :
    RecyclerView.Adapter<MyAdapterHomeFragment.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapterHomeFragment.MyViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_list_item, parent, false)
        return MyViewHolder(itemView)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.initialize(recipeList[position],clickListener)
    }


    override fun getItemCount(): Int {
        return recipeList.size
    }


    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val dishName : TextView = itemView.findViewById(R.id.txtRecipeNameRecyclerView)
        val dishImage: ImageView = itemView.findViewById(R.id.imgDishImageRecyclerView)

        fun initialize(recipeList: Recipe,action: onRecipeItemClickListener){
            dishName.text = recipeList.dishname
           Glide.with(itemView)
               .load(recipeList.imgurl)
               .into(dishImage)


            itemView.setOnClickListener {
                action.onItemClick(recipeList,adapterPosition)
            }


        }


    }



}


interface onRecipeItemClickListener{
    fun onItemClick(recipeList : Recipe,position: Int)
}