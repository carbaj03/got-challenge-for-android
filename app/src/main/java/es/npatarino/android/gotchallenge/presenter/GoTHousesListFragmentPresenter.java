package es.npatarino.android.gotchallenge.presenter;

import es.npatarino.android.gotchallenge.HouseMvp.Presenter;
import es.npatarino.android.gotchallenge.HouseMvp.View;
import es.npatarino.android.gotchallenge.model.GoTHouse;
import es.npatarino.android.gotchallenge.repository.GoTRepository;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoTHousesListFragmentPresenter implements Presenter {

    private View view;
    private GoTRepository goTRepository;
    private Subscription subscription;

    public GoTHousesListFragmentPresenter(View view, GoTRepository goTRepository) {
        this.view = view;
        this.goTRepository = goTRepository;
    }

    @Override
    public void loadHouses() {

        subscription = goTRepository.getCharacters()
                .concatMap(Observable::from)
                .map(GoTHouse::new)
                .filter(goTHouse -> goTHouse.getHouseId() != null && goTHouse.getHouseId().length() > 0).distinct().toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(houses -> {
                    view.displayLoading(false);
                    view.displayHouses(houses);
                }, error -> {
                    view.displayLoading(false);
                });
    }

    @Override
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
