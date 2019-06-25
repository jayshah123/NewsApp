package com.jayshah.newsapp.HeadlinesUI;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jayshah.newsapp.MainApplication;
import com.jayshah.newsapp.newsData.NewsRepository;
import com.jayshah.newsapp.R;
import com.jayshah.newsapp.models.TopHeadline;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Holds the main fragment that displays a list of news headlines
 */
public class NewsHeadlinesFragment extends Fragment implements NewsHeadlinesAdapter.OnItemClickListener {

    @Inject
    NewsRepository newsRepository;

    private static final String TAG = "NewsHeadlinesFragment";

    RecyclerView topHeadlinesList;
    NewsHeadlinesAdapter newsHeadlinesAdapter;
    List<TopHeadline> headlineList;

    Disposable d;

    public static NewsHeadlinesFragment newInstance() {

        Bundle args = new Bundle();

        NewsHeadlinesFragment fragment = new NewsHeadlinesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainApplication)getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.headlines_fragment, container, false);
        topHeadlinesList = (RecyclerView) v.findViewById(R.id.topheadlines_list);
        topHeadlinesList.setLayoutManager(new LinearLayoutManager(getContext()));
        newsHeadlinesAdapter = new NewsHeadlinesAdapter(getContext());
        newsHeadlinesAdapter.setItemClickListener(this);
        topHeadlinesList.setAdapter(newsHeadlinesAdapter);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        d = newsRepository.getHeadlinesForUI().observeOn(AndroidSchedulers.mainThread())
                .subscribe(allHeadlines -> {
                    Log.d(TAG, "Observed data changed!");
                    newsHeadlinesAdapter.setData(allHeadlines);
                    this.headlineList = allHeadlines;
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        d.dispose();
    }

    @Override
    public void onItemClick(int position) {
        String url = this.headlineList.get(position).url;
        HeadlineDetailFragment headlineDetailFragment = HeadlineDetailFragment.newInstance(url);
        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, headlineDetailFragment)
                .addToBackStack(null)
                .commit();
    }
}
