package com.danmoon.project.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.danmoon.project.DTO.PostDto;
import com.danmoon.project.Fragment.My_PostFragment;
import com.danmoon.project.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class My_PostlistFragment_Adapter extends RecyclerView.Adapter<My_PostlistFragment_Adapter.ViewHolder> {

    private Context context;
    static int selected_item = 0;
    private ArrayList<PostDto> arrForPostDto = new ArrayList<>();
    private FragmentManager fragmentManager;
    private My_PostFragment myPostFragment;

    public My_PostlistFragment_Adapter(Context context, ArrayList<PostDto> arrForPostDto){
        this.context = context;
        this.arrForPostDto = arrForPostDto;
    }

    @NonNull
    @Override
    public My_PostlistFragment_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_postlist_fragment_list, parent, false);

        My_PostlistFragment_Adapter.ViewHolder viewHolder = new My_PostlistFragment_Adapter.ViewHolder(view);

        return  viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final My_PostlistFragment_Adapter.ViewHolder viewHolder, final int position) {

        // 글감
        viewHolder.listPostMaterial.setText(arrForPostDto.get(position).getP_material());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일 HH시mm분");
        Date writeTime = new Date();
        writeTime.setTime(Long.parseLong(arrForPostDto.get(position).getP_regdate()));

        // 글을 쓴 시간
        viewHolder.listPostRegDate.setText(sdf.format(writeTime));

        // 내가 쓴 글 클릭리스너
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 글번호
                int p_Idx_Pk = arrForPostDto.get(position).getP_idx_pk();

                myPostFragment = My_PostFragment.newInstance();
                fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("p_idx", p_Idx_Pk);
                myPostFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.post_mid_layout, myPostFragment, "myPostFragment");
                fragmentTransaction.commit();

            }
        });


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

        public final View mView;

        LinearLayout mylistInnerLayout;
        TextView listPostMaterial;
        TextView listPostRegDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            mylistInnerLayout = (LinearLayout)itemView.findViewById(R.id.mylist_post_inner);
            listPostMaterial = (TextView)itemView.findViewById(R.id.mylist_post_material);
            listPostRegDate = (TextView)itemView.findViewById(R.id.mylist_post_regdate);
        }


    }
}
