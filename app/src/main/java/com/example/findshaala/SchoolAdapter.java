package com.example.findshaala;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SchoolAdapter extends RecyclerView.Adapter<SchoolAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Data> Mlist;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
    public SchoolAdapter(Context mContext, ArrayList<Data> mlist) {
        this.mContext = mContext;
        Mlist = mlist;
        Log.d("mList", ""+Mlist);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflator = LayoutInflater.from(mContext);
        View view =  layoutInflator.inflate(R.layout.rv_list, parent ,false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data schoolLtem  =  Mlist.get(position);
        ImageView image = holder.school_image;
        TextView school_name1, address1, contact1;
        school_name1 = holder.school_name;
        address1 = holder.school_address;
        contact1 = holder.school_contact;

        String school_name, address, contact, imgname, sid;
        getIp ip = new getIp();
        String del = ip.getIp();

        school_name = schoolLtem.getSchool_name();
        address = schoolLtem.getAddress();
        sid = schoolLtem.getSid();
        imgname = schoolLtem.getImgname();
        contact = schoolLtem.getContact();



        //Log.d("restaurant name is",""+restaurant_name);
        //Log.d("category is",""+category);
        //Log.d("image name is",""+imgname);

        String loc = ""+del+":8080/Images/"+imgname;
        //loc  = loc.replace('\\', '/');
        //Log.d("loc",loc);

        Picasso.get().load(""+loc).centerCrop().fit().error(R.drawable.ic_launcher_background).into(image, new Callback() {
            @Override
            public void onSuccess() {
                Log.d("success","LOADED");
            }

            @Override
            public void onError(Exception e) {
                Log.d("error",""+e);
            }
        });

        school_name1.setText(school_name);
        address1.setText(address);
        contact1.setText(contact);
    }


    @Override
    public int getItemCount() {
        return Mlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView school_image;
        TextView school_name, school_address, school_contact;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView) ;
            school_image = itemView.findViewById(R.id.school_image);
            school_name = itemView.findViewById(R.id.school_name);
            school_address = itemView.findViewById(R.id.school_address);
            school_contact =  itemView.findViewById(R.id.contact);


        }
    }
}
