package com.telstra.telstraexcercise.base;

import javax.inject.Inject;

/**
 * Created by Sopan on 13-02-18.
 */

public class BasePresenter<V extends BaseView> {

    @Inject
    protected V mView;


}
