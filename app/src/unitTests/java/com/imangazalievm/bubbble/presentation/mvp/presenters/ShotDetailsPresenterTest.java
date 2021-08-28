package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.imangazalievm.bubbble.domain.global.exceptions.NoNetworkException;
import com.imangazalievm.bubbble.domain.shotdetails.ShotDetailsInteractor;
import com.imangazalievm.bubbble.domain.global.models.Comment;
import com.imangazalievm.bubbble.domain.global.models.Images;
import com.imangazalievm.bubbble.domain.global.models.Shot;
import com.imangazalievm.bubbble.domain.global.models.ShotCommentsRequestParams;
import com.imangazalievm.bubbble.domain.global.models.User;
import com.imangazalievm.bubbble.presentation.global.SchedulersProvider;
import com.imangazalievm.bubbble.presentation.global.permissions.Permission;
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionRequestListener;
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionResult;
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionsManager;
import com.imangazalievm.bubbble.presentation.shotdetails.ShotDetailsPresenter;
import com.imangazalievm.bubbble.presentation.shotdetails.ShotDetailsView;
import com.imangazalievm.bubbble.test.BubbbleTestRunner;

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
    private ShotDetailsInteractor interactor;
    @Mock
    private ShotDetailsView view;
    @Mock
    private PermissionsManager permissionsManagerMock;
    private ShotDetailsPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new ShotDetailsPresenter(interactor, new SchedulersProvider(), TEST_SHOT_ID);
        presenter.setPermissionsManager(permissionsManagerMock);
    }

    @Test
    public void shot_shouldLoadShotOnFirstAttach() {
        //arrange
        Shot shot = new Shot();
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        //act
        presenter.attachView(view);

        // assert
        ArgumentCaptor<Long> shotsCaptor = ArgumentCaptor.forClass(Long.class);
        verify(interactor).getShot(shotsCaptor.capture());
        assertEquals(TEST_SHOT_ID, shotsCaptor.getValue());
        verify(view).showLoadingProgress();
        verify(view).showShot(shot);
    }

    @Test
    public void shot_shouldShowNoNetworkLayout() {
        //arrange
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);

        // assert
        verify(interactor).getShot(TEST_SHOT_ID);
        verify(view).hideLoadingProgress();
        verify(view).showNoNetworkLayout();
    }

    @Test
    public void retryLoading_shouldRetryLoadShotAndShowProgress() {
        //arrange
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.error(new NoNetworkException()))
                .thenReturn(Single.just(new Shot()));

        // act
        presenter.attachView(view);
        presenter.retryLoading();

        // assert
        verify(interactor, times(2)).getShot(TEST_SHOT_ID);
        verify(view).hideNoNetworkLayout();
        verify(view, times(2)).showLoadingProgress();
    }

    @Test
    public void retryLoading_shouldHideProgressOnError() {
        //arrange
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);

        // assert
        verify(interactor, times(1)).getShot(TEST_SHOT_ID);
        verify(view).hideLoadingProgress();
    }

    @Test
    public void onImageLoadError_shouldHideImageLoadingProgressOnError() {
        //arrange
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.error(new NoNetworkException()));

        // act
        presenter.attachView(view);
        presenter.onImageLoadError();

        // assert
        verify(interactor, times(1)).getShot(TEST_SHOT_ID);
        verify(view).hideImageLoadingProgress();
    }

    @Test
    public void onImageLoadError_shouldHideImageLoadingProgressOnSuccess() {
        //arrange
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(new Shot()));

        // act
        presenter.attachView(view);
        presenter.onImageLoadSuccess();

        // assert
        verify(interactor, times(1)).getShot(TEST_SHOT_ID);
        verify(view).hideImageLoadingProgress();
    }

    @Test
    public void comments_shouldShowShowNoCommentsOnCommentsCountZero() {
        //arrange
        Shot shot = mock(Shot.class);
        when(shot.getCommentsCount()).thenReturn(0);
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(new Shot()));

        //act
        presenter.attachView(view);

        // assert
        verify(view).showNoComments();
    }

    @Test
    public void comments_shouldLoadCommentsOnShotLoaded() {
        //arrange
        Shot shot = mock(Shot.class);
        when(shot.getCommentsCount()).thenReturn(3);
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        List<Comment> comments = comments();
        when(interactor.getShotComments(any(ShotCommentsRequestParams.class)))
                .thenReturn(Single.just(comments));

        //act
        presenter.attachView(view);

        // assert
        verify(interactor).getShot(TEST_SHOT_ID);
        verify(interactor).getShotComments(any(ShotCommentsRequestParams.class));
        verify(view).showNewComments(comments);
    }

    @Test
    public void onLoadMoreCommentsRequest_shouldCorrectLoadNextComments() {
        //arrange
        Shot shot = mock(Shot.class);
        when(shot.getCommentsCount()).thenReturn(3);
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        List<Comment> newComments = comments();
        when(interactor.getShotComments(any(ShotCommentsRequestParams.class)))
                .thenReturn(Single.just(newComments));

        // act
        presenter.attachView(view);
        presenter.onLoadMoreCommentsRequest();
        presenter.onLoadMoreCommentsRequest();

        // assert
        ArgumentCaptor<ShotCommentsRequestParams> shotsCaptor = ArgumentCaptor.forClass(ShotCommentsRequestParams.class);
        verify(interactor, times(3)).getShotComments(shotsCaptor.capture());
        List<ShotCommentsRequestParams> capturedRequestParams = shotsCaptor.getAllValues();
        assertEquals(1, capturedRequestParams.get(0).getPage());
        assertEquals(2, capturedRequestParams.get(1).getPage());
        assertEquals(3, capturedRequestParams.get(2).getPage());
        verify(view, times(3)).showNewComments(newComments);
    }

    @Test
    public void onImageClick_shouldOpenShotImageScreenScreen() {
        //arrange
        Shot shot = new Shot();
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        // act
        presenter.attachView(view);
        presenter.onImageClicked();

        // assert
        verify(interactor).getShot(TEST_SHOT_ID);
        verify(view).openShotImageScreen(shot);
    }

    @Test
    public void onShareShotClick_shouldShowShotSharing() {
        //arrange
        Shot shot = mock(Shot.class);
        when(shot.getTitle()).thenReturn(TEST_SHOT_TITLE);
        when(shot.getHtmlUrl()).thenReturn(TEST_SHOT_URL);
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        // act
        presenter.attachView(view);
        presenter.onShareShotClicked();

        // assert
        verify(interactor).getShot(TEST_SHOT_ID);
        verify(view).showShotSharing(TEST_SHOT_TITLE, TEST_SHOT_URL);
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
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));
        when(interactor.saveImage(TEST_SHOT_IMAGE_URL))
                .thenReturn(Completable.complete());

        // act
        presenter.attachView(view);
        presenter.onDownloadImageClicked();

        // assert
        verify(interactor).saveImage(TEST_SHOT_IMAGE_URL);
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

        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(new Shot()));

        // act
        presenter.attachView(view);
        presenter.onDownloadImageClicked();

        // assert
        verify(view).showStorageAccessRationaleMessage();
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

        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(new Shot()));

        // act
        presenter.attachView(view);
        presenter.onDownloadImageClicked();

        // assert
        verify(view).showAllowStorageAccessMessage();
    }

    @Test
    public void onAppSettingsButtonClicked_shouldOpenAppSettingsScreen() {
        //arrange
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(new Shot()));

        // act
        presenter.attachView(view);
        presenter.onAppSettingsButtonClicked();

        // assert
        verify(view).openAppSettingsScreen();
    }

    @Test
    public void onOpenShotInBrowserClicked_shouldOpenBrowserScreen() {
        //arrange
        Shot shot = mock(Shot.class);
        when(shot.getHtmlUrl()).thenReturn(TEST_SHOT_URL);
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        // act
        presenter.attachView(view);
        presenter.onOpenShotInBrowserClicked();

        // assert
        verify(view).openInBrowser(TEST_SHOT_URL);
    }

    @Test
    public void onShotAuthorProfileClick_shouldOpenShotDetailsScreen() {
        //arrange
        User shotAuthor = mock(User.class);
        when(shotAuthor.getId()).thenReturn(TEST_USER_ID);
        Shot shot = mock(Shot.class);
        when(shot.getUser()).thenReturn(shotAuthor);
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        // act
        presenter.attachView(view);
        presenter.onShotAuthorProfileClicked();

        // assert
        verify(view).openUserProfileScreen(TEST_USER_ID);
    }

    @Test
    public void onCommentAuthorClick_shouldOpenUserDetailsScreen() {
        //arrange
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(new Shot()));

        User commentAuthor = mock(User.class);
        when(commentAuthor.getId()).thenReturn(TEST_USER_ID);

        // act
        presenter.attachView(view);
        presenter.onCommentAuthorClick(TEST_USER_ID);

        // assert
        verify(view).openUserProfileScreen(TEST_USER_ID);
    }

    @Test
    public void onLinkClicked_shouldOpenBrowserScreen() {
        //arrange
        Shot shot = new Shot();
        when(interactor.getShot(TEST_SHOT_ID))
                .thenReturn(Single.just(shot));

        // act
        presenter.attachView(view);
        presenter.onLinkClicked(TEST_SHOT_URL);

        // assert
        verify(view).openInBrowser(TEST_SHOT_URL);
    }

    private List<Comment> comments() {
        List<Comment> comments = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            comments.add(new Comment());
        }
        return comments;
    }

}
