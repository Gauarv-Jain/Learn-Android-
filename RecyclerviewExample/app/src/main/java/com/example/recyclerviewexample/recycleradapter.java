package com.example.recyclerviewexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class recycleradapter extends RecyclerView.Adapter<recycleradapter.Viewholder>{

    ArrayList<String> courcename = new ArrayList<>();
    ArrayList<String> courceexplination = new ArrayList<>();
    ArrayList<Integer> pictures = new ArrayList<>();
    Context context;

    public recycleradapter(ArrayList<String> courcename, ArrayList<String> courceexplination, ArrayList<Integer> pictures, Context context) {
        this.courcename = courcename;
        this.courceexplination = courceexplination;
        this.pictures = pictures;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycle, parent, false);
        Viewholder viewHolder = new Viewholder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.image.setImageResource(pictures.get(position));
        holder.courcename.setText(courcename.get(position));
        holder.courceexplination.setText(courceexplination.get(position));

        holder.parentlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "U selected cource " + courcename.get(position), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return courcename.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView courcename;
        TextView courceexplination;
        LinearLayout parentlayout;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image);
            courcename = itemView.findViewById(R.id.textViewname);
            courceexplination = itemView.findViewById(R.id.textViewexplination);
            parentlayout = itemView.findViewById(R.id.parent);
        }
    }
}
