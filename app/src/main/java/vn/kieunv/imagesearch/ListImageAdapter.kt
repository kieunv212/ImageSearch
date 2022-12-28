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
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_image.view.*
import vn.kieunv.imagesearch.model.ImageModel
import vn.kieunv.imagesearch.model.Photo
import java.net.URL
import java.util.concurrent.Executors


class ListImageAdapter(private val listImage: MutableList<Photo>): RecyclerView.Adapter<ListImageAdapter.ViewHolder>() {

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
                val url = URL(listImage[position].src.original).openStream()
                val bmp = BitmapFactory.decodeStream(url)

                handler.post {
                    holder.itemView.img.setImageBitmap(bmp)
                }
            } catch (e: Exception) {
                Log.e("onBindViewHolder", e.message.toString())
            }
        }

    }

    override fun getItemCount(): Int {
        return listImage.size
    }

    class loadImage() : AsyncTask<ImageView, Void, Bitmap>() {
        override fun doInBackground(vararg p0: ImageView?): Bitmap {
            TODO("Not yet implemented")
        }

        override fun onPostExecute(result: Bitmap?) {
            super.onPostExecute(result)
        }
    }
}