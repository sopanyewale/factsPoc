package com.telstra.telstraexcercise.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.telstra.telstraexcercise.R;
import com.telstra.telstraexcercise.adapter.FactsListAdapter;
import com.telstra.telstraexcercise.base.BaseActivity;
import com.telstra.telstraexcercise.business.di.component.DaggerFactsComponent;
import com.telstra.telstraexcercise.business.di.component.FactsComponent;
import com.telstra.telstraexcercise.business.di.module.FactsModule;
import com.telstra.telstraexcercise.business.model.FactsItem;
import com.telstra.telstraexcercise.ui.mvp.FactsPresenter;
import com.telstra.telstraexcercise.ui.mvp.FactsView;
import com.telstra.telstraexcercise.utils.NetworkUtil;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements FactsView {

    @BindView(R.id.layout_swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_facts_list)
    RecyclerView factListView;
    @BindView(R.id.layout_coordinator)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.txt_no_data)
    TextView txtNoData;

    @Inject
    FactsPresenter factsPresenter;
    Snackbar snackbar;

    private FactsComponent factsComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialize();
    }

    private void initialize() {
        //initialize dagger component
        factsComponent = DaggerFactsComponent.builder().factsModule(new FactsModule(this)).build();
        factsComponent.inject(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        factListView.setLayoutManager(layoutManager);
        factListView.setItemAnimator(new DefaultItemAnimator());


        getFactsDataFromServer();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFactsDataFromServer();
            }
        });
    }

    private void getFactsDataFromServer() {
        if (NetworkUtil.isNetworkAvailable(this)) {
            factsPresenter.getDataFromServer();
            swipeRefreshLayout.setRefreshing(true);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            showRetryMessage(getString(R.string.network_error), null);
        }
    }


    @Override
    public void displayFactsList(ArrayList<FactsItem> factList) {
        if(factList.size() > 0)
            txtNoData.setVisibility(View.INVISIBLE);
        else
            txtNoData.setVisibility(View.VISIBLE);

        FactsListAdapter factsListAdapter = new FactsListAdapter(this, factList);
        factListView.setAdapter(factsListAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError() {
        swipeRefreshLayout.setRefreshing(false);
        showRetryMessage(getString(R.string.retry_message), getString(R.string.retry));
    }

    @Override
    public void setScreenTitle(String title) {
        this.setTitle(title);
    }

    @Override
    protected void onStop() {
        super.onStop();
        factsPresenter.onActivityStop();
    }

    private void showRetryMessage(String message, String action) {
        if (null != action) {
            snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(getString(R.string.retry), new RetryActionListener());
        } else {
            snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        }
        snackbar.show();
    }

    private class RetryActionListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            getFactsDataFromServer();
        }
    }
}
