package com.example.test;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class FirstFragment<i> extends Fragment {


    private Object listener;
    private BottomNavigationView bottomNavigationView;
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
    public void onDetach() {
        super.onDetach();
        this.listener = null;
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