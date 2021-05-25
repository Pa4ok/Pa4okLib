package ru.pa4ok.library.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pa4ok.library.javafx.form.CachedForm;
import ru.pa4ok.library.javafx.form.UpdatableCachedForm;
import ru.pa4ok.library.util.Log4jUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class FxApplication extends Application
{
    static {
        Log4jUtil.configureLogging();
    }

    private static final Logger logger = LogManager.getLogger(FxApplication.class);

    private final Map<Class<? extends Parent>, Stage> formCache = new HashMap<>();

    protected Stage activeStage;

    @Override
    public void start(Stage stage) throws Exception
    {
        this.activeStage = stage;
        stage.setTitle(getInitialStageTitle());
        changeForm(getInitialStageRoot());
    }

    public void stopApp()
    {
        onAppStop();
        Platform.exit();
    }

    protected abstract String getInitialStageTitle();

    protected abstract Parent getInitialStageRoot();

    protected abstract void onAppStop();

    public boolean hasCacheForm(Class<? extends Parent> cls) {
        return formCache.containsKey(cls);
    }

    public void clearFormCache() {
        formCache.clear();
    }

    public void changeForm(Parent root)
    {
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);

        if(root instanceof CachedForm) {
            formCache.put(root.getClass(), stage);
        }

        changeForm(stage);
    }

    public void changeFormFromCache(Class<? extends Parent> cls) throws NoCachedStageException
    {
        long startMills = System.currentTimeMillis();

        Stage stage = formCache.get(cls);
        if(stage == null) {
            throw new NoCachedStageException(cls);
        }

        long updateMills = -1L;
        if(stage.getScene().getRoot() instanceof UpdatableCachedForm) {
            updateMills = System.currentTimeMillis();
            ((UpdatableCachedForm)stage.getScene().getRoot()).update();
            updateMills = System.currentTimeMillis() - updateMills;
        }

        changeForm(stage);

        logger.debug("Form '" + cls.getSimpleName() + "' load from cache by " + (System.currentTimeMillis() - startMills) + "ms" +
                (updateMills == -1 ? "" : " with update by " + updateMills + "ms"));
    }

    private void changeForm(Stage stage)
    {
        this.activeStage.hide();
        FxUtils.checkStageForMaximized(this.activeStage, stage);
        this.activeStage = stage;
        FxUtils.setStageSizesFromRootElement(this.activeStage);
        this.activeStage.show();
    }

    public Stage getActiveStage() {
        return activeStage;
    }
}
