package cc.runyan.techo;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import cc.runyan.techo.adapter.RecordPagerAdapter;
import cc.runyan.techo.frag_record.BaseRecordFragment;
import cc.runyan.techo.frag_record.InComeFragment;
import cc.runyan.techo.frag_record.OutComeFragment;

public class RecordActivity extends BaseActivity {

    TabLayout tabLayout;

    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        applySystemBarInsets(findViewById(R.id.activity_record_root));

        // 查找组件
        viewPager2 = findViewById(R.id.activity_record_vp);
        tabLayout = findViewById(R.id.activity_record_tl);

        // 初始化顶部页面
        initPager();

    }

    public void initPager() {
        List<Fragment> fragmentList = new ArrayList<>();

        BaseRecordFragment outComeFragment = new OutComeFragment();
        BaseRecordFragment inComeFragment = new InComeFragment();
        fragmentList.add(outComeFragment);
        fragmentList.add(inComeFragment);

        // 将该适配器绑定到当前的 recordActivity 上
        RecordPagerAdapter recordPagerAdapter = new RecordPagerAdapter(this, fragmentList);

        // 设置适配器
        viewPager2.setAdapter(recordPagerAdapter);

        // tabLayout和viewPager 进行关联
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            private final String[] titles = new String[]{"支出", "收入"};

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(titles[position]);
            }
        }).attach();

    }

    public void onClick(View v) {
        if (v.getId() == R.id.activity_record_back) {
            finish(); // activity 结束并销毁
        }
    }
}