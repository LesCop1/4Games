package fr.bcecb.state.gui;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import fr.bcecb.event.MouseEvent;
import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.RenderManager;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.Texture;
import fr.bcecb.util.Constants;

public class Button extends GuiElement {
    private String title;
    private float titleScale;
    private ResourceHandle<Texture> texture;
    private ResourceHandle<Texture> onHoverTexture;
    private float ticksHovered = 0;

    public Button(int id, float x, float y, float width, float height) {
        this(id, x, y, width, height, false);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered) {
        this(id, x, y, width, height, centered, (String) null);
    }

    public Button(int id, float x, float y, float width, float height, String title) {
        this(id, x, y, width, height, false, title, 1.0f, null);
    }

    public Button(int id, float x, float y, float width, float height, String title, float titleScale) {
        this(id, x, y, width, height, false, title, titleScale, null);
    }

    public Button(int id, float x, float y, float width, float height, ResourceHandle<Texture> texture) {
        this(id, x, y, width, height, false, null, 1.0f, texture);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title) {
        this(id, x, y, width, height, centered, title, 1.0f, null);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title, float titleScale) {
        this(id, x, y, width, height, centered, title, titleScale, null);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, ResourceHandle<Texture> texture) {
        this(id, x, y, width, height, centered, null, 1.0f, texture);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title, ResourceHandle<Texture> texture) {
        this(id, x, y, width, height, centered, title, 1.0f, texture, texture);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title, float titleScale, ResourceHandle<Texture> texture) {
        this(id, x, y, width, height, centered, title, titleScale, texture, texture);
    }

    public Button(int id, float x, float y, float width, float height, boolean centered, String title, float titleScale, ResourceHandle<Texture> texture, ResourceHandle<Texture> onHoverTexture) {
        super(id, x - (centered ? (width / 2) : 0), y - (centered ? (height / 2) : 0), width, height);
        this.title = title;
        this.titleScale = Math.min(titleScale, 1.0f);
        this.texture = MoreObjects.firstNonNull(texture, Constants.DEFAULT_TEXTURE);
        this.onHoverTexture = MoreObjects.firstNonNull(onHoverTexture, Constants.DEFAULT_TEXTURE);
    }

    public float getHoveredTicks() {
        return ticksHovered;
    }

    public void incHoveredTicks() {
        this.ticksHovered++;
    }

    public void decHoveredTicks() {
        if (this.ticksHovered != 0) {
            this.ticksHovered--;
        }
    }

    public void setTicksHovered(float ticksHovered) {
        this.ticksHovered = ticksHovered;
    }

    @Override
    public void onClick(MouseEvent.Click event) {
    }

    @Override
    public void onHover(MouseEvent.Move event) {

    }

    @Override
    public void onScroll(MouseEvent.Scroll event) {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getTitleScale() {
        return titleScale;
    }

    public void setTitleScale(float titleScale) {
        this.titleScale = titleScale;
    }

    public ResourceHandle<Texture> getTexture() {
        return texture;
    }

    public void setTexture(ResourceHandle<Texture> texture) {
        this.texture = texture;
    }

    public ResourceHandle<Texture> getOnHoverTexture() {
        return onHoverTexture;
    }

    public void setOnHoverTexture(ResourceHandle<Texture> onHoverTexture) {
        this.onHoverTexture = onHoverTexture;
    }

    public static class ButtonRenderer extends Renderer<Button> {

        public ButtonRenderer(RenderManager renderManager) {
            super(renderManager);
        }

        @Override
        public ResourceHandle<Texture> getTexture(Button button) {
            return button.isHovered() ? button.getOnHoverTexture() : button.getTexture();
        }

        @Override
        public void render(Button button, float partialTick) {
            RenderEngine engine = renderManager.getRenderEngine();

            float effect = bouncyEffect(button, 5, 5, 3);

            engine.drawRect(getTexture(button), button.getX() - effect, button.getY() - effect, button.getX() + button.getWidth() + effect, button.getY() + button.getHeight() + effect);

            if (!Strings.isNullOrEmpty(button.getTitle())) {
                engine.drawCenteredText(Constants.DEFAULT_FONT, button.getTitle(), button.getX() + (button.getWidth() / 2.0f), button.getY() + (button.getHeight() / 2.0f), button.getTitleScale());
            }
        }

        private float bouncyEffect(Button button, int offset, int bouncePower, int bounceSpeed) {
            boolean bounce = true;
            if (button.isEnabled() && button.isHovered()) {
                if (button.getHoveredTicks() < (180 / ((float) bounceSpeed)) + offset) {
                    button.incHoveredTicks();
                } else {
                    button.setTicksHovered(offset);
                }
            } else {
                bounce = false;
                if (button.getHoveredTicks() < (offset + bouncePower)) {
                    button.decHoveredTicks();
                } else {
                    button.setTicksHovered((float) Math.floor(offset + (bouncePower * Math.sin(Math.toRadians((button.getHoveredTicks() - offset) * bounceSpeed)))));
                }
            }

            if ((button.getHoveredTicks() < offset) || !bounce) {
                return button.getHoveredTicks();
            } else {
                return (float) (offset + (bouncePower * Math.sin(Math.toRadians((button.getHoveredTicks() - offset) * bounceSpeed))));
            }
        }
    }
}