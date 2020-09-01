package ru.pa4ok.library.javafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.pa4ok.library.javafx.form.InitableForm;
import ru.pa4ok.library.javafx.form.NoCacheForm;

import java.util.HashMap;
import java.util.Map;

public abstract class FxApplication extends Application
{
    private final Map<Class<? extends Parent>, Scene> formCache = new HashMap<>();

    protected Stage stage;
    private String title;
    private boolean resizable;
    private Parent root;

    protected void initUi(String title, boolean resizable, Parent root)
    {
        this.title = title;
        this.resizable = resizable;
        this.root = root;
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        this.stage = stage;
        stage.setTitle(title);
        stage.setResizable(resizable);
        changeScene(root);
        stage.show();
    }

    public void stopApp()
    {
        onAppStop();
        Platform.exit();
    }

    protected abstract void onAppStop();

    public void changeScene(Parent root, boolean updateCache)
    {
        Scene scene = checkCache(root, updateCache);
        scene.getStylesheets().add("style.css");
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void changeScene(Parent root)
    {
        changeScene(root, false);
    }

    private Scene checkCache(Parent root, boolean updateCache)
    {
        Class<? extends Parent> cls = root.getClass();

        if(cls.isAnnotationPresent(NoCacheForm.class)) {
            checkInit(root);
            return new Scene(root);
        }

        if(updateCache)
        {
            checkInit(root);
            Scene scene = new Scene(root);
            formCache.put(cls, scene);
            return scene;
        }

        Scene sceneFromCache = formCache.get(cls);
        if(sceneFromCache == null)
        {
            checkInit(root);
            Scene scene = new Scene(root);
            formCache.put(cls, scene);
            return scene;
        }

        return sceneFromCache;
    }

    private void checkInit(Parent root)
    {
        if(root instanceof InitableForm) {
            ((InitableForm)root).init();
        }
    }

    public Stage getStage() {
        return stage;
    }
}
