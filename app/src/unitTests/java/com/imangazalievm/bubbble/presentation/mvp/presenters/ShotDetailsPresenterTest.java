package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.imangazalievm.bubbble.domain.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.interactors.ShotDetailsInteractor;
import com.imangazalievm.bubbble.domain.models.Comment;
import com.imangazalievm.bubbble.domain.models.Images;
import com.imangazalievm.bubbble.domain.models.Shot;
import com.imangazalievm.bubbble.domain.models.ShotCommentsRequestParams;
import com.imangazalievm.bubbble.domain.models.User;
import com.imangazalievm.bubbble.presentation.commons.permissions.Permission;
import com.imangazalievm.bubbble.presentation.commons.permissions.PermissionRequestListener;
import com.imangazalievm.bubbble.presentation.commons.permissions.PermissionResult;
import com.imangazalievm.bubbble.presentation.commons.permissions.PermissionsManager;
import com.imangazalievm.bubbble.presentation.mvp.views.ShotDetailsView$$State;
import com.imangazalievm.bubbble.test.BubbbleTestRunner;
import com.imangazalievm.bubbble.test.TestRxSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(BubbbleTestRunner.class)
public class ShotDetailsPresenterTest {

    private static final Long TEST_SHOT_ID = 98419625L;
    private static final String TEST_SHOT_TITLE = "Test title";
    private static final String TEST_SHOT_URL = "https://test-url.com/516";
    private static final String TEST_SHOT_IMAGE_URL = "https://test-url.com/image.png";
    private static final Long TEST_USER_ID = 1962179L;

    @Mock
    ShotDetailsInteractor shotDetailsInteractorMock;
    @Mock
    ShotDetailsPresenter shotsDetailsViewMock;
    @Mock
    ShotDetailsView$$State shotDetailsViewStateMock;
    @Mock
    PermissionsManager permissionsManagerMock;

