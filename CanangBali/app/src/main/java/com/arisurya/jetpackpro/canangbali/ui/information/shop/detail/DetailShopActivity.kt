package com.arisurya.jetpackpro.canangbali.ui.information.shop.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.arisurya.jetpackpro.canangbali.data.source.local.entity.ShopEntity
import com.arisurya.jetpackpro.canangbali.databinding.ActivityDetailShopBinding
import com.bumptech.glide.Glide

class DetailShopActivity : AppCompatActivity(), View.OnClickListener {
    companion object{
        const val EXTRA_SHOP ="extra_shop"
    }

    private lateinit var  viewModel: DetailShopViewModel
    private lateinit var detailShopBinding: ActivityDetailShopBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailShopBinding = ActivityDetailShopBinding.inflate(layoutInflater)
        setContentView(detailShopBinding.root)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailShopViewModel::class.java]
        val extras = intent.extras

        if(extras!=null){
            val shopId = extras.getString(EXTRA_SHOP)
            if(shopId!=null){
                viewModel.setSelectedShop(shopId)
                populateShop(viewModel.getDetailShop())
            }
        }
    }

    private fun populateShop(shop: ShopEntity) {
        detailShopBinding.apply {
            tvShopName.text = shop.name
            tvShopLoc.text = shop.location
            tvShopTlp.text = shop.tlp
            tvProductDesc.text = shop.product
            tvDesc.text = shop.desc
            Glide.with(this@DetailShopActivity)
                .load(shop.imgPath)
                .into(imgShop)

            btnTlp.setOnClickListener(this@DetailShopActivity)
        }
    }

    override fun onClick(v: View?) {
       when(v){
           detailShopBinding.btnTlp ->{
               callShop()
           }
       }
    }

    private fun callShop() {
        val telp = viewModel.getDetailShop().tlp
        val dialPhoneIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+telp))
        startActivity(dialPhoneIntent)
    }
}