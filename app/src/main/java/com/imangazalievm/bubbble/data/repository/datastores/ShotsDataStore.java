package com.imangazalievm.bubbble.data.repository.datastores;

import android.util.Log;

import com.imangazalievm.bubbble.Constants;
import com.imangazalievm.bubbble.data.mappers.ShotResponseMapper;
import com.imangazalievm.bubbble.data.network.DribbbleApiConstants;
import com.imangazalievm.bubbble.data.network.DribbbleApiService;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsRequestParams;
import com.imangazalievm.bubbble.domain.models.UserShotsRequestParams;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ShotsDataStore {

    private DribbbleApiService dribbbleApiService;
    private ShotResponseMapper shotResponseMapper;

    @Inject
    public ShotsDataStore(DribbbleApiService dribbbleApiService, ShotResponseMapper shotResponseMapper) {
        this.dribbbleApiService = dribbbleApiService;
        this.shotResponseMapper = shotResponseMapper;
    }

    public Single<List<Shot>> getShots(ShotsRequestParams requestParams) {
        String sort = requestParams.getSort().equals(Constants.SHOTS_SORT_POPULAR) ?
                DribbbleApiConstants.SHOTS_SORT_POPULAR : DribbbleApiConstants.SHOTS_SORT_RECENT;

        return dribbbleApiService.getShots(sort, requestParams.getPage(), requestParams.getPageSize())
                .map(shots -> shotResponseMapper.map(shots));
    }

    public Single<Shot> getShot(long shotId) {
        return dribbbleApiService.getShot(shotId)
                .map(shot -> shotResponseMapper.map(shot));
    }

    public Single<List<Shot>> getUserShots(UserShotsRequestParams requestParams) {
        return dribbbleApiService.getUserShots(requestParams.getUserId(), requestParams.getPage(), requestParams.getPageSize())
                .map(shots -> shotResponseMapper.map(shots));
    }



}
