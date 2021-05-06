package com.ssafy.recto;

import android.app.Application;

public class MyApplication extends Application {
    private Boolean cardPrivate;
    private Boolean cardOnlyPhoto;
    private String cardPhrases;
    private String cardDate;
    private String cardPassword;

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

    public String getCardPhrases()
    {
        return cardPhrases;
    }

    public void setCardPhrases(String cardPhrases)
    {
        this.cardPhrases = cardPhrases;
    }

    public String getCardDate()
    {
        return cardDate;
    }

    public void setCardDate(String cardDate)
    {
        this.cardDate = cardDate;
    }

    public String getCardPassword()
    {
        return cardPassword;
    }

    public void setCardPassword(String cardPassword)
    {
        this.cardPassword = cardPassword;
    }
}
