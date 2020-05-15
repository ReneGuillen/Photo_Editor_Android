package com.software.ProfileFit.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.yalantis.ucrop.UCropActivity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();
    private final List<Method> methodList = new ArrayList<>();
    private final List<String> methodTitleList = new ArrayList<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public  void addFragment(Fragment fragment,String title)
    {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    public  void replaceFragment(Fragment fragment, Fragment fragment2)
    {
        fragmentList.remove(fragment);
        fragmentList.add(fragment2);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}
