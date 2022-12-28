package vn.kieunv.imagesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.kieunv.imagesearch.model.ImageModel
import vn.kieunv.imagesearch.model.Photo

class MainActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var listImage: MutableList<Photo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val img = ImageModel("https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg")
//        val listImage: MutableList<ImageModel> = mutableListOf(img)
//        val mAdapter = ListImageAdapter(listImage)
//        linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//        rcvImage.layoutManager = linearLayoutManager
//        rcvImage.setHasFixedSize(true)
//        rcvImage.adapter = mAdapter

        fabDelete.setOnClickListener {
            Toast.makeText(applicationContext,"ahhhhhiii",Toast.LENGTH_SHORT).show()
        }

        ApiService.create().searchImage(1,15, "People").enqueue(object :Callback<ImageModel>{
            override fun onResponse(call: Call<ImageModel>, response: Response<ImageModel>) {
                val res = response.body() as ImageModel
                listImage = res.photos
                val mAdapter = ListImageAdapter(listImage)
                linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                rcvImage.layoutManager = linearLayoutManager
                rcvImage.setHasFixedSize(true)
                rcvImage.adapter = mAdapter
            }

            override fun onFailure(call: Call<ImageModel>, t: Throwable) {
                Toast.makeText(applicationContext,t.message,Toast.LENGTH_SHORT).show()
            }

        })
    }
}