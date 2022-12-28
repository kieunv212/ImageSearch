package vn.kieunv.imagesearch.model

data class ImageModel(
    var total_results: Int,
    var photos: MutableList<Photo>
)

data class Photo(
    var id: Int,
    var photographer: String,
    var src: Src
)

data class Src(
    var original: String
)