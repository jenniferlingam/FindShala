package com.example.findshaala;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPageAdapter extends FragmentPagerAdapter {

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        First_Fragment first_fragment= new First_Fragment();

        Public Public_fragment= new Public();
        Private diet_fragment= new Private();
        International appointment_fragment= new International();

        position=position+1;
        if(position==1){
            Bundle bundle=new Bundle();
//            bundle.putString("message","Fragment :"+position);
            Public_fragment.setArguments(bundle);
            return Public_fragment;
        }else if(position==2){
            Bundle bundle=new Bundle();
//            bundle.putString("message","Fragment :"+position);
            diet_fragment.setArguments(bundle);
            return diet_fragment;
        }else{
            Bundle bundle=new Bundle();
//            bundle.putString("message","Fragment :"+position);
            appointment_fragment.setArguments(bundle);
            return appointment_fragment;
        }
        /*Bundle bundle=new Bundle();
        bundle.putString("message","Fragment :"+position);
        first_fragment.setArguments(bundle);
        return first_fragment;*/
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        position=position+1;

        switch (position) {
            case 1:
                return "Public";
            case 2:
                return "Private";
            case 3:
                return "International";
            default:
                return null;
        }
//        return "Fragment "+position;
    }
}