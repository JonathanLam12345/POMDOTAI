package com.example.ai.jlam.pomdotai;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import java.util.List;

//import com.voice.controller.client.R;

public class MyCustomAdapter extends BaseAdapter
{
    private List<RowItem> RowItems;
    private LayoutInflater mLayoutInflater;
    UserInfoDatabase userInfoDatabase;

    Context context;

    public MyCustomAdapter(Context context, List<RowItem> arrayList)
    {
        RowItems = arrayList;
        this.context = context;
        // get the layout inflater
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        // getCount() represents how many items are in the list
        return RowItems.size();
    }

    @Override
    public Object getItem(int position)
    {
        return RowItems.get(position);
    }

    @Override
    // get the position id of the item from the list
    public long getItemId(int i)
    {
        return 0;
    }

    /*private view holder class*/
    private class ViewHolder
    {
        ImageView imageView_displayPic;  //POM image, or Current user image.
        TextView textView_message_bot;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        ViewHolder holder = null;
        userInfoDatabase = new UserInfoDatabase(this.context);


        // check to see if the reused view is null or not, if is not null then
        // reuse it
        if (view == null)
        {
            view = mLayoutInflater.inflate(R.layout.bot_list_item, null);
            holder = new ViewHolder();
            holder.textView_message_bot = (TextView) view.findViewById(R.id.textView_message_bot);
            holder.imageView_displayPic = (ImageView) view.findViewById(R.id.imageView_displayPic);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        RowItem RowItem = (RowItem) getItem(position);

//pom image ID: 2130837673

        holder.textView_message_bot.setText(RowItem.getMessage());

        if (RowItem.getImageType().equals("POM"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.ic_launcher);
        }
        //current user
        else if (RowItem.getImageType().equals("user"))
        {
            Log.d("user image plz: ", userInfoDatabase.getProfileImageURL());

            if (userInfoDatabase.getProfileImageURL().contains("."))
            {
                Glide.with(view.getContext())
                        .load(userInfoDatabase.getProfileImageURL())
                        .into(holder.imageView_displayPic);
            }
            else
            {
                String image64;
                image64 = userInfoDatabase.getProfileImageURL();
                byte[] decodedString = Base64.decode(image64, Base64.NO_WRAP);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                holder.imageView_displayPic.setImageBitmap(decodedByte); //display the image.
            }
        }


        // this method must return the view corresponding to the data at the
        // specified position.
        return view;

    }
}