package com.ssafy.recto.arcore

data class PhotoVO (
        var photo_seq : Int,
        var user_seq : Int,
        var photo_id : String,
        var photo_date : String,
        var photo_url : String,
        var video_url : String,
        var phrase : String,
        var photo_pwd : String,
        var design : Int,
        var publication : Boolean
)