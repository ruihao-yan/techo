package cc.runyan.techo.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class RecordPagerAdapter extends FragmentStateAdapter {

    List<Fragment> fragmentList;

    public RecordPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }

    // 从index为position中获取fragment
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }
    // 获取fragment的长度
    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

}
