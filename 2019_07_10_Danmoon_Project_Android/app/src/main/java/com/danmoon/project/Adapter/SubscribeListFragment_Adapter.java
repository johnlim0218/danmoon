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

import com.danmoon.project.DTO.SubscribeDto;
import com.danmoon.project.R;
import com.danmoon.project.Fragment.SubscribeListFragment;
import com.danmoon.project.Fragment.User_PostlistFragment;

import java.util.ArrayList;

public class SubscribeListFragment_Adapter extends RecyclerView.Adapter<SubscribeListFragment_Adapter.ViewHolder>{

    private Context context;
    private ArrayList<SubscribeDto> arrForSubscribeList = new ArrayList<>();
    private FragmentManager fragmentManager;
    private SubscribeListFragment subscribeListFragment;
    private User_PostlistFragment materialSearchMemInfoFragment;

    public SubscribeListFragment_Adapter(Context context, ArrayList<SubscribeDto> arrForSubscribeList){
        this.context = context;
        this.arrForSubscribeList = arrForSubscribeList;
    }

    @NonNull
    @Override
    public SubscribeListFragment_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscribe_list_fragment_list, parent, false);

        SubscribeListFragment_Adapter.ViewHolder viewHolder = new SubscribeListFragment_Adapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubscribeListFragment_Adapter.ViewHolder viewHolder, final int position) {
        viewHolder.subscribeListNick.setText(arrForSubscribeList.get(position).getMem_nickname());
        viewHolder.subscribeListCountpost.setText(arrForSubscribeList.get(position).getMem_postcount()+"개의 글 작성");

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int memIdx = arrForSubscribeList.get(position).getS_subsmemidx();
                String memNickname = arrForSubscribeList.get(position).getMem_nickname();

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
        if(arrForSubscribeList == null) {
            result = 0;
        } else if (arrForSubscribeList != null){
            result = arrForSubscribeList.size();
        }

        return result;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        LinearLayout subscribeListInner;
        TextView subscribeListNick;
        TextView subscribeListCountpost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;

            subscribeListInner = (LinearLayout)itemView.findViewById(R.id.subscribe_list_mem_inner);
            subscribeListNick = (TextView)itemView.findViewById(R.id.subscribe_list_mem_name);
            subscribeListCountpost = (TextView)itemView.findViewById(R.id.subscribe_list_countpost);
        }
    }}
