package com.example.home

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.example.adapter.GoodsAdapter
import com.example.home.databinding.ActivityHomeBinding
import com.example.lib_common.ActivityPath
import com.example.lib_http.entity.GoosData
import com.example.viewmodel.GoodsIntent
import com.example.viewmodel.GoodsState
import com.example.viewmodel.GoodsViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/*双向绑定劣势
数据绑定增加Bug调试难度。由于数据和视图的双向绑定，导致出现问题时不太好定位来源，有可能数据问题导致，也有可能业务逻辑中对视图属性的修改导致。
复杂的页面，model也会很大，虽然使用方便了也很容易保证了数据的一致性，当时长期持有，不利于释放内存。
数据双向绑定不利于View重用。
会增加编译出的 apk 文件的类数量和方法数量。
新建一个空的工程，统计打开 build.gradle 中 Data Binding 开关前后的 apk 文件中类数量和方法数量，类增加了 120+，方法数增加了 9k+（开启混淆后该数量减少为 3k+）。
如果工程对方法数量很敏感的话，请慎重使用 Data Binding。*/
@Route(path = ActivityPath.PAGER_HOME)
class HomeActivity : AppCompatActivity() {
    lateinit var vm:GoodsViewModel
    lateinit var dialog:ProgressDialog
    lateinit var db:ActivityHomeBinding
    lateinit var goodsAdapter: GoodsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(db.root)
        vm=ViewModelProvider(this).get(GoodsViewModel::class.java)
        val list = mutableListOf<GoosData>()
        goodsAdapter= GoodsAdapter(this,list,object :GoodsAdapter.ClickListener{
            override fun onClick(position: Int, entity: GoosData) {
                ToastUtils.showLong(position.toString()+"点击了")
            }

        },object :GoodsAdapter.LongClickListener{
            override fun onLongClick(entity: GoosData) {
                ToastUtils.showLong(entity.goods_desc)
            }

        })
        //开启一个协程
        lifecycleScope.launch {
            vm.goodsState.collect{
                when(it){
                    is GoodsState.Loading->{
                        dialog= ProgressDialog(this@HomeActivity)
                        dialog.setTitle("加载中……")
                        dialog.create()
                        dialog.show()
                    }
                    is GoodsState.Success->{
                        it.list.let{//拿到数据
                            dialog.dismiss()
                            Log.i("===",it.toString())
                            goodsAdapter.addData(it)
                        }
                     }
                    is GoodsState.Error->{

                     }

                }
            }
        }

        db.hIv.apply {
            adapter=goodsAdapter
            layoutManager=LinearLayoutManager(this@HomeActivity)

        }


        lifecycleScope.launch {
            //发送意图
            vm.goodschannel.send(GoodsIntent.getGoods(1))
        }
    }
}