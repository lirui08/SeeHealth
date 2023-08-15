package com.example.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.home.databinding.HomeItemgoodsBinding
import com.example.lib_http.entity.GoosData

class GoodsAdapter(val context:Context,val data:MutableList<GoosData>,
        val click:ClickListener,val longClick:LongClickListener):
    RecyclerView.Adapter<GoodsAdapter.GoodsViewHolder>() {

    lateinit var itembd:HomeItemgoodsBinding
    inner class GoodsViewHolder(view:View):RecyclerView.ViewHolder(view){
        var title=itembd.hTitle
        var iv=itembd.hIv
    }

    fun addData(data:MutableList<GoosData>){
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoodsViewHolder {
        itembd=HomeItemgoodsBinding.inflate(LayoutInflater.from(context),parent,false)
        return GoodsViewHolder(itembd.root)
    }

    override fun onBindViewHolder(holder: GoodsViewHolder, position: Int) {
        val goosData = data.get(position)
        holder.title.text=goosData.goods_desc
        Glide.with(context).load(goosData.goods_default_icon)
            .apply(RequestOptions().transform(CenterCrop(),RoundedCorners(20)))
            .into(holder.iv)
        //项的点击事件
        holder.itemView.setOnClickListener{
            click.onClick(position,goosData)
        }
        //项的长按事件
        holder.itemView.setOnLongClickListener {
            longClick.onLongClick(goosData)
            true
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface ClickListener{
        fun onClick(position: Int,entity:GoosData)
    }

    interface LongClickListener{
        fun onLongClick(entity:GoosData)
    }
}