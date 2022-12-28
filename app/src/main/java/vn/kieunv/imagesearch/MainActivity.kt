package vn.kieunv.imagesearch

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.kieunv.imagesearch.model.ImageModel
import vn.kieunv.imagesearch.model.Photo
import java.util.ArrayList
import kotlin.math.log


class MainActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var mAdapter: ListImageAdapter
    private var page: Int = 1
    private var query: String = "Nature"
    private var listImage: MutableList<Photo> = mutableListOf()
    private var listImageDelete: MutableList<Photo> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAdapter = ListImageAdapter()
        linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        rcvImage.layoutManager = linearLayoutManager
        rcvImage.setHasFixedSize(true)
        rcvImage.adapter = mAdapter
        mAdapter.setOnItemClick(object: ListImageAdapter.OnItemListener{
            override fun onItemClick(mPos: Int) {
                val myIntent = Intent(this@MainActivity, DetailActivity::class.java)
                myIntent.putExtra("Pos",mPos)
                myIntent.putParcelableArrayListExtra("ListImage", listImage as ArrayList<Photo>)
                startForResult.launch(myIntent)
            }

            override fun onCheck(photo: Photo, isCheck: Boolean) {
                if(isCheck){
                    listImageDelete.add(photo)
                }
            }

        })





        getData()

        rcvImage.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == listImage.size - 1) {
                    pbLoad.visibility = View.VISIBLE
                    page += 1
                    getData()

                }
            }
        })

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(p0.toString().length > 2){
                    val newList: MutableList<Photo> = mutableListOf()
                    listImage = newList
                    mAdapter.addData(newList)
                    page = 1
                    query = p0.toString()
                    getData()
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })



        fabDelete.setOnClickListener {
            listImage.removeAll(listImageDelete)
            mAdapter.addData(listImage)

            Toast.makeText(applicationContext,"Xóa thành công",Toast.LENGTH_SHORT).show()
        }


    }

    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result: ActivityResult ->
        val pos = result.data!!.getIntExtra("result",0)
        linearLayoutManager.scrollToPositionWithOffset(pos, 0);
        rcvImage.scrollToPosition(pos)

    }




    fun getData(){
        ApiService.create().searchImage(page,15, query).enqueue(object :Callback<ImageModel>{
            override fun onResponse(call: Call<ImageModel>, response: Response<ImageModel>) {
                if (response.code() == 200){
                    val res = response.body() as ImageModel
                    pbLoad.visibility = View.GONE
                    listImage.addAll(res.photos)
                    mAdapter.addData(listImage)
                }

            }

            override fun onFailure(call: Call<ImageModel>, t: Throwable) {
                Toast.makeText(applicationContext,t.message,Toast.LENGTH_SHORT).show()
            }

        })
    }


}