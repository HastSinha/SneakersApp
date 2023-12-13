package com.example.sneakersapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sneakersapp.modal.ProductItem
import com.example.sneakersapp.modal.Response
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class ProductRepository(private val context: Context) {
    private var data: Response? = null
    private val gson = Gson()
    private lateinit var mutableLiveData: MutableLiveData<List<ProductItem>>

    private suspend fun loadData(): Response? = withContext(Dispatchers.IO) {
        try {
            val jsonString = readJSONFromAssets(context)
            data = gson.fromJson(jsonString, Response::class.java)
            return@withContext data
        } catch (e: IOException) {
            e.printStackTrace()
            return@withContext null
        }
    }

    private suspend fun readJSONFromAssets(context: Context): String = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.assets.open("products.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            return@withContext String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            return@withContext ""
        }
    }

    fun getProductList(): LiveData<List<ProductItem>> {
        mutableLiveData = MutableLiveData()
        CoroutineScope(Dispatchers.IO).launch {
            data = loadData()
            withContext(Dispatchers.Main) {
                data?.productLists?.let {
                    mutableLiveData.value = it
                }
            }
        }
        return mutableLiveData
    }
}
