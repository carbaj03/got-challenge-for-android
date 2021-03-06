package es.npatarino.android.gotchallenge;

import java.util.List;

import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.GoTHouse;

public interface CharactesMvp {

    interface Model {
    }

    interface View {
        void displayCharacters(List<GoTCharacter> goTCharacters);
        void displayLoading(boolean show);
        GoTHouse getHouse();
    }

    interface Presenter {
        void loadCharacters();
        void loadQuery(String query);
        void onCreate();
        void onDestroy();
    }
}