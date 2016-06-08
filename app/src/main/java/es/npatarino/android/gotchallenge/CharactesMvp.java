package es.npatarino.android.gotchallenge;

import java.util.List;

import es.npatarino.android.gotchallenge.model.GoTCharacter;

public interface CharactesMvp {

    interface Model {
    }

    interface View {
        void displayCharacters(List<GoTCharacter> goTCharacters);
        void displayLoading(boolean show);
    }

    interface Presenter {
        void loadCharacters();
    }
}