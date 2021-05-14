package com.ssafy.recto.arcore

import lombok.NoArgsConstructor

@NoArgsConstructor
data class PhotoVO (
        var photo_seq : Int = 0,
        var user_seq : String = "",
        var photo_id : String = "",
        var photo_date : String = "",
        var photo_url : String = "",
        var video_url : String = "",
        var phrase : String = "",
        var photo_pwd : String = "",
        var design : Int = 0,
        var publication : Boolean = false
){

//    constructor(user_seq: String, photo_id: String, str: String, str2: String, cardPassword: String, cardDesign: Int, cardPublic: Boolean) : this() {
//    }
}