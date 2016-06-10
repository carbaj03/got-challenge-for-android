package es.npatarino.android.gotchallenge.repository.data;

import java.util.List;

import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.mapper.GoTRealmMapper;
import es.npatarino.android.gotchallenge.repository.service.GoTService;
import rx.Observable;
import rx.functions.Action1;

public class RetrofitDataSource implements GoTDataSource {

    private GoTService goTService;
    private GoTRealmMapper goTRealmMapper;

    public RetrofitDataSource(GoTService goTService, GoTRealmMapper goTRealmMapper) {
        this.goTService = goTService;
        this.goTRealmMapper = goTRealmMapper;
    }

    @Override
    public Observable<List<GoTCharacter>> getCharacters() {
        Observable<List<GoTCharacter>> characters = goTService.listCharacters();
        return characters.doOnNext(new Action1<List<GoTCharacter>>() {
            @Override
            public void call(List<GoTCharacter> goTCharacter) {
                goTRealmMapper.persistCharacters(goTCharacter);
            }
        });
    }
}
