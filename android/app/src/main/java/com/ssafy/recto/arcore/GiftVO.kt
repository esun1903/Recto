package com.ssafy.recto.arcore

import lombok.NoArgsConstructor

@NoArgsConstructor
data class GiftVO(
        var gift_seq: Int = 0,
        var gift_from: String? = "",
        var photo_seq: Int? = 0,
        var gift_to: String? = "",
        var photo_id: String? = "",
        var photo_url: String? = "",
        var video_url: String? = "",
        var phrase: String? = "",
        var photo_pwd: String? = "",
        var design: Int? = 0,
        var publication: Boolean? = false
){

    constructor(gift_from: String?, photo_seq: Int?, gift_to: String?, photo_id: String?, photo_url: String?, video_url: String?, phrase: String?, photo_pwd: String?, design: Int?, publication: Boolean?) : this() {
        this.gift_from = gift_from
        this.photo_seq = photo_seq
        this.gift_to = gift_to
        this.photo_id = photo_id
        this.photo_url = photo_url
        this.video_url = video_url
        this.phrase = phrase
        this.photo_pwd = photo_pwd
        this.design = design
        this.publication = publication
    }

}