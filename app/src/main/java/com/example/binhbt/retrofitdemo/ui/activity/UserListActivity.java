package com.example.binhbt.retrofitdemo.ui.activity;

import android.os.Bundle;
import android.view.Window;

import com.example.binhbt.retrofitdemo.R;
import com.example.binhbt.retrofitdemo.ui.fragment.UserListFragment;

/**
 * Created by binhbt on 6/8/2016.
 */
public class UserListActivity extends BaseActivity{
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_layout);
        if (savedInstanceState == null) {
            addFragment(R.id.fragmentContainer, new UserListFragment());
        }
    }


}
