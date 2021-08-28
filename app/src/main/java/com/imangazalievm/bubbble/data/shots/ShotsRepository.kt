package com.imangazalievm.bubbble.data.shots;

import com.imangazalievm.bubbble.Constants;
import com.imangazalievm.bubbble.data.global.network.Dribbble;
import com.imangazalievm.bubbble.data.global.network.DribbbleApiService;
import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.domain.global.models.ShotsRequestParams;
import com.imangazalievm.bubbble.domain.global.models.ShotsSearchRequestParams;
import com.imangazalievm.bubbble.domain.global.models.UserShotsRequestParams;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

public class ShotsRepository {

    private DribbbleApiService dribbbleApiService;
    private DribbbleSearchDataSource dribbbleSearchDataSource;

    @Inject
    public ShotsRepository(
            DribbbleApiService dribbbleApiService,
            DribbbleSearchDataSource dribbbleSearchDataSource
    ) {
        this.dribbbleApiService = dribbbleApiService;
        this.dribbbleSearchDataSource = dribbbleSearchDataSource;
    }

    public Single<List<Shot>> getShots(ShotsRequestParams requestParams) {
        String sort = requestParams.getSort().equals(Constants.SHOTS_SORT_POPULAR) ?
                Dribbble.Shots.type_popular : Dribbble.Shots.type_recent;

        return dribbbleApiService.getShots(sort, requestParams.getPage(), requestParams.getPageSize());
    }

    public Single<Shot> getShot(long shotId) {
        return dribbbleApiService.getShot(shotId);
    }

    public Single<List<Shot>> getUserShots(UserShotsRequestParams requestParams) {
        return dribbbleApiService.getUserShots(
                requestParams.getUserId(),
                requestParams.getPage(),
                requestParams.getPageSize()
        );
    }

    public Single<List<Shot>> search(ShotsSearchRequestParams requestParams) {
        return dribbbleSearchDataSource.search(
                requestParams.getSearchQuery(),
                requestParams.getSort(),
                requestParams.getPage(),
                requestParams.getPageSize()
        );
    }

}
