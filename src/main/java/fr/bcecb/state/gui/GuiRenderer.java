package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Log;

public class GuiRenderer extends Renderer<ScreenState> {
    public GuiRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceHandle<Texture> getTexture(ScreenState state) {
        return null;
    }

    @Override
    public void render(ScreenState state, float partialTick) {
        for (GuiElement element : state.getGuiElements()) {
            Renderer<GuiElement> guiElementRenderer = renderManager.getRendererFor(element);

            if (guiElementRenderer != null) {
                guiElementRenderer.render(element, partialTick);
            } else {
                Log.RENDER.warning("GUI element {0} has no renderer !", element.getClass());
            }
        }
    }
}
