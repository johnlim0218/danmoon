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
import com.danmoon.project.Fragment.User_PostFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class User_PostlistFragment_Adapter extends RecyclerView.Adapter<User_PostlistFragment_Adapter.ViewHolder>{

    private Context context;
    static int selected_item = 0;
    private ArrayList<PostDto> arrForPostDto = new ArrayList<>();
    private FragmentManager fragmentManager;
    private User_PostFragment materialSearchMemPostFragment;


    public User_PostlistFragment_Adapter(Context context){
        this.context = context;

    }

    public User_PostlistFragment_Adapter(Context context, ArrayList<PostDto> arrForPostDto){
        this.context = context;
        this.arrForPostDto = arrForPostDto;
    }

    @NonNull
    @Override
    public User_PostlistFragment_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.material_search_mem_info_fragment_list, parent, false);

        User_PostlistFragment_Adapter.ViewHolder viewHolder = new User_PostlistFragment_Adapter.ViewHolder(view);

        return  viewHolder;


    }

    @Override
    public void onBindViewHolder(@NonNull final User_PostlistFragment_Adapter.ViewHolder viewHolder, final int position) {

        if(arrForPostDto.get(0).getP_material() != null){
            // 글감
            viewHolder.listPostMaterial.setText(arrForPostDto.get(position).getP_material());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy년MM월dd일 HH시mm분");
            Date writeTime = new Date();
            writeTime.setTime(Long.parseLong(arrForPostDto.get(position).getP_regdate()));

            // 글을 쓴 시간
            viewHolder.listPostRegDate.setText(sdf.format(writeTime));

            // 다른 사용자가 쓴 글 클릭리스너
            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 글번호
                    int p_Idx_Pk = arrForPostDto.get(position).getP_idx_pk();

                    materialSearchMemPostFragment = User_PostFragment.newInstance();
                    fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putInt("p_idx", p_Idx_Pk);
                    materialSearchMemPostFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.post_mid_layout, materialSearchMemPostFragment, "userPostFragment");
                    fragmentTransaction.commit();

                }
            });

        } else if(arrForPostDto.get(0).getP_material() == null){
            viewHolder.listPostMaterial.setText("작성한 글이 없습니다.");
            viewHolder.listPostRegDate.setText("");
        } else if(arrForPostDto.size() == 0){
            viewHolder.listPostMaterial.setText("구독해주세요.");
            viewHolder.listPostRegDate.setText("");
        }




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

            mylistInnerLayout = (LinearLayout)itemView.findViewById(R.id.userlist_post_inner);
            listPostMaterial = (TextView)itemView.findViewById(R.id.userlist_post_material);
            listPostRegDate = (TextView)itemView.findViewById(R.id.userlist_post_regdate);
        }


    }
}
