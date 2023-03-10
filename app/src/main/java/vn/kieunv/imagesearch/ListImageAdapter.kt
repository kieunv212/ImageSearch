package vn.kieunv.imagesearch

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image.view.*
import vn.kieunv.imagesearch.model.ImageModel
import vn.kieunv.imagesearch.model.Photo
import java.net.URL
import java.util.concurrent.Executors


class ListImageAdapter(): RecyclerView.Adapter<ListImageAdapter.ViewHolder>() {

    private var listImage: MutableList<Photo> = mutableListOf()
    private lateinit var mOnItemListener: OnItemListener

    interface OnItemListener {
        fun onItemClick(mPos: Int)
        fun onCheck(photo: Photo,isCheck: Boolean)
    }

    fun setOnItemClick(onItemListener: OnItemListener) {
        mOnItemListener = onItemListener
    }

    fun addData(mlistImage: MutableList<Photo>) {
        listImage = mlistImage
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.txtPhotographer.text = listImage[position].photographer
        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        executor.execute {
            try {
                val url = URL(listImage[position].src.medium).openStream()
                val bmp = BitmapFactory.decodeStream(url)

                handler.post {
                    holder.itemView.img.setImageBitmap(bmp)
                }
            } catch (e: Exception) {
                Log.e("onBindViewHolder", e.message.toString())
            }
        }

        holder.itemView.cbCheck.isChecked = false

        holder.itemView.cbCheck.setOnCheckedChangeListener { p0, p1 ->  mOnItemListener.onCheck(listImage[position],p1)}

        holder.itemView.img.setOnClickListener {
            mOnItemListener.onItemClick(position)
        }

    }

    override fun getItemCount(): Int {
        return listImage.size
    }

}