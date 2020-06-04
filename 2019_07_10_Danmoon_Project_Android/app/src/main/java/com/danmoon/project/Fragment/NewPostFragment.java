package com.danmoon.project.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.danmoon.project.DTO.MemberDto;
import com.danmoon.project.Activity.MaterialActivity;
import com.danmoon.project.R;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForPost;

import java.util.HashMap;
import java.util.Map;

public class NewPostFragment extends Fragment {

    static final String insertPostURL = "/danmoon/insertPost";

    private EditText postContentEditText;

    String postMaterialStr;
    String postContentStr;

    public static NewPostFragment newInstance() {
        return new NewPostFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MaterialActivity.MODE = MaterialActivity.NEW_POST_MODE;
        View view = inflater.inflate(R.layout.material_post_fragment, container, false);

//        Bitmap bitmap = backgroundPatternResize();
//        EditText editText = (EditText)view.findViewById(R.id.post_content_text);
//        editText.setBackground(new BitmapDrawable(bitmap));
        LinearLayout postWriteLayout = (LinearLayout)view.findViewById(R.id.post_write_layout);
//        postWriteLayout.setBackground(new BitmapDrawable(bitmap));


        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.danmoon_post_background, options);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        drawable.setTileModeY(Shader.TileMode.REPEAT);

//        postWriteLayout.setBackground(drawable);

//        backgroundPatternResize();


        postContentEditText = view.findViewById(R.id.post_content_text);
//        loadTemporarySave();
        return view;
    }

    public void writingPost(MemberDto memberDto, int materialIdx, String postMaterialStr){

        postContentStr = postContentEditText.getText().toString();

        String newPostPublic = "n";

        if (MaterialActivity.PUBLIC == MaterialActivity.POST_PUBLIC) {
            newPostPublic = "y";
        } else if(MaterialActivity.PUBLIC == MaterialActivity.POST_NOT_PUBLIC){
            newPostPublic = "n";
        }

        Map<String, Object> postData = new HashMap<>();
        postData.put("mem_idx", memberDto.getIdx());
        postData.put("material_idx", materialIdx);
        postData.put("material", postMaterialStr);
        postData.put("content", postContentStr);

        postData.put("public", newPostPublic);

        if (!postContentStr.equals("")) {
            ServerCommunicationForPost serverCommunicationForPost = new ServerCommunicationForPost(postData);
            serverCommunicationForPost.execute(insertPostURL);
        }
    }

//    public void loadTemporarySave(){
//
//        TemporarySave temporarySave = new TemporarySave(this.getActivity().getApplicationContext());
//        String content = temporarySave.temporarySaveLoader();
//
//        if(!content.equals("")){
//            Toast.makeText(this.getActivity().getApplicationContext(), "임시저장된 글입니다.", Toast.LENGTH_LONG).show();
//            postContentEditText.setText(content);
//        }
//
//    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

    // 일단 보류
    // 레이아웃에서 호출(화면에 줄긋기)
    static class LineEditText extends AppCompatEditText {
        private Rect mRect;
        private Paint mPaint;

        public LineEditText(Context context, AttributeSet attrs) {
            super(context, attrs);
            mRect = new Rect();
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mPaint.setColor(Color.BLUE);
        }

        @Override protected void onDraw(Canvas canvas) {
            int height = getHeight();
            int line_height = getLineHeight();
            int count = height / line_height;
            if (getLineCount() > count) count = getLineCount();
            Rect r = mRect; Paint paint = mPaint;
            int baseline = getLineBounds(0, r);
            for (int i = 0; i < count; i++) {
                canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
                baseline += getLineHeight();
                super.onDraw(canvas);
            }
        }
    }

    public void backgroundPatternResize(){

        // 현재 디스플레이의 크기를 구한다.
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int displayWidth = display.getWidth();
        int displayHeight = display.getHeight();

        // 배경으로 사용할 이미지 불러오기
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.danmoon_post_background, options);

//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.danmoon_post_background, options);
//
//        System.out.println(bitmap1.getWidth());
//        System.out.println(bitmap1.getHeight());

        // 화면 크기에 가장 근접하는 이미지의 리스케일 사이즈를 구한다.
        float widthScale = options.outWidth / displayWidth;
        float heightScale = options.outHeight / displayHeight;
        float scale = widthScale > heightScale ? widthScale : heightScale;

        System.out.println(scale);
        if (scale >= 8) {
            options.inSampleSize = 8;
        } else if (scale >= 4) {
            options.inSampleSize = 4;
        } else if (scale >= 2) {
            options.inSampleSize = 2;
        } else {
            options.inSampleSize = 1;
        }
        options.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.danmoon_post_background, options);
        System.out.println(bitmap.getWidth());
        System.out.println(bitmap.getHeight());

        BitmapFactory.Options optionss = new BitmapFactory.Options();
        options.inSampleSize = 2;

        Bitmap bitmap2 = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.post_background_pattern, optionss);
//        System.out.println(bitmap2.getWidth());
//        return bitmap;
    }
}

