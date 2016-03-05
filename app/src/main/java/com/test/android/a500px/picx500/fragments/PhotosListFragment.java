package com.test.android.a500px.picx500.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.AdapterView;

import com.test.android.a500px.picx500.R;
import com.test.android.a500px.picx500.activities.MainActivity;
import com.test.android.a500px.picx500.activities.PhotoViewActivity;
import com.test.android.a500px.picx500.adapters.PhotoCardAdapter;
import com.test.android.a500px.picx500.api.PhotoService;
import com.test.android.a500px.picx500.api.PhotosApi;
import com.test.android.a500px.picx500.listeners.ScrollPaginationListener;
import com.test.android.a500px.picx500.models.Photo;
import com.test.android.a500px.picx500.models.PhotosPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.ScaleInBottomAnimator;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * A placeholder fragment containing a simple view.
 */
public class PhotosListFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final int PAGE_ITEMS_AMOUNT = 20;
    private static final int FIRST_PAGE_NUMBER = 1;
    @Bind(R.id.photosRecyclerView)
    RecyclerView photosRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private GridLayoutManager mLayoutManager;
    private List<Photo> photoList;
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private PhotosApi photosApi;
    private ScrollPaginationListener scrollPaginationListener;

    public PhotosListFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);


        photoList = new ArrayList<>(PAGE_ITEMS_AMOUNT);
        mAdapter = new PhotoCardAdapter(photoList, this);

        subscriptions = new CompositeSubscription();
        photosApi = PhotoService.createPhotoService();
        if (((MainActivity)getActivity()).isNetworkAvailable()) {
            loadPhotosByPageNumber(FIRST_PAGE_NUMBER);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_photos_list, container, false);
        ButterKnife.bind(this, layout);

        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        scrollPaginationListener = new ScrollPaginationListener(mLayoutManager) {
            @Override
            public void loadNextPage(int nextPageNumber, int amountOfItemsInPage) {
                loadPhotosByPageNumber(nextPageNumber);
            }
        };

        scrollPaginationListener.setPageItemsAmount(PAGE_ITEMS_AMOUNT);
        photosRecyclerView.setLayoutManager(mLayoutManager);
        photosRecyclerView.setItemAnimator(new ScaleInBottomAnimator(new OvershootInterpolator(1f)));
        photosRecyclerView.getItemAnimator().setAddDuration(1000);
        photosRecyclerView.getItemAnimator().setMoveDuration(1000);
        photosRecyclerView.getItemAnimator().setChangeDuration(1000);
        photosRecyclerView.getItemAnimator().setRemoveDuration(1000);
        photosRecyclerView.setAdapter(mAdapter);
        photosRecyclerView.addOnScrollListener(scrollPaginationListener);
        return layout;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), PhotoViewActivity.class);
        intent.putExtra(PhotoViewActivity.EXTRAS_PHOTO_OBJECT, photoList.get(i));
        startActivity(intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscriptions != null) {
            subscriptions.unsubscribe();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private void loadPhotosByPageNumber(int pageNumber) {
        subscriptions.add(//
                photosApi.photosPage(String.valueOf(pageNumber))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<PhotosPage>() {
                            @Override
                            public void onCompleted() {
                                scrollPaginationListener.loadingStopped();
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(PhotosListFragment.class.getSimpleName() + ":loadPhotosByPageNumber", "error to load photos", e);
                                scrollPaginationListener.loadingStopped();
                                Snackbar.make(photosRecyclerView, R.string.error_during_load_page_of_photos, Snackbar.LENGTH_LONG)
                                        .show();
                            }

                            @Override
                            public void onNext(PhotosPage photosPage) {
                                int newItemsCount = photosPage.getPhotos().size();
                                if (newItemsCount > 0) {
                                    int oldItemsCount = photoList.size();
                                    photoList.addAll(photosPage.getPhotos());
                                    mAdapter.notifyItemRangeInserted(oldItemsCount, newItemsCount);
                                    scrollPaginationListener.setTotalPagesAmount(photosPage.getTotalPages());
                                }
                            }

                        }));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (((MainActivity)getActivity()).isNetworkAvailable()) {
            loadPhotosByPageNumber(FIRST_PAGE_NUMBER);
        }
    }
}
