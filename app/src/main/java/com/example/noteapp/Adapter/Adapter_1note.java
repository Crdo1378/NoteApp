package com.example.noteapp.Adapter;

import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapp.Object.NoteOO;
import com.example.noteapp.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;

public class Adapter_1note extends RecyclerView.Adapter<Adapter_1note.MyViewHolder>{
    private final ClickListener listener;
    private final ArrayList<NoteOO> list;
    private ArrayList<NoteOO> list_search, list_origin;

    public Adapter_1note(ArrayList<NoteOO> itemsList, ClickListener listener) {
        this.listener = listener;
        this.list = itemsList;
    }


    @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.for_1_note, parent, false), listener);
    }

    @Override public void onBindViewHolder(MyViewHolder holder, int position) {
        // bind layout and data etc..
        holder.tvNote.setText(list.get(position).getContentNote());
        holder.tvTitle.setText(list.get(position).getTitleNote());
        holder.layout.setBackgroundResource(list.get(position).getResoureBack());
        holder.tvNote.setTextColor(list.get(position).getColorText());
        holder.tvNote.setHintTextColor(list.get(position).getColorHint());
        holder.tvTitle.setTextColor(list.get(position).getColorText());
        holder.tvTitle.setHintTextColor(list.get(position).getColorHint());
        if(list.get(position).getTagNote() != null){
            holder.tvTag.setText(list.get(position).getTagNote());
            holder.tvTag.setBackgroundResource(R.drawable.bg_black);
            holder.tvTag.setTextColor(Color.WHITE);
            holder.tvTag.setVisibility(View.VISIBLE);
        }
    }

    @Override public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tvTitle, tvNote, tvTag;
        RelativeLayout layout;
        private WeakReference<ClickListener> listenerRef;

        public MyViewHolder(final View itemView, ClickListener listener) {
            super(itemView);

            listenerRef = new WeakReference<>(listener);
            tvTitle = itemView.findViewById(R.id.tvTitle_1note);
            tvNote = itemView.findViewById(R.id.tvNote_1note);
            layout = itemView.findViewById(R.id.layout_for_1note);
            tvTag = itemView.findViewById(R.id.tvTag_);


            tvNote.setOnClickListener(this);
            tvTitle.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        // onClick Listener for view
        @Override
        public void onClick(View v) {

            if (v.getId() == tvNote.getId()) {
              //  Toast.makeText(v.getContext(), "Clock Content" + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
            else if(v.getId() == tvTitle.getId()){
              //  Toast.makeText(v.getContext(), "Click Title" + getAdapterPosition(), Toast.LENGTH_SHORT).show();

            }
            else {
              //  Toast.makeText(v.getContext(), "item PRESSED = " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }

            listenerRef.get().onPositionClicked(getAdapterPosition());
        }


        //onLongClickListener for view
        @Override
        public boolean onLongClick(View v) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
            builder.setTitle("Hello Dialog")
                    .setMessage("LONG CLICK DIALOG WINDOW FOR ICON " + getAdapterPosition())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

            builder.create().show();
            listenerRef.get().onLongClicked(getAdapterPosition());
            return true;
        }
    }

    // Filter Class
    //Search toolbar
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        if(!list.isEmpty()){
            list_search = new ArrayList<>();
            list_search.addAll(list);
        }
        if(list_origin == null){
            list_origin = new ArrayList<>();
            list_origin.addAll(list_search);
        }
        list.clear();
        if (charText.length() == 0) {
            if(list_search.size() != list_origin.size()){
                list_search.clear();
                list_search.addAll(list_origin);
            }
            list.addAll(list_search);
        } else {
            for (NoteOO wp : list_search) {
                if (wp.getContentNote().toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}
