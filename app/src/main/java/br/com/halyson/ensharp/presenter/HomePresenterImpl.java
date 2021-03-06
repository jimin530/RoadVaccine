package br.com.halyson.ensharp.presenter;

import com.squareup.otto.Subscribe;

import br.com.halyson.ensharp.events.LoadColorsTabsDisk;
import br.com.halyson.ensharp.events.LoadTitleTabsDisk;
import br.com.halyson.ensharp.interfaces.home.HomePresenter;
import br.com.halyson.ensharp.interfaces.home.HomeView;
import br.com.halyson.ensharp.service.HomeServiceImpl;
import br.com.halyson.ensharp.utility.BusProvider;

/**
 * Created by Halyson on 20/01/15.
 */
public class HomePresenterImpl implements HomePresenter {
    private static final String TAG = HomePresenterImpl.class.getSimpleName();
    private HomeView mHomeView;
    private HomeServiceImpl mHomeService;

    public HomePresenterImpl(HomeView homeView) {
        BusProvider.getInstance().register(this);
        mHomeView = homeView;
        mHomeService = new HomeServiceImpl();
    }

    @Override
    public void loadSectionsTabs() {
        mHomeService.recoverTitleTabs();
    }

    @Subscribe
    public void onLoadTitleTabsDiskSuccess(LoadTitleTabsDisk loadTitleTabsDisk) {
        mHomeView.loadViewPager(loadTitleTabsDisk.getListTitleTabs());
        mHomeService.recoverColorTabs();
    }

    @Subscribe
    public void onLoadColorTabsDiskSuccess(LoadColorsTabsDisk loadColorsTabsDisk) {
        mHomeView.setColorTabs(loadColorsTabsDisk.getColorTab());
        mHomeView.setDividerColorTabs(loadColorsTabsDisk.getDividerColorTab());
        mHomeView.setIndicatorColorTabs(loadColorsTabsDisk.getIndicatorColorTab());
        mHomeView.loadTabs();
    }

    @Override
    public void onDestroy() {
        BusProvider.getInstance().unregister(this);
        mHomeService.onDestroy();
        mHomeService = null;
    }
}
