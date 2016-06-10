package es.npatarino.android.gotchallenge;

import java.util.List;

import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.model.GoTHouse;

public interface HouseMvp {

    interface Model {
    }

    interface View {
        void displayHouses(List<GoTHouse> goTHouses);
        void displayLoading(boolean show);
    }

    interface Presenter {
        void loadHouses();
        void onDestroy();
    }
}