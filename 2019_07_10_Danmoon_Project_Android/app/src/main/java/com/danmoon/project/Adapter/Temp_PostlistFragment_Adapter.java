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
import com.danmoon.project.R;
import com.danmoon.project.Fragment.Temp_PostFragment;

import java.util.ArrayList;


public class Temp_PostlistFragment_Adapter extends RecyclerView.Adapter<Temp_PostlistFragment_Adapter.ViewHolder> {

    private Context context;
    static int selected_item = 0;
    private ArrayList<PostDto> arrForPostDto = new ArrayList<>();
    private FragmentManager fragmentManager;
    private Temp_PostFragment tempPostFragment;

    public Temp_PostlistFragment_Adapter(Context context, ArrayList<PostDto> arrForPostDto){
        this.context = context;
        this.arrForPostDto = arrForPostDto;
    }

    @NonNull
    @Override
    public Temp_PostlistFragment_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_postlist_fragment_list, parent, false);

        Temp_PostlistFragment_Adapter.ViewHolder viewHolder = new Temp_PostlistFragment_Adapter.ViewHolder(view);

        return  viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final Temp_PostlistFragment_Adapter.ViewHolder viewHolder, final int position) {

        // 글감
        viewHolder.listPostMaterial.setText(arrForPostDto.get(position).getP_material());

         // 글을 쓴 시간
        viewHolder.listPostRegDate.setText(arrForPostDto.get(position).getP_regdate());

        // 내가 쓴 글 클릭리스너
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 글번호
                int tempsave_idx = arrForPostDto.get(position).getTempSave_idx();

                tempPostFragment = Temp_PostFragment.newInstance();
                fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("tempsave_idx", tempsave_idx);
                tempPostFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.post_mid_layout, tempPostFragment, "tempPostFragment");
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

            mylistInnerLayout = (LinearLayout)itemView.findViewById(R.id.templist_post_inner);
            listPostMaterial = (TextView)itemView.findViewById(R.id.templist_post_material);
            listPostRegDate = (TextView)itemView.findViewById(R.id.templist_post_regdate);
        }


    }
}
