package com.telstra.telstraexcercise.ui.mvp;

import com.telstra.telstraexcercise.base.BasePresenter;
import com.telstra.telstraexcercise.business.api.ConsumeService;
import com.telstra.telstraexcercise.business.model.FactsDataObject;
import com.telstra.telstraexcercise.business.model.FactsItem;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by Sopan on 13-02-18.
 */

public class FactsPresenter extends BasePresenter<FactsView> {

    ConsumeService consumeService;
    private CompositeSubscription subscriptions;

    @Inject
    public FactsPresenter(ConsumeService consumeService) {
        this.consumeService = consumeService;
        this.subscriptions = new CompositeSubscription();
    }

    public void getDataFromServer() {
        Subscription subscription = consumeService.getFactList(new ConsumeService.GetFactListCallback() {
            @Override
            public void onSuccess(FactsDataObject factsDataObject) {
                ArrayList<FactsItem> refinedFactList = new ArrayList<FactsItem>();
                for (int i = 0; i < factsDataObject.getFactsItems().size(); i++) {
                    FactsItem factsItem = factsDataObject.getFactsItems().get(i);
                    String title = factsItem.getTitle();
                    String desc = factsItem.getDescription();
                    String imgUrl = factsItem.getImageHref();

                    if (title == null && desc == null && imgUrl == null) {
                        continue;
                    }
                    refinedFactList.add(factsItem);
                }
                mView.setScreenTitle(factsDataObject.getTitle());
                mView.displayFactsList(refinedFactList);
            }

            @Override
            public void onError() {
                mView.showError();
            }

        });

        subscriptions.add(subscription);
    }

    public void onActivityStop() {
        subscriptions.unsubscribe();
    }

}
