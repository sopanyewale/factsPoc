package com.telstra.telstraexcercise.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.telstra.telstraexcercise.R;
import com.telstra.telstraexcercise.adapter.FactsListAdapter;
import com.telstra.telstraexcercise.base.BaseActivity;
import com.telstra.telstraexcercise.business.di.component.DaggerFactsComponent;
import com.telstra.telstraexcercise.business.di.component.FactsComponent;
import com.telstra.telstraexcercise.business.di.module.FactsModule;
import com.telstra.telstraexcercise.business.model.FactsItem;
import com.telstra.telstraexcercise.ui.mvp.FactsPresenter;
import com.telstra.telstraexcercise.ui.mvp.FactsView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements FactsView {

    @BindView(R.id.layout_swipe_to_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.rv_facts_list)
    RecyclerView factListView;

    @Inject
    FactsPresenter factsPresenter;

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
        factsPresenter.getDataFromServer();
        swipeRefreshLayout.setRefreshing(true);
    }


    @Override
    public void displayFactsList(ArrayList<FactsItem> factList) {
        FactsListAdapter factsListAdapter = new FactsListAdapter(this, factList);
        factListView.setAdapter(factsListAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError() {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, "Some error occured. Please try again later", Toast.LENGTH_SHORT).show();
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
}
