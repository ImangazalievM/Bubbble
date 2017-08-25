package com.imangazalievm.bubbble.data.repository;

import com.imangazalievm.bubbble.data.repository.datastores.ShotsDataStore;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsRequestParams;
import com.imangazalievm.bubbble.domain.models.UserShotsRequestParams;
import com.imangazalievm.bubbble.domain.repository.IShotsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ShotsRepository implements IShotsRepository {

    private ShotsDataStore shotsDataStore;

    @Inject
    public ShotsRepository(ShotsDataStore shotsDataStore) {
        this.shotsDataStore = shotsDataStore;
    }

    @Override
    public Single<List<Shot>> getShot(ShotsRequestParams shotsRequestParams) {
        return shotsDataStore.getShots(shotsRequestParams);
    }

    @Override
    public Single<Shot> getShot(long shotId) {
        return shotsDataStore.getShot(shotId);
    }

    @Override
    public Single<List<Shot>> getUserShots(UserShotsRequestParams requestParams) {
        return shotsDataStore.getUserShots(requestParams);
    }

}
