package es.npatarino.android.gotchallenge.repository.data;

import java.util.List;

import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.entity.GoTCharacterEntity;
import es.npatarino.android.gotchallenge.model.mapper.GoTEntityMapper;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

public class LocalDataSource implements GoTDataSource {

    private Realm realm;
    private GoTEntityMapper goTEntityMapper;

    public LocalDataSource(Realm realm, GoTEntityMapper goTEntityMapper) {
        this.realm = realm;
        this.goTEntityMapper = goTEntityMapper;
    }

    @Override
    public Observable<List<GoTCharacter>> getCharacters() {
         RealmResults<GoTCharacterEntity> query = realm.where(GoTCharacterEntity.class)
                .isNotNull("name")
                .findAllSortedAsync("name");
        return Observable.from(query).map(goTCharacterEntity -> goTEntityMapper.transform(goTCharacterEntity)).toList();
    }
}
