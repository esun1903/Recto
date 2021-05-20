package com.ssafy.recto.arcore

import com.google.gson.annotations.SerializedName
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import java.io.Serializable

@NoArgsConstructor
@AllArgsConstructor
data class PhotoVO (
        @SerializedName("photo_seq")
        var photo_seq : Int? = 0,
        @SerializedName("user_uid")
        var user_uid : String? = "",
        @SerializedName("photo_id")
        var photo_id : String? = "",
        @SerializedName("photo_date")
        var photo_date : String? = "",
        @SerializedName("photo_url")
        var photo_url : String? = "",
        @SerializedName("video_url")
        var video_url : String? = "",
        @SerializedName("phrase")
        var phrase : String? = "",
        @SerializedName("photo_pwd")
        var photo_pwd : String? = "",
        @SerializedName("design")
        var design : Int? = 0

) : Serializable