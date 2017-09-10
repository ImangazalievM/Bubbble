package com.imangazalievm.bubbble.domain.repository;


import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotsRequestParams;
import com.imangazalievm.bubbble.domain.models.ShotsSearchRequestParams;
import com.imangazalievm.bubbble.domain.models.UserShotsRequestParams;

import java.util.List;

import io.reactivex.Single;

public interface ShotsRepository {

    Single<List<Shot>> getShots(ShotsRequestParams shotsRequestParams);
    Single<Shot> getShot(long shotId);
    Single<List<Shot>> getUserShots(UserShotsRequestParams requestParams);
    Single<List<Shot>> search(ShotsSearchRequestParams requestParams);
}
