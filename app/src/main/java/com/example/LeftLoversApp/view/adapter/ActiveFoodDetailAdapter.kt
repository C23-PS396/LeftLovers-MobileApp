package com.example.LeftLoversApp.view.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.LeftLoversApp.R
import com.example.LeftLoversApp.localData.Food
import com.example.LeftLoversApp.localData.FoodCartItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
//import com.example.LeftLoversApp.view.detail.DetailActivity
import java.text.NumberFormat
import java.util.Locale

class ActiveFoodDetailAdapter(private val listStory: List<Food>, private val cartManager: CartManager) :
    RecyclerView.Adapter<ActiveFoodDetailAdapter.ViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_merch, viewGroup, false)
        )

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val (id, createdAt, updatedAt, name, pictureUrl, price, merchantId, category, activeFood) = listStory[position]
        Glide.with(viewHolder.itemView.context).load(pictureUrl).into(viewHolder.tvImg)
        viewHolder.tvTitle.text = name
        val currencyFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        currencyFormat.maximumFractionDigits = 0
        val formattedPrice = currencyFormat.format(price)
        viewHolder.tvDesc.text = "Harga: $formattedPrice"

        viewHolder.tvAdd.setOnClickListener {
            cartManager.addItem(id, 1)
            viewHolder.updateQuantity(cartManager.getQuantity(id))
        }

//        viewHolder.tvRemove.setOnClickListener {
//            cartManager.removeItem(id)
//            viewHolder.updateQuantity(cartManager.getQuantity(id))
//        }
        viewHolder.tvRemove.setOnClickListener {
            val currentQuantity = cartManager.getQuantity(id)
            if (currentQuantity > 0) {
                cartManager.removeItem(id)
                viewHolder.updateQuantity(cartManager.getQuantity(id))
            }
        }
        viewHolder.tvCek.setOnClickListener() {
            val cartItems = cartManager.getItems()

            // Lakukan sesuatu dengan daftar item keranjang, misalnya menampilkannya di logcat
            for (entry in cartItems.entries) {
                val foodId = entry.key
                val quantity = entry.value
                Log.d("Keranjang", "FoodId: $foodId, Quantity: $quantity")
            }

        }

        viewHolder.updateQuantity(cartManager.getQuantity(id))
    }

    override fun getItemCount(): Int = listStory.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_item_title)
        val tvImg: ImageView = itemView.findViewById(R.id.img_story)
        val tvDesc: TextView = itemView.findViewById(R.id.tv_item_desc)
        val tvAdd: Button = itemView.findViewById(R.id.btn_plus)
        val tvRemove: Button = itemView.findViewById(R.id.btn_minus)
        private val qtt: TextView = itemView.findViewById(R.id.tv_counter)
        val tvCek: Button = itemView.findViewById(R.id.tv_cek_response)

        fun updateQuantity(quantity: Int) {
            qtt.text = quantity.toString()
        }
    }
    fun resetCart() {
        cartManager.resetCart()
    }
}

class CartManager(private val context: Context) {
    private val FoodsItem: MutableMap<String, Int> = mutableMapOf()

    init {
        // Memulihkan data keranjang dari SharedPreferences saat inisialisasi
        restoreCartItems()
    }

    fun addItem(foodId: String, quantity: Int) {
        val existingQuantity = FoodsItem[foodId] ?: 0
        val newQuantity = existingQuantity + quantity
        FoodsItem[foodId] = newQuantity

        saveCartItems() // Menyimpan data keranjang setelah penambahan item
    }

    fun removeItem(foodId: String) {
        if (FoodsItem.containsKey(foodId)) {
            val existingQuantity = FoodsItem[foodId]!!
            if (existingQuantity > 1) {
                FoodsItem[foodId] = existingQuantity - 1
            } else {
                FoodsItem.remove(foodId)
            }
            saveCartItems()
        }
    }

    fun getItems(): Map<String, Int> {
        return FoodsItem.toMap()
    }

    fun getQuantity(foodId: String): Int {
        return FoodsItem[foodId] ?: 0
    }

    private fun saveCartItems() {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(FoodsItem)
        editor.putString("cartItems", json)
        editor.apply()
    }

    private fun restoreCartItems() {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("CartPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("cartItems", "")
        val type = object : TypeToken<Map<String, Int>>() {}.type
        FoodsItem.clear()
        FoodsItem.putAll(gson.fromJson(json, type) ?: emptyMap())
    }
    fun resetCart() {
        FoodsItem.clear()
        saveCartItems()
    }
    companion object {
        @Volatile
        private var INSTANCE: CartManager? = null

        fun getInstance(context: Context): CartManager =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CartManager(context).also { INSTANCE = it }
            }
    }
}
