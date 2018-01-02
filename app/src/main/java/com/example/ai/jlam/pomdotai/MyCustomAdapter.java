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
import com.team.socero.soceroapp.Database.MessagingDatabase;
import com.team.socero.soceroapp.Database.UserInfoDatabase;
import com.team.socero.soceroapp.Discovery.Bot.RowItem;
import com.team.socero.soceroapp.R;

import java.util.List;

//import com.voice.controller.client.R;

public class MyCustomAdapter extends BaseAdapter
{
    private List<RowItem> RowItems;
    private LayoutInflater mLayoutInflater;
    UserInfoDatabase userInfoDatabase;
    MessagingDatabase messagingDatabase;
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
        messagingDatabase = new MessagingDatabase(this.context);

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
        //This means the type is the user ID. This is for 1 to 1 messaging.

        else if (RowItem.getImageType().equals("1"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_artsnculture);
        }
        else if (RowItem.getImageType().equals("2"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_careernbusiness);
        }
        else if (RowItem.getImageType().equals("3"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_carsnmotors);
        }
        else if (RowItem.getImageType().equals("4"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_cmntynenviro);
        }
        else if (RowItem.getImageType().equals("5"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_dancing);
        }
        else if (RowItem.getImageType().equals("6"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_education);
        }
        else if (RowItem.getImageType().equals("7"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_fashionnpretty);
        }
        else if (RowItem.getImageType().equals("8"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_fitness);
        }
        else if (RowItem.getImageType().equals("9"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_food);
        }
        else if (RowItem.getImageType().equals("10"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_games);
        }
        else if (RowItem.getImageType().equals("11"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_lgbt);
        }
        else if (RowItem.getImageType().equals("12"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_movenpolitics);
        }
        else if (RowItem.getImageType().equals("13"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_healthnbeing);
        }
        else if (RowItem.getImageType().equals("14"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_hobbies);
        }
        else if (RowItem.getImageType().equals("15"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_language);
        }
        else if (RowItem.getImageType().equals("16"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_lifestyle);
        }
        else if (RowItem.getImageType().equals("17"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_bookclubs);
        }
        else if (RowItem.getImageType().equals("18"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_moviesnfilm);
        }
        else if (RowItem.getImageType().equals("19"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_music);
        }
        else if (RowItem.getImageType().equals("20"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_newage);
        }
        else if (RowItem.getImageType().equals("21"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_outside);
        }
        else if (RowItem.getImageType().equals("22"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_paranormal);
        }
        else if (RowItem.getImageType().equals("23"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_parentsnfam);
        }
        else if (RowItem.getImageType().equals("24"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_pets);
        }
        else if (RowItem.getImageType().equals("25"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_photo);
        }
        else if (RowItem.getImageType().equals("26"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_religion);
        }
        else if (RowItem.getImageType().equals("27"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_scifi);
        }
        else if (RowItem.getImageType().equals("28"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_singles);
        }
        else if (RowItem.getImageType().equals("29"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_social);
        }
        else if (RowItem.getImageType().equals("30"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_sports);
        }
        else if (RowItem.getImageType().equals("31"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_support);
        }
        else if (RowItem.getImageType().equals("32"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_tech);
        }
        else if (RowItem.getImageType().equals("33"))
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.interests_writing);
        }


        else
        {
            holder.imageView_displayPic.setImageResource(R.mipmap.bot_user);
        }

        // this method must return the view corresponding to the data at the
        // specified position.
        return view;

    }
}