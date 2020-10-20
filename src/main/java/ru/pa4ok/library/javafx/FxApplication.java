package ru.pa4ok.library.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.pa4ok.library.javafx.form.NoCacheForm;
import ru.pa4ok.library.javafx.form.UpdatableForm;
import ru.pa4ok.library.javafx.util.FxUtils;
import ru.pa4ok.library.util.Log4jUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class FxApplication extends Application
{
    static {
        Log4jUtil.configureLogging();
    }

    private static final Logger logger = LogManager.getLogger(FxApplication.class);

    private final Map<Class<? extends Parent>, Scene> formCache = new HashMap<>();

    protected Stage stage;
    private String title;
    private Parent root;

    protected void initUi(String title, Parent root)
    {
        this.title = title;
        this.root = root;
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        this.stage = stage;
        stage.setTitle(title);
        changeForm(root);
        stage.show();
    }

    public void stopApp()
    {
        onAppStop();
        Platform.exit();
    }

    protected abstract void onAppStop();

    public boolean hasCacheForm(Class<? extends Parent> cls) {
        return formCache.containsKey(cls);
    }

    public void clearFormCache() {
        formCache.clear();
    }

    public void changeForm(Parent root)
    {
        Class<? extends Parent> cls = root.getClass();
        Scene scene = new Scene(root);

        if(!cls.isAnnotationPresent(NoCacheForm.class)) {
            formCache.put(cls, scene);
        }

        changeForm(scene);
    }

    public void changeFormFromCache(Class<? extends Parent> cls) throws NoCachedSceneException
    {
        Scene scene = formCache.get(cls);
        if(scene == null) {
            throw new NoCachedSceneException(cls);
        }

        if(scene.getRoot() instanceof UpdatableForm) {
            long startMills = System.currentTimeMillis();
            ((UpdatableForm)scene.getRoot()).update();
            logger.debug("Form '" + cls.getSimpleName() + "' update from cache by " + (System.currentTimeMillis() - startMills) + "ms");
        }

        changeForm(scene);
    }

    private void changeForm(Scene scene)
    {
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        FxUtils.setStageSizesFromRootElement(stage);
        stage.centerOnScreen();
    }

    public Stage getStage() {
        return stage;
    }
}
