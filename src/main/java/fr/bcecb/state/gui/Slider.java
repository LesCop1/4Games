package fr.bcecb.state.gui;

import fr.bcecb.event.MouseEvent;

public class Slider extends GuiElement {
    private int value = 0;
    private int maxValue = 99;

    public Slider(int id, float x, float y, float width, float height) {
        super(id, x, y, width, height);
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onClick(MouseEvent.Click event) {
        float xMinValue = getX() + (getHeight() * getValue());
        float yMinValue = getY();
        float xMaxValue = xMinValue + getHeight();
        float yMaxValue = yMinValue + getHeight();

        float xEvent = event.getX();
        float yEvent = event.getY();

        if (xMinValue < xEvent && xEvent < xMaxValue &&
            yMinValue < yEvent && yEvent < yMaxValue) {
            System.out.println("e");
        }
    }

    @Override
    public void onHover(MouseEvent.Move event) {

    }

    @Override
    public void onScroll(MouseEvent.Scroll event) {

    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
