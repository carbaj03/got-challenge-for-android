package es.npatarino.android.gotchallenge.injection.module;

import android.content.Context;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.npatarino.android.gotchallenge.Constants;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.model.mapper.GoTEntityMapper;
import es.npatarino.android.gotchallenge.model.mapper.GoTRealmMapper;
import es.npatarino.android.gotchallenge.repository.GoTFactoryRepository;
import es.npatarino.android.gotchallenge.repository.GoTRepository;
import es.npatarino.android.gotchallenge.repository.data.LocalDataSource;
import es.npatarino.android.gotchallenge.repository.data.RetrofitDataSource;
import es.npatarino.android.gotchallenge.repository.service.GoTService;
import es.npatarino.android.gotchallenge.util.NetworkUtil;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class GoTRepositoryModule {

    @Provides @Singleton
    GoTEntityMapper provideGoTEntityMapper(){
        return new GoTEntityMapper();
    }

    @Provides @Named(Constants.Injection.Named.GOT_API_KEY)
    public String provideGoTApiKey(Context context){
        return context.getString(R.string.url_service);
    }

    @Provides @Singleton
    public Retrofit provideRestAdapter(@Named(Constants.Injection.Named.GOT_API_KEY) String apiKey) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(apiKey)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());
        return builder.build();
    }

    @Provides @Singleton
    public GoTService provideGoTService(Retrofit restAdapter) {
        return restAdapter.create(GoTService.class);
    }

    @Provides @Singleton
    public RetrofitDataSource provideRetrofitDataSource(GoTService goTService, GoTRealmMapper goTRealmMapper){
        return new RetrofitDataSource(goTService, goTRealmMapper);
    }

    @Provides @Singleton
    public Realm provideRealm(Context context) {
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfig);
        return Realm.getDefaultInstance();
    }

    @Provides @Singleton
    public GoTRealmMapper provideRealmMapper(Realm realm){
        return new GoTRealmMapper(realm);
    }

    @Provides @Singleton
    public LocalDataSource provideLocalDataSource(Realm realm, GoTEntityMapper goTEntityMapper){
        return new LocalDataSource(realm, goTEntityMapper);
    }

    @Provides @Singleton
    NetworkUtil networkUtil(Context context){
        return new NetworkUtil(context);
    }

    @Provides @Singleton
    GoTRepository provideGoTRepository(NetworkUtil networkUtil,
                                       RetrofitDataSource retrofitDataSource,
                                       LocalDataSource localDataSource){
        return new GoTFactoryRepository(networkUtil, retrofitDataSource, localDataSource);
    }
}
