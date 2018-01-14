package com.imangazalievm.bubbble.domain.global.repository;


import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.domain.global.models.ShotsRequestParams;
import com.imangazalievm.bubbble.domain.global.models.ShotsSearchRequestParams;
import com.imangazalievm.bubbble.domain.global.models.UserShotsRequestParams;

import java.util.List;

import io.reactivex.Single;

public interface ShotsRepository {

    Single<List<Shot>> getShots(ShotsRequestParams shotsRequestParams);
    Single<Shot> getShot(long shotId);
    Single<List<Shot>> getUserShots(UserShotsRequestParams requestParams);
    Single<List<Shot>> search(ShotsSearchRequestParams requestParams);
}
