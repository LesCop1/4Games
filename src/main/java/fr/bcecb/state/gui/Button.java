package fr.bcecb.state.gui;

import fr.bcecb.input.MouseEvent;
import fr.bcecb.render.Renderer;

public class Button extends GuiElement {

    @Override
    protected void onClick(MouseEvent.Click event) {

    }

    @Override
    protected void onHover(MouseEvent.Move event) {

    }

    @Override
    protected void onScroll(MouseEvent.Scroll event) {

    }

    public static class ButtonRenderer extends Renderer<Button> {

        @Override
        public void render(Button object, double partialTick) {

        }
    }
}