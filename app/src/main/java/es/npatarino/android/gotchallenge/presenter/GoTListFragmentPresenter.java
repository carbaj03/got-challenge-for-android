package es.npatarino.android.gotchallenge.presenter;

import javax.inject.Inject;

import es.npatarino.android.gotchallenge.CharactesMvp.*;
import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.GoTHouse;
import es.npatarino.android.gotchallenge.repository.GoTRepository;
import rx.Observable;
import rx.Subscription;

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
        Observable<GoTCharacter> character = goTRepository.getCharacters()
                .concatMap(Observable::from);
        character = checkHouse(character);
        character = checkQuery(character);
        subscription = character.toList().subscribe(characters -> {
            view.displayLoading(false);
            view.displayCharacters(characters);
        }, error -> {
            view.displayLoading(false);
        });
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
}
