package fr.bcecb.state.gui;

import fr.bcecb.render.RenderManager;
import fr.bcecb.render.animation.Animator;
import fr.bcecb.render.animation.BounceAnimation;
import org.joml.Matrix4f;

public class ButtonAnimator extends Animator<Button> {
    private final BounceAnimation bounce = new BounceAnimation(5, 5, 3);

    public ButtonAnimator(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void animate(Button button, Matrix4f transform, float partialTick) {
        float bounceValue = bounce.getInterpolatedValue(partialTick);
        transform.scaleXY(bounceValue, bounceValue);
    }
}