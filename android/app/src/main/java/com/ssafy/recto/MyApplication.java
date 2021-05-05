package com.ssafy.recto;

import android.app.Application;

public class MyApplication extends Application {
    private Boolean cardPrivate;
    private Boolean cardOnlyPhoto;

    public Boolean getCardPrivate()
    {
        return cardPrivate;
    }

    public void setCardPrivate(Boolean cardPrivate)
    {
        this.cardPrivate = cardPrivate;
    }

    public Boolean getCardOnlyPhoto()
    {
        return cardOnlyPhoto;
    }

    public void setCardOnlyPhoto(Boolean cardOnlyPhoto)
    {
        this.cardOnlyPhoto = cardOnlyPhoto;
    }
}
