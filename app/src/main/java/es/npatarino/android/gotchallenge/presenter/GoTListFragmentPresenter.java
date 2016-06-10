package es.npatarino.android.gotchallenge.presenter;

import java.util.List;

import es.npatarino.android.gotchallenge.CharactesMvp.Presenter;
import es.npatarino.android.gotchallenge.CharactesMvp.View;
import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.GoTHouse;
import es.npatarino.android.gotchallenge.repository.GoTRepository;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class GoTListFragmentPresenter implements Presenter {

    private View view;
    private GoTRepository goTRepository;
    private Subscription subscription;

    private GoTHouse goTHouse;
    private String query;

    public GoTListFragmentPresenter(View view, GoTRepository goTRepository) {
        this.view = view;
        this.goTRepository = goTRepository;
    }

    @Override
    public void loadCharacters() {
        view.displayLoading(true);
        Observable<GoTCharacter> character = goTRepository.getCharacters()
                .concatMap(Observable::from);
        character = checkHouse(character);
        character = checkQuery(character);
        subscription = character.toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(characters -> {
                    view.displayLoading(false);
                    view.displayCharacters(characters);
                }, error -> {
                    view.displayLoading(false);
                });
    }

    @Override
    public void loadQuery(String query) {
        this.query = query;
        loadCharacters();
    }

    private Observable<GoTCharacter> checkQuery(Observable<GoTCharacter> character){
        if(query != null && !query.isEmpty())
            character = character.filter(goTCharacter ->
                    goTCharacter.getName().contains(query));
        return character;
    }

    private Observable<GoTCharacter> checkHouse(Observable<GoTCharacter> character){
        if(goTHouse != null)
            character = character.filter(goTCharacter ->
                    goTCharacter.getHouseId().equals(goTHouse.getHouseId()));
        return character;
    }

    @Override
    public void onCreate() {
        goTHouse = view.getHouse();
    }

    @Override
    public void onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();
    }
}
