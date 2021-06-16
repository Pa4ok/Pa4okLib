package ru.pa4ok.library.javafx;

import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;

public class DragResizer
{
    private static final int RESIZE_MARGIN = 5;

    private final Region region;

    private double y;

    private boolean initMinHeight;

    private double maxHeight;

    private boolean dragging;

    private DragResizer(Region region, double maxHeight) {
        this.region = region;
        this.maxHeight = maxHeight;
    }

    public static void makeResizable(Region region, double maxHeight) {
        final DragResizer resizer = new DragResizer(region, maxHeight);
        region.setOnMousePressed(event -> resizer.mousePressed(event));
        region.setOnMouseDragged(event -> resizer.mouseDragged(event));
        region.setOnMouseMoved(event -> resizer.mouseOver(event));
        region.setOnMouseReleased(event -> resizer.mouseReleased(event));
    }

    public static void makeResizable(Region region) {
        makeResizable(region, -1D);
    }

    protected void mouseReleased(MouseEvent e) {
        dragging = false;
        region.setCursor(Cursor.DEFAULT);
    }

    protected void mouseOver(MouseEvent event) {
        if(isInDraggableZone(event) || dragging) {
            region.setCursor(Cursor.S_RESIZE);
        }
        else {
            region.setCursor(Cursor.DEFAULT);
        }
    }

    protected boolean isInDraggableZone(MouseEvent e) {
        return e.getY() > (region.getHeight() - RESIZE_MARGIN) ;
    }

    protected void mouseDragged(MouseEvent e) {
        if(!dragging) {
            return;
        }

        double mouseY = e.getY();

        double newHeight = region.getMinHeight() + (mouseY - y);

        if(maxHeight != -1 && newHeight > maxHeight) {
            return;
        }

        region.setMinHeight(newHeight);
        y = mouseY;
    }

    protected void mousePressed(MouseEvent e) {

        if(!isInDraggableZone(e)) {
            return;
        }

        dragging = true;

        if (!initMinHeight) {
            region.setMinHeight(region.getHeight());
            initMinHeight = true;
        }

        y = e.getY();
    }
}