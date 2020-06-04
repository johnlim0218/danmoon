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


import com.danmoon.project.DTO.MemberDto;
import com.danmoon.project.R;
import com.danmoon.project.Fragment.User_PostlistFragment;

import java.util.ArrayList;

public class Search_MemFragment_Adapter extends RecyclerView.Adapter<Search_MemFragment_Adapter.ViewHolder>{

    private Context context;
    private ArrayList<MemberDto> arrForSearchMem = new ArrayList<>();
    private FragmentManager fragmentManager;
    private User_PostlistFragment materialSearchMemInfoFragment;

    public Search_MemFragment_Adapter(Context context, ArrayList<MemberDto> arrForSearchMem){
        this.context = context;
        this.arrForSearchMem = arrForSearchMem;
    }

    @NonNull
    @Override
    public Search_MemFragment_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_search_mem_fragment_list, parent, false);

        Search_MemFragment_Adapter.ViewHolder viewHolder = new Search_MemFragment_Adapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Search_MemFragment_Adapter.ViewHolder viewHolder, final int position) {
        viewHolder.searchMemNick.setText(arrForSearchMem.get(position).getNickname());
        viewHolder.searchMemEmail.setText(arrForSearchMem.get(position).getEmail());
        viewHolder.searchMemCountpost.setText(arrForSearchMem.get(position).getCountpost()+"개의 글 작성");

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int memIdx = arrForSearchMem.get(position).getIdx();
                String memNickname = arrForSearchMem.get(position).getNickname();

                materialSearchMemInfoFragment = User_PostlistFragment.newInstance();
                fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("mem_idx", memIdx);
                bundle.putString("mem_nickname", memNickname);

                materialSearchMemInfoFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.post_mid_layout, materialSearchMemInfoFragment, "searchMemInfoFragment");
                fragmentTransaction.commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        int result = 0;
        if(arrForSearchMem == null) {
            result = 0;
        } else if (arrForSearchMem != null){
            result = arrForSearchMem.size();
        }

        return result;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        LinearLayout searchMemInner;
        TextView searchMemNick;
        TextView searchMemEmail;
        TextView searchMemCountpost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            searchMemInner = (LinearLayout)itemView.findViewById(R.id.search_mem_inner);
            searchMemNick = (TextView)itemView.findViewById(R.id.search_mem_name);
            searchMemEmail = (TextView)itemView.findViewById(R.id.search_mem_email);
            searchMemCountpost = (TextView)itemView.findViewById(R.id.search_mem_countpost);
        }
    }
}
