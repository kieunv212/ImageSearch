package vn.kieunv.imagesearch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_detail.*
import vn.kieunv.imagesearch.model.Photo

class DetailActivity : AppCompatActivity() {

    var posChange: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Chi tiết"
        setContentView(R.layout.activity_detail)
        val mPos = intent.getIntExtra("Pos",0)
        posChange  = mPos
        val listImage: ArrayList<Photo> = intent.getParcelableArrayListExtra("ListImage")!!

        val mAdapter = ListImageDetailAdapter(listImage)
        val linearLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
        rcvImageDetail.layoutManager = linearLayoutManager
        rcvImageDetail.setHasFixedSize(true)
        rcvImageDetail.adapter = mAdapter
        rcvImageDetail.scrollToPosition(mPos)
        rcvImageDetail.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState === RecyclerView.SCROLL_STATE_IDLE) {
                    val position: Int = (rcvImageDetail.layoutManager as LinearLayoutManager)
                        .findFirstVisibleItemPosition()
                    posChange = position
                }
            }
        })
    }

    override fun onBackPressed() {
        Log.v("onActivityResulton", posChange.toString())
        val intent = Intent()
        intent.putExtra("result", posChange)
        setResult(0, intent)
        super.onBackPressed()
    }
}