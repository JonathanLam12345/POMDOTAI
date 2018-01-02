package com.example.ai.jlam.pomdotai;

/**
 * Created by Jonathan Lam on 2017-07-25.
 */

public class RowItem
{
    private String imageType;
    private String message;



    public RowItem(String imageType, String message)
    {
        this.imageType = imageType;
        this.message = message;

    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getImageType()
    {
        return imageType;
    }

    public void setImageType(String imageType)
    {
        this.imageType = imageType;
    }

    @Override
    public String toString()
    {
        return message;
    }
}