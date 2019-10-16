package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;

public class GuiRenderer extends Renderer<GuiState> {
    public GuiRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void render(GuiState object, double partialTick) {

    }
}
