package es.npatarino.android.gotchallenge.model.mapper;


import java.util.List;

import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.entity.GoTCharacterEntity;
import io.realm.Realm;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class GoTRealmMapper {

    private Realm realm;

    public GoTRealmMapper(Realm realm) {
        this.realm = realm;
    }

    public void persistCharacters(List<GoTCharacter> goTCharacters){
        deleteRealm();

        Observable.from(goTCharacters).doOnNext(this::persistCharacter);
    }

    private void deleteRealm() {
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    private void persistCharacter(GoTCharacter goTCharacter) {
        GoTCharacterEntity c = new GoTCharacterEntity();
        c.setDescription(goTCharacter.getDescription());
        c.setHouseId(goTCharacter.getHouseId());
        c.setHouseImageUrl(goTCharacter.getHouseImageUrl());
        c.setHouseName(goTCharacter.getHouseName());
        c.setImageUrl(goTCharacter.getImageUrl());
        c.setName(goTCharacter.getName());

        realm.beginTransaction();
        realm.copyToRealm(c);
        realm.commitTransaction();
    }


}
