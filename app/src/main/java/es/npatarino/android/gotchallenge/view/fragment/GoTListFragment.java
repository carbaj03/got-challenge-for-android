package es.npatarino.android.gotchallenge.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.npatarino.android.gotchallenge.GoTApplication;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.injection.module.GoTListFragmentModule;
import es.npatarino.android.gotchallenge.model.GoTCharacter;
import es.npatarino.android.gotchallenge.repository.GoTRepository;
import es.npatarino.android.gotchallenge.view.activity.DetailActivity;
import es.npatarino.android.gotchallenge.view.adapter.GoTAdapter;
import es.npatarino.android.gotchallenge.view.listener.ItemClickListener;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GoTListFragment extends FragmentBase implements ItemClickListener {

    private static final String TAG = GoTListFragment.class.getSimpleName();

    @Inject GoTAdapter adapter;
    @Inject LayoutManager layoutManager;
    @Inject GoTRepository goTRepository;

    @BindView(R.id.progressBar) ContentLoadingProgressBar progressBar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    private SearchView searchView;

    public static Fragment newInstance() {
        return new GoTListFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, rootView);

        configRecyclerView();
        displayLoading(true);
        getCharacters();

        return rootView;
    }

    private void getCharacters() {
        goTRepository.getCharacters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(characters -> {
                    displayLoading(false);
                    displayCharacters(characters);
                }, error -> {
                    displayLoading(false);
                });
    }

    private void displayCharacters(List<GoTCharacter> characters) {
        adapter.addAll(characters);
        adapter.notifyDataSetChanged();
    }

    private void displayLoading(boolean show) {
        if (show)
            progressBar.show();
        else
            progressBar.hide();
    }

    private void configRecyclerView() {
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void setupActivityComponent() {
        GoTApplication.get(getActivity()).getComponent()
                .plus(new GoTListFragmentModule(this)).inject(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        DetailActivity.launch(getActivity(), adapter.getItem(position));
    }

    @Override
    public void onActivityCreated(Bundle savedState) {
        super.onActivityCreated(savedState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.characters, menu);
        configSearchView(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void configSearchView(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                displayLoading(true);
                getCharacterQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.isEmpty()) {
                    displayLoading(true);
                    getCharacterQuery(newText);
                }
                return false;
            }

        });
    }

    private void getCharacterQuery(String query) {
        goTRepository.getCharacters()
                .map(goTCharacters -> Observable.from(goTCharacters)
                        .concatMap(Observable::just)
                        .filter(goTCharacter -> goTCharacter.getName().contains(query))
                        .toList().toBlocking().single())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(characters -> {
                    displayLoading(false);
                    displayCharacters(characters);
                }, error -> {
                    displayLoading(false);
                });
    }
}