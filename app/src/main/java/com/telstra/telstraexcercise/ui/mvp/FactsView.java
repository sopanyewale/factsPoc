package com.telstra.telstraexcercise.ui.mvp;

import com.telstra.telstraexcercise.base.BaseView;
import com.telstra.telstraexcercise.business.model.FactsItem;

import java.util.ArrayList;

/**
 * Created by Sopan on 13-02-18.
 */

public interface FactsView extends BaseView {

    void displayFactsList(ArrayList<FactsItem> factList);

    void showError();

    void setScreenTitle(String title);
}