    private ShotDetailsPresenter shotDetailsPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        shotDetailsPresenter = new ShotDetailsPresenter(shotDetailsInteractorMock, new TestRxSchedulerProvider(), TEST_SHOT_ID);
        shotDetailsPresenter.setViewState(shotDetailsViewStateMock);
        shotDetailsPresenter.setPermissionsManager(permissionsManagerMock);
    }

    @Test
    public void shot_shouldLoadShotOnFirstAttach() {
        //arrange
        Shot shot = new Shot();
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        //act
        shotDetailsPresenter.onFirstViewAttach();

        // assert
        ArgumentCaptor<Long> shotsCaptor = ArgumentCaptor.forClass(Long.class);
        verify(shotDetailsInteractorMock).getShot(shotsCaptor.capture());
        assertEquals(TEST_SHOT_ID, shotsCaptor.getValue());
        verify(shotDetailsViewStateMock).showLoadingProgress();
        verify(shotDetailsViewStateMock).showShot(shot);
    }

    @Test
    public void shot_shouldShowNoNetworkLayout() {
        //arrange
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        shotDetailsPresenter.onFirstViewAttach();

        // assert
        verify(shotDetailsInteractorMock).getShot(TEST_SHOT_ID);
        verify(shotDetailsViewStateMock).hideLoadingProgress();
        verify(shotDetailsViewStateMock).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadShotAndShowProgress() {
        //arrange
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(new Shot()));

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.retryLoading();

        // assert
        verify(shotDetailsInteractorMock, times(2)).getShot(TEST_SHOT_ID);
        verify(shotDetailsViewStateMock).hideNoNetworkLayout();
        verify(shotDetailsViewStateMock, times(2)).showLoadingProgress();
    }

    @Test
    public void retryLoading_shouldHideProgressOnError() {
        //arrange
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        shotDetailsPresenter.onFirstViewAttach();

        // assert
        verify(shotDetailsInteractorMock, times(1)).getShot(TEST_SHOT_ID);
        verify(shotDetailsViewStateMock).hideLoadingProgress();
    }

    @Test
    public void onImageLoadError_shouldHideImageLoadingProgressOnError() {
        //arrange
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.onImageLoadError();

        // assert
        verify(shotDetailsInteractorMock, times(1)).getShot(TEST_SHOT_ID);
        verify(shotDetailsViewStateMock).hideImageLoadingProgress();
    }

    @Test
    public void onImageLoadError_shouldHideImageLoadingProgressOnSuccess() {
        //arrange
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(new Shot()));

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.onImageLoadSuccess();

        // assert
        verify(shotDetailsInteractorMock, times(1)).getShot(TEST_SHOT_ID);
        verify(shotDetailsViewStateMock).hideImageLoadingProgress();
    }

    @Test
    public void comments_shouldShowShowNoCommentsOnCommentsCountZero() {
        //arrange
        Shot shot = mock(Shot.class);
        when(shot.getCommentsCount()).thenReturn(0);
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(new Shot()));

        //act
        shotDetailsPresenter.onFirstViewAttach();

        // assert
        verify(shotDetailsViewStateMock).showNoComments();
    }

    @Test
    public void comments_shouldLoadCommentsOnShotLoaded() {
        //arrange
        Shot shot = mock(Shot.class);
        when(shot.getCommentsCount()).thenReturn(3);
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        List<Comment> comments = comments();
        when(shotDetailsInteractorMock.getShotComments(any(ShotCommentsRequestParams.class)))
                .thenReturn(Single.just(comments));

        //act
        shotDetailsPresenter.onFirstViewAttach();

        // assert
        verify(shotDetailsInteractorMock).getShot(TEST_SHOT_ID);
        verify(shotDetailsInteractorMock).getShotComments(any(ShotCommentsRequestParams.class));
        verify(shotDetailsViewStateMock).showNewComments(comments);
    }

    @Test
    public void onLoadMoreCommentsRequest_shouldCorrectLoadNextComments() {
        //arrange
        Shot shot = mock(Shot.class);
        when(shot.getCommentsCount()).thenReturn(3);
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        List<Comment> newComments = comments();
        when(shotDetailsInteractorMock.getShotComments(any(ShotCommentsRequestParams.class)))
                .thenReturn(Single.just(newComments));

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.onLoadMoreCommentsRequest();
        shotDetailsPresenter.onLoadMoreCommentsRequest();

        // assert
        ArgumentCaptor<ShotCommentsRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotCommentsRequestParams.class);
        verify(shotDetailsInteractorMock, times(3)).getShotComments(shotsCaptor.capture());
        List<ShotCommentsRequestParams> capturedRequestParams = shotsCaptor.getAllValues();
        assertEquals(1, capturedRequestParams.get(0).getPage());
        assertEquals(2, capturedRequestParams.get(1).getPage());
        assertEquals(3, capturedRequestParams.get(2).getPage());
        verify(shotDetailsViewStateMock, times(3)).showNewComments(newComments);
    }

    @Test
    public void onImageClick_shouldOpenShotImageScreenScreen() {
        //arrange
        Shot shot = new Shot();
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.onImageClicked();

        // assert
        verify(shotDetailsInteractorMock).getShot(TEST_SHOT_ID);
        verify(shotDetailsViewStateMock).openShotImageScreen(shot);
    }

    @Test
    public void onShareShotClick_shouldShowShotSharing() {
        //arrange
        Shot shot = mock(Shot.class);
        when(shot.getTitle()).thenReturn(TEST_SHOT_TITLE);
        when(shot.getHtmlUrl()).thenReturn(TEST_SHOT_URL);
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.onShareShotClicked();

        // assert
        verify(shotDetailsInteractorMock).getShot(TEST_SHOT_ID);
        verify(shotDetailsViewStateMock).showShotSharing(TEST_SHOT_TITLE, TEST_SHOT_URL);
    }

    @Test
    public void onDownloadImageClicked_shouldSaveImageOnPermissionGranted() {
        //arrange
        Answer<Void> permissionRequestAnswer = invocation -> {
            PermissionRequestListener listener = (PermissionRequestListener) invocation.getArguments()[1];
            listener.onResult(new PermissionResult(Permission.READ_EXTERNAL_STORAGE, true, true));
            return null;
        };

        doAnswer(permissionRequestAnswer)
                .when(permissionsManagerMock)
                .requestPermission(eq(Permission.READ_EXTERNAL_STORAGE), any(PermissionRequestListener.class));

        Images images = mock(Images.class);
        when(images.best()).thenReturn(TEST_SHOT_IMAGE_URL);
        Shot shot = mock(Shot.class);
        when(shot.getId()).thenReturn(TEST_SHOT_ID);
        when(shot.getImages()).thenReturn(images);
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));
        when(shotDetailsInteractorMock.saveImage(TEST_SHOT_IMAGE_URL))
                .thenReturn(Completable.complete());

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.onDownloadImageClicked();

        // assert
        verify(shotDetailsInteractorMock).saveImage(TEST_SHOT_IMAGE_URL);
    }

    @Test
    public void onDownloadImageClicked_shouldShowStorageAccessRationaleMessage() {
        //arrange
        Answer<Void> permissionRequestAnswer = invocation -> {
            PermissionRequestListener listener = (PermissionRequestListener) invocation.getArguments()[1];
            listener.onResult(new PermissionResult(Permission.READ_EXTERNAL_STORAGE, false, true));
            return null;
        };

        doAnswer(permissionRequestAnswer)
                .when(permissionsManagerMock)
                .requestPermission(eq(Permission.READ_EXTERNAL_STORAGE), any(PermissionRequestListener.class));

        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(new Shot()));

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.onDownloadImageClicked();

        // assert
        verify(shotDetailsViewStateMock).showStorageAccessRationaleMessage();
    }

    @Test
    public void onDownloadImageClicked_shouldShowAllowStorageAccessMessage() {
        //arrange
        Answer<Void> permissionRequestAnswer = invocation -> {
            PermissionRequestListener listener = (PermissionRequestListener) invocation.getArguments()[1];
            listener.onResult(new PermissionResult(Permission.READ_EXTERNAL_STORAGE, false, false));
            return null;
        };

        doAnswer(permissionRequestAnswer)
                .when(permissionsManagerMock)
                .requestPermission(eq(Permission.READ_EXTERNAL_STORAGE), any(PermissionRequestListener.class));

        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(new Shot()));

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.onDownloadImageClicked();

        // assert
        verify(shotDetailsViewStateMock).showAllowStorageAccessMessage();
    }

    @Test
    public void onAppSettingsButtonClicked_shouldOpenAppSettingsScreen() {
        //arrange
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(new Shot()));

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.onAppSettingsButtonClicked();

        // assert
        verify(shotDetailsViewStateMock).openAppSettingsScreen();
    }

    @Test
    public void onOpenShotInBrowserClicked_shouldOpenBrowserScreen() {
        //arrange
        Shot shot = mock(Shot.class);
        when(shot.getHtmlUrl()).thenReturn(TEST_SHOT_URL);
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.onOpenShotInBrowserClicked();

        // assert
        verify(shotDetailsViewStateMock).openInBrowser(TEST_SHOT_URL);
    }

    @Test
    public void onShotAuthorProfileClick_shouldOpenShotDetailsScreen() {
        //arrange
        User shotAuthor = mock(User.class);
        when(shotAuthor.getId()).thenReturn(TEST_USER_ID);
        Shot shot = mock(Shot.class);
        when(shot.getUser()).thenReturn(shotAuthor);
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.onShotAuthorProfileClicked();

        // assert
        verify(shotDetailsViewStateMock).openUserProfileScreen(TEST_USER_ID);
    }

    @Test
    public void onCommentAuthorClick_shouldOpenUserDetailsScreen() {
        //arrange
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(new Shot()));

        User commentAuthor = mock(User.class);
        when(commentAuthor.getId()).thenReturn(TEST_USER_ID);

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.onCommentAuthorClick(TEST_USER_ID);

        // assert
        verify(shotDetailsViewStateMock).openUserProfileScreen(TEST_USER_ID);
    }

    @Test
    public void onLinkClicked_shouldOpenBrowserScreen() {
        //arrange
        Shot shot = new Shot();
        when(shotDetailsInteractorMock.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        // act
        shotDetailsPresenter.onFirstViewAttach();
        shotDetailsPresenter.onLinkClicked(TEST_SHOT_URL);

        // assert
        verify(shotDetailsViewStateMock).openInBrowser(TEST_SHOT_URL);
    }

    private List<Comment> comments() {
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            comments.add(new Comment());
        }
        return comments;
    }

}
