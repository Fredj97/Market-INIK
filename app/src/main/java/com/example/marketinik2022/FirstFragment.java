package com.example.marketinik2022;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FirstFragment extends Fragment {
    public BottomNavigationView bottomNavigationView;
    public FirstFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (getActivity() != null && getActivity() instanceof MainActivity)
            ((MainActivity) getActivity()).bottomNavigationView.setVisibility(View.GONE);
        return inflater.inflate(R.layout.fragment_first, container, false);
    }


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    //  private void clearBackStack(FragmentManager fragmentManager) {
    // if (fragmentManager.getBackStackEntryCount() > 0) {
    // FragmentManager.BackStackEntry entry = fragmentManager.getBackStackEntryAt(0);
    // fragmentManager.popBackStack(entry.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    //}
    //}

}
