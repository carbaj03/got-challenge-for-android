package es.npatarino.android.gotchallenge.repository;

import java.util.List;

import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.entity.GoTCharacterEntity;
import es.npatarino.android.gotchallenge.model.mapper.GoTEntityMapper;
import es.npatarino.android.gotchallenge.model.mapper.GoTRealmMapper;
import es.npatarino.android.gotchallenge.repository.data.GoTDataSource;
import es.npatarino.android.gotchallenge.repository.data.LocalDataSource;
import es.npatarino.android.gotchallenge.repository.data.RetrofitDataSource;
import rx.Observable;
import rx.functions.Action1;

public class GoTFactoryRepository implements GoTRepository {

    GoTEntityMapper goTEntityMapper;
    GoTRealmMapper goTRealmMapper;

    RetrofitDataSource retrofitDataSource;
    LocalDataSource localDataSource;

    public GoTFactoryRepository(GoTEntityMapper goTEntityMapper,
                                GoTRealmMapper goTRealmMapper,
                                RetrofitDataSource retrofitDataSource,
                                LocalDataSource localDataSource) {
        this.goTEntityMapper = goTEntityMapper;
        this.goTRealmMapper = goTRealmMapper;
        this.retrofitDataSource = retrofitDataSource;
        this.localDataSource = localDataSource;
    }

    @Override
    public Observable<List<GoTCharacter>> getCharacters() {
        return getBestDataSource().getCharacters();
    }

    private GoTDataSource getBestDataSource() {
//        if ((!NetworkGoTDataSource.getInstance().isNetworkAvailable()) ||
//                (!CacheGoTPolicy.isCacheExpired()))
        return retrofitDataSource;
    }
}
