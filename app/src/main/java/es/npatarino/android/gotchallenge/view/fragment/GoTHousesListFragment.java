package es.npatarino.android.gotchallenge.view.fragment;


import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.npatarino.android.gotchallenge.GoTApplication;
import es.npatarino.android.gotchallenge.HouseMvp;
import es.npatarino.android.gotchallenge.R;
import es.npatarino.android.gotchallenge.injection.module.GoTHousesListFragmentModule;
import es.npatarino.android.gotchallenge.model.GoTHouse;
import es.npatarino.android.gotchallenge.repository.GoTRepository;
import es.npatarino.android.gotchallenge.view.activity.HouseActivity;
import es.npatarino.android.gotchallenge.view.adapter.GoTHouseAdapter;
import es.npatarino.android.gotchallenge.view.listener.ItemClickListener;

public class GoTHousesListFragment extends FragmentBase implements ItemClickListener, HouseMvp.View{

    private static final String TAG = GoTHousesListFragment.class.getSimpleName();

    @BindView(R.id.progressBar) ContentLoadingProgressBar progressBar;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    @Inject GoTHouseAdapter adapter;
    @Inject GoTRepository goTRepository;
    @Inject HouseMvp.Presenter presenter;

    public static GoTHousesListFragment newInstance() {
        return new GoTHousesListFragment();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, rootView);

        configRecyclerView();

        presenter.loadHouses();

        return rootView;
    }

    private void configRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void displayHouses(List<GoTHouse> goTHouses) {
        adapter.addAll(goTHouses);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void displayLoading(boolean show) {
        if (show)
            progressBar.show();
        else
            progressBar.hide();
    }

    @Override
    protected void setupActivityComponent() {
        GoTApplication.get(getActivity()).getComponent()
                .plus(new GoTHousesListFragmentModule(this))
                .inject(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        HouseActivity.launch(getActivity(), adapter.getItem(position));
    }

}
