package com.danmoon.project.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.danmoon.project.R;

import java.util.ArrayList;

public class Material_DrawerListView_Adapter extends BaseAdapter {

    ArrayList<DrawerListViewDto> arrForDrawer = new ArrayList<>();
    String[] menuTitle = {"내 공간","내가 쓴 글", "구독"};
    int[] menuIcon = {R.drawable.ic_round_person_24px, R.drawable.ic_baseline_view_list_24px, R.drawable.ic_subscribe};
    public Material_DrawerListView_Adapter(Context context){

        for(int i = 0; i < menuTitle.length; i++) {
            Drawable drawerIcon = ContextCompat.getDrawable(context, menuIcon[i]);
            DrawerListViewDto drawerListViewDto = new DrawerListViewDto();
            drawerListViewDto.setTitle(menuTitle[i]);
            drawerListViewDto.setIcon(drawerIcon);
            arrForDrawer.add(drawerListViewDto);
        }
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final int pos = position;
        final Context context = viewGroup.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.drawerlistview_item, viewGroup, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView imageImageView = (ImageView) view.findViewById(R.id.drawer_menu_image);
        TextView titleTextView = (TextView) view.findViewById(R.id.drawer_menu_title) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        DrawerListViewDto listViewItem = arrForDrawer.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        imageImageView.setImageDrawable(listViewItem.getIcon());
        titleTextView.setText(listViewItem.getTitle());

        return view;
    }
    @Override
    public Object getItem(int position) {
        return arrForDrawer.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        int drawerSize = arrForDrawer.size();
        return drawerSize;
    }

    class DrawerListViewDto {

        private Drawable icon;
        private String title;

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
