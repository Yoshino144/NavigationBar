package com.pc.ks;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.pc.ks.Adapter.MainTabFragmentPagerAdapter;
import com.pc.ks.Fragment.BlankFragment_me;
import com.pc.ks.Fragment.BlankFragment_time;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private long exitTime = 0;
    private ViewPager mViewPager;
    private RadioGroup mTabRadioGroup;
    private List<Fragment> mFragments;
    private FragmentPagerAdapter mAdapter;

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTabView();  //注册底部菜单
    }

    private void initTabView() {
        // find view
        mViewPager = findViewById(R.id.main_fragment_vp);
        mTabRadioGroup = findViewById(R.id.main_tabs);
        //Icon size
        RadioButton main_tab_time = findViewById(R.id.main_tab_time);
        Drawable drawable_time = getResources().getDrawable(R.drawable.main_tab_selector_time);
        drawable_time.setBounds(0, 0, 60, 60);
        main_tab_time.setCompoundDrawables(null, drawable_time, null, null);
        RadioButton main_tab_me = findViewById(R.id.main_tab_me);
        Drawable drawable_me = getResources().getDrawable(R.drawable.main_tab_selector_me);
        drawable_me.setBounds(0, 0, 60, 60);
        main_tab_me.setCompoundDrawables(null, drawable_me, null, null);
        // fragment
        mFragments = new ArrayList<>(2);
        mFragments.add(BlankFragment_time.newInstance("学习","1"));
        mFragments.add(BlankFragment_me.newInstance("我的","2"));
        // Adapter
        mAdapter = new MainTabFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(2);
        // Listener
        mViewPager.addOnPageChangeListener(mPageChangeListener);
        mTabRadioGroup.setOnCheckedChangeListener(mOnCheckedChangeListener);
    }

    @Override //双击退出
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            RadioButton radioButton = (RadioButton) mTabRadioGroup.getChildAt(position);
            radioButton.setChecked(true);
            Log.d("当前页数", String.valueOf(position));
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            for (int i = 0; i < group.getChildCount(); i++) {
                if (group.getChildAt(i).getId() == checkedId) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    };

}
