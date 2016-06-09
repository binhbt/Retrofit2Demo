package com.example.binhbt.retrofitdemo.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.binhbt.retrofitdemo.R;
import com.example.binhbt.retrofitdemo.adapter.UsersAdapter;
import com.example.binhbt.retrofitdemo.adapter.UsersLayoutManager;
import com.example.binhbt.retrofitdemo.model.User;
import com.example.binhbt.retrofitdemo.net.ApiService;
import com.example.binhbt.retrofitdemo.net.VegaRequest;
import com.example.binhbt.retrofitdemo.ui.activity.UserDetailActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by binhbt on 6/8/2016.
 */
public class UserListFragment extends BaseFragment{
    private UsersAdapter usersAdapter;// = new UsersAdapter(getActivity());
    @Bind(R.id.rv_users)
    RecyclerView rv_users;
    @Bind(R.id.rl_progress)
    RelativeLayout rl_progress;
    @Bind(R.id.rl_retry) RelativeLayout rl_retry;
    @Bind(R.id.bt_retry)
    Button bt_retry;
    public UserListFragment() {
        setRetainInstance(true);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState) {
        final View fragmentView = inflater.inflate(R.layout.fragment_user_list, container, false);
        ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState == null) {
            this.loadUserList();
        }
    }
    private void setupRecyclerView() {
        usersAdapter = new UsersAdapter(getActivity());
        this.usersAdapter.setOnItemClickListener(new UsersAdapter.OnItemClickListener() {
            @Override
            public void onUserItemClicked(User userModel) {
                Intent i = new Intent(getActivity(), UserDetailActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("user",userModel);
                i.putExtras(b);
                startActivity(i);
            }
        });
        this.rv_users.setLayoutManager(new UsersLayoutManager(getActivity()));
        this.rv_users.setAdapter(usersAdapter);
    }
    @Override public void onDestroyView() {
        super.onDestroyView();
        rv_users.setAdapter(null);
        ButterKnife.unbind(this);
    }
    private void showLoading() {
        this.rl_progress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    private void hideLoading() {
        this.rl_progress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }
    private void showError(String message) {
        this.showToastMessage(message);
    }
    private void showRetry() {
        this.rl_retry.setVisibility(View.VISIBLE);
    }

    private void hideRetry() {
        this.rl_retry.setVisibility(View.GONE);
    }

    @OnClick(R.id.bt_retry) void onButtonRetryClick() {
        UserListFragment.this.loadUserList();
    }
    private void renderUserList(Collection<User> userModelCollection) {
        if (userModelCollection != null) {
            this.usersAdapter.setUsersCollection(userModelCollection);
        }
    }
    private void loadUserList() {
        Call<List<User>> call= new ApiService().getApi().userEntityList();
        call.enqueue(new VegaRequest<List<User>>() {
            @Override
            public void onStart() {
                hideRetry();
                showLoading();
            }

            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit) {
                hideLoading();
                renderUserList(response.body());
                Log.e("response", response.raw().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                hideLoading();
                showError("Request failed");
                showRetry();
            }
        });
    }
}
