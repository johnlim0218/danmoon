package com.danmoon.project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.danmoon.project.Adapter.Material_DrawerListView_Adapter;
import com.danmoon.project.DTO.MaterialDto;
import com.danmoon.project.DTO.MemberDto;
import com.danmoon.project.Fragment.Material_MidLayoutFragment;
import com.danmoon.project.Fragment.NewPostFragment;
import com.danmoon.project.Fragment.PostlistFragment;
import com.danmoon.project.Fragment.My_PostFragment;
import com.danmoon.project.Fragment.My_PostlistFragment;
import com.danmoon.project.Fragment.SubscribeListFragment;
import com.danmoon.project.Fragment.Temp_PostFragment;
import com.danmoon.project.Fragment.Temp_PostlistFragment;
import com.danmoon.project.R;
import com.danmoon.project.Saver.LoggedInInfoChecker;
import com.danmoon.project.Saver.TemporarySave;
import com.danmoon.project.ServerCommunicator.ServerCommunicationForMaterial;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MaterialActivity extends AppCompatActivity {

    static final String getMaterialURL = "/danmoon/getMaterial";

    final public static int POSTLIST_MODE = 0;
    final public static int MY_POSTLIST_MODE = 1;
    final public static int NEW_POST_MODE = 2;
    final public static int READ_MY_POST_MODE = 3;
    final public static int UPDATE_POST_MODE = 4;
    final public static int TEMP_POSTLIST_MODE = 5;
    final public static int TEMP_READ_MODE = 6;
    final public static int TEMP_UPDATE_MODE = 7;

    final public static int SEARCH_MODE = 8;
    final public static int SEARCH_MEM_MODE = 9;
    final public static int SEARCH_MEM_POSTLIST_MODE = 10;
    final public static int SEARCH_MEM_READ_MODE = 11;

    final public static int SUBSCRIBING_MODE = 12;

    public static int MODE;

    final public static int POST_NOT_PUBLIC = 0;
    final public static int POST_PUBLIC = 1;
    public static int PUBLIC = 0;

    MaterialDto materialDto = new MaterialDto();
    MemberDto memberDto = new MemberDto();

    private Material_MidLayoutFragment materialMidLayoutFragment = Material_MidLayoutFragment.newInstance();
    private PostlistFragment materialPostlistFragment = PostlistFragment.newInstance();
    private NewPostFragment materialPostFragment = NewPostFragment.newInstance();
    private My_PostFragment myPostFragment;
    private Temp_PostFragment tempPostFragment;
    final FragmentManager fragmentManager = getSupportFragmentManager();

    static boolean flagWritePost = false;

    private CollapsingToolbarLayout collapsingToolbar;
    private EditText appBarSearchWord;
    private CheckBox userSubscribeCheckBox;

    private DrawerLayout postDrawerlayout;
    private ActionBarDrawerToggle postDrawerToggle;
    private View drawerMenuHeaderLayout;
    private TextView drawerMenuNickname;
    private TextView drawerMenuEmail;
    private AppBarLayout appBarLayout;
    private BottomAppBar postBar;

    private ImageButton deletePostButton;
    private Toolbar postBottomToolBar;
    private FloatingActionButton postFAB;

//    private NestedScrollView postMidLayout;

    private ViewPager viewPager;

    private TemporarySave temporarySave = new TemporarySave(this);
    public static String fileName;

    public static int mem_idx; // 로그인된 사용자의 idx
    public static int post_idx;
    public static int material_idx;
    public static String material_str;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.post_menu_bottom, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        temporarySave.createTable();

        MODE = POSTLIST_MODE; // MODE 초기화
        PUBLIC = POST_NOT_PUBLIC; // 공개 여부 초기화(비공개)

        // 로그인 정보
        LoggedInInfoChecker loggedInInfoChecker = new LoggedInInfoChecker(this);
        memberDto = loggedInInfoChecker.loggedInInfoReader();
        mem_idx = memberDto.getIdx();

        // 전체 레이아웃 요소 초기화
        // 리소스 가져오기
        intitLayoutResource();

        // 상단툴바 폰트 설정
        // 현재 글감 서버에서 불러오기
        initHeadToolbar();

        // 구독하기버튼 초기화
        initSubscribeButton();

        // 드로어 메뉴(햄버거 메뉴) 초기화
        initDrawer();

        // activity_material의 중간 NestedScrollView 에 최초 삽입되는 Material_Postlist_Fragment (다른 사람 글 리스트) 세팅
        // 글쓰기 버튼을 누르면 Material_Postlist_Fragment가 NewPostFragment(글쓰기)로 바뀐다.
        initMaterialActivityMidLayout();

        // 글쓰기 하단 메뉴바 초기화
        setSupportActionBar(postBottomToolBar);
        postBottomToolBar.setVisibility(View.INVISIBLE);

        // FloatingActionButton을 눌렀을 때의 이벤트 설정
        postFAB.setOnLongClickListener(new Button.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    System.out.println("MODE");
                    System.out.println(MODE);
                    // 현재 POSTLIST_MODE일 때
                    // POSTLIST_MODE란 현재 글감에 대해 다른 사용자들이 작성한 글 목록을 의미
                    // 로그인하고 최초로 보는 페이지(fragment)
                    // 글쓰기 버튼을 누르면 (longclick) NEW_POST_MODE(글쓰기 모드)로 바뀐다
                    if(MODE == POSTLIST_MODE){

                        PUBLIC = POST_NOT_PUBLIC; // 공개 여부 초기화(비공개)

                        moveFABAnimationCenterToEnd();

                        // Material_PostlistFragment를 Material_PostFragment로 전환
                        initMaterialActivityMidLayoutForWritePost();


                    } else if(MODE == NEW_POST_MODE) {

                        moveFABAnimationEndToCenter();

                        // 글감과
                        // 사용자가 Material_PostFragment에 입력한 텍스트 내용을 가져온다.
                        String postMaterialStr = collapsingToolbar.getTitle().toString();

                        // 저장하기
                        materialPostFragment = (NewPostFragment) getSupportFragmentManager().findFragmentByTag("writeNewPostFragment");
                        materialPostFragment.writingPost(memberDto, materialDto.getMaterial_idx_pk(), material_str);

                        // activity_material의 중간 NestedScrollView 에 최초 삽입되는 Material_Postlist_Fragment (다른 사람 글 리스트) 세팅
                        // 글쓰기 버튼을 누르면 Material_Postlist_Fragment가 NewPostFragment(글쓰기)로 바뀐다.
                        initMaterialActivityMidLayout();


                    } else if (MODE == MY_POSTLIST_MODE) {

                    } else if (MODE == READ_MY_POST_MODE || MODE == UPDATE_POST_MODE){
                        myPostFragment = (My_PostFragment)getSupportFragmentManager().findFragmentByTag("myPostFragment");

                        if(MODE == READ_MY_POST_MODE){
                            MODE = UPDATE_POST_MODE;

                            moveFABAnimationCenterToEnd();
                            myPostFragment.makeEnabledContentEditText();

                        } else if(MODE == UPDATE_POST_MODE){
                            MODE = READ_MY_POST_MODE;

                            moveFABAnimationEndToCenter();
                            myPostFragment.makeUnenabledContentEditText();
                            myPostFragment.updateMyPost(); // 글 수정
                            deletePostButton.setVisibility(View.VISIBLE);

                        }
                    } else if (MODE == TEMP_POSTLIST_MODE){


                    } else if (MODE == TEMP_READ_MODE || MODE == TEMP_UPDATE_MODE){
                        tempPostFragment = (Temp_PostFragment)getSupportFragmentManager().findFragmentByTag("tempPostFragment");

                        if(MODE == TEMP_READ_MODE){
                            MODE = TEMP_UPDATE_MODE;

                            moveFABAnimationCenterToEnd();
                            tempPostFragment.makeEnabledContentEditText();

                        } else if(MODE == TEMP_UPDATE_MODE){
                            MODE = TEMP_READ_MODE;

                            moveFABAnimationEndToCenter();
                            tempPostFragment.makeUnenabledContentEditText();
                            tempPostFragment.updateTempPost(memberDto); // 글 수정

                        }
                    }

                    return false;
                }
            });
        }

    public void moveFABAnimationCenterToEnd(){
        if (postBar.getFabAlignmentMode() == BottomAppBar.FAB_ALIGNMENT_MODE_CENTER) {
            menuOnPost();

            // 글쓰기 상태에서 툴바 크기 상태
            appBarLayout.setExpanded(false);

            // FloatingActionButton 위치 변경
            postBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
            postFAB.setImageResource(R.drawable.ic_outline_check_24px);

            flagWritePost = true;
        }
    }
    public void moveFABAnimationEndToCenter(){
        if (postBar.getFabAlignmentMode() == BottomAppBar.FAB_ALIGNMENT_MODE_END) {
            menuOffPost();

            // 상단 툴바 상태 초기화
            appBarLayout.setExpanded(true);
            // FloatingActionButton 위치 변경
            postBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
            postFAB.setImageResource(R.drawable.ic_outline_edit_24px);

            flagWritePost = false;
        }
    }

    public void intitLayoutResource(){
        collapsingToolbar =(CollapsingToolbarLayout)findViewById(R.id.post_collapsingtoolbarlayout);
        appBarSearchWord = (EditText)findViewById(R.id.app_bar_search_word);
        userSubscribeCheckBox = (CheckBox)findViewById(R.id.user_subscribe_checkbox);

        appBarLayout = (AppBarLayout) findViewById(R.id.post_appbarlayout);
        postFAB = (FloatingActionButton) findViewById(R.id.post_fab);
        postBar = (BottomAppBar) findViewById(R.id.post_bar);
        postBottomToolBar = (Toolbar) findViewById(R.id.post_bottomtoolbar);
//        readPostBottomToolBar = (Toolbar) findViewById(R.id.read_post_bottomtoolbar);
        deletePostButton = (ImageButton)findViewById(R.id.delete_post_button);
        postDrawerlayout = (DrawerLayout)findViewById(R.id.post_drawerlayout);

//        postMidLayout =(NestedScrollView)findViewById(R.id.post_mid_layout);



    }

    public void initDrawer(){
        // 드로어 메뉴의 헤더 초기화
        // 사용자의 닉네임, 이메일 설정
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        NavigationView drawerNavigationView = (NavigationView)findViewById(R.id.drawer_navigationView);
        drawerMenuHeaderLayout = inflater.inflate(R.layout.drawer_header, null, false);
        drawerMenuEmail = (TextView)drawerMenuHeaderLayout.findViewById(R.id.drawer_menu_email);
        drawerMenuNickname = (TextView)drawerMenuHeaderLayout.findViewById(R.id.drawer_menu_nickname);
        drawerMenuEmail.setText(memberDto.getEmail());
        drawerMenuNickname.setText(memberDto.getNickname());
        drawerNavigationView.addHeaderView(drawerMenuHeaderLayout);

        Material_DrawerListView_Adapter postDrawerListviewAdapter = new Material_DrawerListView_Adapter(this);
        postDrawerToggle = new ActionBarDrawerToggle(this, postDrawerlayout, postBar, R.string.drawer_open, R.string.drawer_close){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                collapsingToolbar.setTitle(menuItem.getTitle());
                appBarLayout.setExpanded(false);
                postDrawerlayout.closeDrawers();

               menuItem.setChecked(true);
                deletePostButton.setVisibility(View.GONE);

                int menuItemId = menuItem.getItemId();


                switch(menuItemId){
                    case R.id.drawer_menu_home:

                        MODE = POSTLIST_MODE;

                        moveFABAnimationEndToCenter();
//                        appBarLayout.setExpanded(true);
                        initHeadToolbar();
                        initSubscribeButton();
                        initMaterialActivityMidLayout();
                        break;
                    case R.id.drawer_menu_myroom :
                        Toast.makeText(MaterialActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        moveFABAnimationEndToCenter();
                        initSubscribeButton();
                        initMaterialActivityMidLayoutForMyPostlist();
                        break;

                    case R.id.drawer_menu_subscribe :
                        moveFABAnimationEndToCenter();
                        Toast.makeText(MaterialActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        initSubscribeButton();
                        initMaterialActivityMidLayoutForSubscriblist();
                        break;
                    case R.id.drawer_menu_tempsave :
                        Toast.makeText(MaterialActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        moveFABAnimationEndToCenter();
                        initSubscribeButton();
                        initMaterialActivityMidLayoutForTempSavelist();
                        break;
                    case R.id.drawer_menu_setting :
                        Toast.makeText(MaterialActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        initSubscribeButton();
                        break;
                }


                return true;
            }
        });
    }

    public void initHeadToolbar(){
        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nanumgothicextrabold.ttf");
        collapsingToolbar.setCollapsedTitleTypeface(tf);
        collapsingToolbar.setExpandedTitleTypeface(tf);
        // 툴바 폰트 설정 끝

        try {
            ServerCommunicationForMaterial serverCommunicationForMaterial = new ServerCommunicationForMaterial();
            materialDto = serverCommunicationForMaterial.execute(getMaterialURL).get();
            material_str = materialDto.getMaterial_title();

            collapsingToolbar.setTitle(material_str);
            appBarLayout.setExpanded(true);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public void initSubscribeButton(){

        userSubscribeCheckBox.setVisibility(View.GONE);
        userSubscribeCheckBox.setSelected(false);
    }

    public void initMaterialActivityMidLayout(){
        MODE = POSTLIST_MODE;
        material_idx = materialDto.getMaterial_idx_pk();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.post_mid_layout, materialMidLayoutFragment, "midFragment");
        fragmentTransaction.commit();
    }

    public void initMaterialActivityMidLayoutForWritePost(){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.post_mid_layout, materialPostFragment, "writeNewPostFragment");
        fragmentTransaction.commit();
    }

    public void initMaterialActivityMidLayoutForMyPostlist(){

        My_PostlistFragment myPostlistFragment = My_PostlistFragment.newInstance();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.post_mid_layout, myPostlistFragment, "myPostListFragment");
        fragmentTransaction.commit();
    }

    public void initMaterialActivityMidLayoutForSubscriblist(){

        SubscribeListFragment subscribeListFragment = SubscribeListFragment.newInstance();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.post_mid_layout, subscribeListFragment, "subscribeListFragment");
        fragmentTransaction.commit();
    }

    public void initMaterialActivityMidLayoutForTempSavelist(){
        Temp_PostlistFragment tempPostlistFragment = Temp_PostlistFragment.newInstance();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.post_mid_layout, tempPostlistFragment, "tempPostlistFragment");
        fragmentTransaction.commit();
    }

    public void menuOnPost(){

        // 글 작성 Fragment(NewPostFragment) 하단 메뉴를 보이게 한다.
        postBottomToolBar.setVisibility(View.VISIBLE);
//        readPostBottomToolBar.setVisibility(View.GONE);
        deletePostButton.setVisibility(View.GONE);

        MenuItem publicMenuItem = postBottomToolBar.getMenu().findItem(R.id.navigation_public);

        if(PUBLIC == POST_NOT_PUBLIC){
            publicMenuItem.setIcon(R.drawable.ic_outline_public_24px);

        } else if (PUBLIC == POST_PUBLIC){
            publicMenuItem.setIcon(R.drawable.baseline_outline_public_24px);

        }

        postBottomToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int itemClicked = item.getItemId();

                    switch(itemClicked) {
                        case R.id.navigation_public :
                            if(PUBLIC == POST_PUBLIC) {
                                PUBLIC = POST_NOT_PUBLIC;
                                item.setIcon(R.drawable.ic_outline_public_24px);
                                Toast.makeText(getApplicationContext(), "글이 비공개되었습니다.", Toast.LENGTH_SHORT).show();
                                break;
                            } else if(PUBLIC == POST_NOT_PUBLIC){
                                PUBLIC = POST_PUBLIC;
                                item.setIcon(R.drawable.baseline_outline_public_24px);
                                Toast.makeText(getApplicationContext(), "글이 공개되었습니다.", Toast.LENGTH_SHORT).show();
                                break;
                            }

                        case R.id.navigation_temporarysave :
                            Fragment fragment = fragmentManager.findFragmentById(R.id.post_mid_layout);
                            EditText contentEditText;
                            String strContentEditText = "";
                            if(MODE == NEW_POST_MODE) {
                                contentEditText = (EditText) fragment.getView().findViewById(R.id.post_content_text);
                                strContentEditText = contentEditText.getText().toString();
                                post_idx = 0;
                                material_idx = materialDto.getMaterial_idx_pk();
                            } else if(MODE == UPDATE_POST_MODE){
                                contentEditText = (EditText) fragment.getView().findViewById(R.id.mypost_content_text);
                                strContentEditText = contentEditText.getText().toString();
                            }
                            String strMaterial = collapsingToolbar.getTitle().toString();

                            if(!strContentEditText.equals("")) {

                                temporarySave.insertTempSaveData(post_idx, material_idx, strMaterial ,strContentEditText);
//                                temporarySave.temporarySaver(strContentEditText);
//                                Toast.makeText(getApplicationContext(), "임시저장되었습니다..", Toast.LENGTH_SHORT).show();
                            }

                            break;
                    }

                return false;
            }
        });
    }

    public void menuOffPost(){
        // 글 작성 Fragment(NewPostFragment) 하단 메뉴를 숨긴다.
        postBottomToolBar.setVisibility(View.GONE);
    }

}
