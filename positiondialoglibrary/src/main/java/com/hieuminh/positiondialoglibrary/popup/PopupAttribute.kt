package com.hieuminh.positiondialoglibrary.popup

data class PopupAttribute(
    var width: Int? = null,
    var height: Int? = null,
    var horizontalGravity: HorizontalGravity? = null,
    var partitionX: Int = 0,
    var partitionY: Int = 0,
) {
    companion object {
        val DEFAULT = PopupAttribute()
    }
}
