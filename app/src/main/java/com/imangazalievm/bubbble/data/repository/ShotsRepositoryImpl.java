package com.imangazalievm.bubbble.data.repository;

import com.imangazalievm.bubbble.Constants;
import com.imangazalievm.bubbble.data.network.DribbbleApiConstants;
import com.imangazalievm.bubbble.data.network.DribbbleApiService;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsRequestParams;
import com.imangazalievm.bubbble.domain.models.UserShotsRequestParams;
import com.imangazalievm.bubbble.domain.repository.ShotsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ShotsRepositoryImpl implements ShotsRepository {

    private DribbbleApiService dribbbleApiService;

    @Inject
    public ShotsRepositoryImpl(DribbbleApiService dribbbleApiService) {
        this.dribbbleApiService = dribbbleApiService;
    }

    @Override
    public Single<List<Shot>> getShots(ShotsRequestParams requestParams) {
        String sort = requestParams.getSort().equals(Constants.SHOTS_SORT_POPULAR) ?
                DribbbleApiConstants.SHOTS_SORT_POPULAR : DribbbleApiConstants.SHOTS_SORT_RECENT;

        return dribbbleApiService.getShots(sort, requestParams.getPage(), requestParams.getPageSize());
    }

    @Override
    public Single<Shot> getShot(long shotId) {
        return dribbbleApiService.getShot(shotId);
    }

    @Override
    public Single<List<Shot>> getUserShots(UserShotsRequestParams requestParams) {
        return dribbbleApiService.getUserShots(requestParams.getUserId(), requestParams.getPage(), requestParams.getPageSize());
    }

}
