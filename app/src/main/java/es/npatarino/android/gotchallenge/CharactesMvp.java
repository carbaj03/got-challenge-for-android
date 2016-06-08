package es.npatarino.android.gotchallenge;

public interface CharactesMvp {

    interface Model {
    }

    interface View {
        void displayCharacters();
        void displayLoading(boolean show);
    }

    interface Presenter {
        void loadCharacters();
    }
}