package es.npatarino.android.gotchallenge.repository;

import java.util.List;

import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.entity.GoTCharacterEntity;
import es.npatarino.android.gotchallenge.model.mapper.GoTEntityMapper;
import es.npatarino.android.gotchallenge.model.mapper.GoTRealmMapper;
import es.npatarino.android.gotchallenge.repository.data.GoTDataSource;
import es.npatarino.android.gotchallenge.repository.data.LocalDataSource;
import es.npatarino.android.gotchallenge.repository.data.RetrofitDataSource;
import es.npatarino.android.gotchallenge.util.NetworkUtil;
import rx.Observable;
import rx.functions.Action1;

public class GoTFactoryRepository implements GoTRepository {

    RetrofitDataSource retrofitDataSource;
    LocalDataSource localDataSource;

    NetworkUtil networkUtil;

    public GoTFactoryRepository(NetworkUtil networkUtil,
                                RetrofitDataSource retrofitDataSource,
                                LocalDataSource localDataSource) {
        this.retrofitDataSource = retrofitDataSource;
        this.localDataSource = localDataSource;
        this.networkUtil = networkUtil;
    }

    @Override
    public Observable<List<GoTCharacter>> getCharacters() {
        return getBestDataSource().getCharacters();
    }

    private GoTDataSource getBestDataSource() {
        if (networkUtil.isNetworkAvailable() )
            return retrofitDataSource;
        else
            return localDataSource;
    }
}
