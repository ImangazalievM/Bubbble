package com.imangazalievm.bubbble.presentation.mvp.presenters;

import com.imangazalievm.bubbble.domain.shotzoom.ShotZoomInteractor;
import com.imangazalievm.bubbble.domain.global.models.Images;
import com.imangazalievm.bubbble.presentation.global.permissions.PermissionsManager;
import com.imangazalievm.bubbble.presentation.shotzoom.ShotZoomPresenter;
import com.imangazalievm.bubbble.presentation.shotzoom.ShotZoomView;
import com.imangazalievm.bubbble.test.BubbbleTestRunner;
import com.imangazalievm.bubbble.test.TestSchedulersProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import io.reactivex.Completable;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(BubbbleTestRunner.class)
public class ShotZoomPresenterTest {

    private static final String TEST_SHOT_TITLE = "Test title";
    private static final String TEST_SHOT_URL = "https://test-url.com/516";
    private static final String TEST_SHOT_IMAGE_URL = "https://test-url.com/image.png";

    @Mock
    private ShotZoomInteractor interactor;
    @Mock
    private ShotZoomView view;
    @Mock
    private PermissionsManager permissionsManager;
    private ShotZoomPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new ShotZoomPresenter(interactor, new TestSchedulersProvider(),
                TEST_SHOT_TITLE, TEST_SHOT_URL, TEST_SHOT_IMAGE_URL);
        presenter.setPermissionsManager(permissionsManager);
    }

    @Test
    public void shot_shouldLoadShotOnFirstAttach() {
        //arrange

        //act
        presenter.attachView(view);

        // assert
        verify(view).showLoadingProgress();
        verify(view).showShotImage(TEST_SHOT_IMAGE_URL);
    }

    @Test
    public void onImageLoadSuccess_shouldHideProgress() {
        //arrange

        // act
        presenter.attachView(view);
        presenter.onImageLoadSuccess();

        // assert
        verify(view).hideLoadingProgress();
    }

    @Test
    public void onImageLoadError_shouldShowErrorLayout() {
        //arrange

        // act
        presenter.attachView(view);
        presenter.onImageLoadError();

        // assert
        verify(view).hideLoadingProgress();
        verify(view).showErrorLayout();
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
                .when(permissionsManager)
                .requestPermission(eq(Permission.READ_EXTERNAL_STORAGE), any(PermissionRequestListener.class));

        Images images = mock(Images.class);
        when(images.best()).thenReturn(TEST_SHOT_IMAGE_URL);
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
                .when(permissionsManager)
                .requestPermission(eq(Permission.READ_EXTERNAL_STORAGE), any(PermissionRequestListener.class));

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
                .when(permissionsManager)
                .requestPermission(eq(Permission.READ_EXTERNAL_STORAGE), any(PermissionRequestListener.class));

        // act
        presenter.attachView(view);
        presenter.onDownloadImageClicked();

        // assert
        verify(view).showAllowStorageAccessMessage();
    }

    @Test
    public void onAppSettingsButtonClicked_shouldOpenAppSettingsScreen() {
        //arrange

        // act
        presenter.attachView(view);
        presenter.onAppSettingsButtonClicked();

        // assert
        verify(view).openAppSettingsScreen();
    }


    @Test
    public void onShareShotClick_shouldShowShotSharing() {
        //arrange

        // act
        presenter.attachView(view);
        presenter.onShareShotClicked();

        // assert
        verify(view).showShotSharing(TEST_SHOT_TITLE, TEST_SHOT_URL);
    }

    @Test
    public void onOpenShotInBrowserClicked_shouldOpenBrowserScreen() {
        //arrange

        // act
        presenter.attachView(view);
        presenter.onOpenInBrowserClicked();

        // assert
        verify(view).openInBrowser(TEST_SHOT_URL);
    }

}
