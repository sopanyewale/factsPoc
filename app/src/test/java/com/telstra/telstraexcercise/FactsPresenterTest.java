package com.telstra.telstraexcercise;

import com.telstra.telstraexcercise.business.api.ConsumeService;
import com.telstra.telstraexcercise.business.model.FactsItem;
import com.telstra.telstraexcercise.ui.mvp.FactsPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

/**
 * Created by SO356253 on 2/22/2018.
 */
@RunWith(MockitoJUnitRunner.class)
public class FactsPresenterTest {

    @Mock
    FactsItem factsItem;
    @Mock
    ConsumeService consumeService;

    FactsPresenter factsPresenter;

    @Before
    public void Initialize() {
        factsPresenter = new FactsPresenter(consumeService);
    }

    @Test
    public void isFactValid_returnsTrue() {
        when(factsItem.getTitle()).thenReturn("FactTitle");
        when(factsItem.getDescription()).thenReturn("FactDesc");
        when(factsItem.getImageHref()).thenReturn("FactImageUrl");

        assertThat(factsPresenter.isFactValid(factsItem), is(true));
    }

}
