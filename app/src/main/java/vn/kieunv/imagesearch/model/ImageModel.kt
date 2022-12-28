package vn.kieunv.imagesearch.model

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class ImageModel(
    var total_results: Int,
    var photos: MutableList<Photo>
)

data class Photo(
    var id: Int,
    var photographer: String?,
    var src: Src
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readSerializable() as Src
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeInt(id)
        p0.writeString(photographer)
        p0.writeSerializable(src)
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }

}

data class Src(
    var medium: String
): Serializable