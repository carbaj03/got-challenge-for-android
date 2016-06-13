package es.npatarino.android.gotchallenge.repository.data;

import java.util.List;

import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.mapper.GoTRealmMapper;
import es.npatarino.android.gotchallenge.repository.service.GoTService;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RetrofitDataSource implements GoTDataSource {

    private GoTService goTService;
    private GoTRealmMapper goTRealmMapper;

    public RetrofitDataSource(GoTService goTService, GoTRealmMapper goTRealmMapper) {
        this.goTService = goTService;
        this.goTRealmMapper = goTRealmMapper;
    }

    @Override
    public Observable<List<GoTCharacter>> getCharacters() {

        goTService.listCharacters()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GoTCharacter>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(List<GoTCharacter> goTCharacters) {
                goTRealmMapper.persistCharacters(goTCharacters);
            }
        });

        return goTService.listCharacters();
    }
}
