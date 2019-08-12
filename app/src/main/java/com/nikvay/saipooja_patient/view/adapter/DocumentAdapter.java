package com.nikvay.saipooja_patient.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikvay.saipooja_patient.R;
import com.nikvay.saipooja_patient.apiCallCommen.BaseApi;
import com.nikvay.saipooja_patient.model.DocumentModel;
import com.nikvay.saipooja_patient.view.activity.DocumentPhotoActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DocumentAdapter  extends RecyclerView.Adapter<DocumentAdapter.MyViewHolder> {


    Context context;
    ArrayList<DocumentModel> doctorModelArrayList;
    String url;
    private OnItemClickListener listener;

    public DocumentAdapter(DocumentPhotoActivity activity, String url,
                           ArrayList<DocumentModel> doctorModelArrayList)
    {
        this.context=activity;
        this.url=url;
        this.doctorModelArrayList=doctorModelArrayList;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_item_photo_gallery_adpater,parent,false);
        return new DocumentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position)
    {
        final DocumentModel documentModel=doctorModelArrayList.get(position);

        String imageTitle=documentModel.getTitle();
        String imageName=documentModel.getDocument_name();

        String imageUrl=url+imageName;
        final String finalImageUrl = BaseApi.BASE_URL + imageUrl;
        Picasso.get()
                .load(finalImageUrl)
                .into(holder.imageView);

        holder.textView.setText(imageTitle);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onAdapterClick(documentModel, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return doctorModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iv_gallery_image);
            textView=itemView.findViewById(R.id.txt_gallery_image_title);

        }
    }

    // ============================================
    public interface OnItemClickListener {
        void onAdapterClick(DocumentModel documentModel, int position);
    }

    public void setOnItemClickListener(DocumentAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }


}
