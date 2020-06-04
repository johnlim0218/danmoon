package com.danmoon.project.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.danmoon.project.DTO.PostDto;
import com.danmoon.project.R;

import java.util.ArrayList;

public class PostlistFragment_Adapter extends RecyclerView.Adapter<PostlistFragment_Adapter.ViewHolder> {

    private ArrayList<PostDto> arrForPostDto = new ArrayList<>();

    public PostlistFragment_Adapter(ArrayList<PostDto> arrForPostDto){

        this.arrForPostDto = arrForPostDto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_postlist_fragment_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            viewHolder.listPostContent.setText(arrForPostDto.get(position).getP_content());
            viewHolder.listPostNickName.setText(arrForPostDto.get(position).getMem_nickname());
    }

    @Override
    public int getItemCount() {
        int result = 0;
        if(arrForPostDto == null) {
            result = 0;
        } else if (arrForPostDto != null){
            result = arrForPostDto.size();
        }

        return result;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView listPostContent;
        TextView listPostNickName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            listPostContent = (TextView)itemView.findViewById(R.id.list_post_content);
            listPostNickName = (TextView)itemView.findViewById(R.id.list_post_nickname);
        }
    }
}
