package fr.bcecb.render.animation;

import fr.bcecb.render.IRenderable;
import fr.bcecb.render.RenderEngine;
import fr.bcecb.render.Renderer;
import fr.bcecb.resources.ResourceManager;

import java.util.HashMap;
import java.util.Map;

public class AnimationManager {
    private final RenderEngine renderEngine;
    private final ResourceManager resourceManager;
    private final Map<Class<? extends IAnimatable>, Animator<? extends IAnimatable>> animators = new HashMap<>();

    public AnimationManager(RenderEngine renderEngine, ResourceManager resourceManager) {
        this.renderEngine = renderEngine;
        this.resourceManager = resourceManager;
    }

    private <T extends IAnimatable> void register(Class<T> clazz, Animator<T> animator) {
        animators.put(clazz, animator);
    }

    public <T extends IAnimatable, A extends Animator<T>> A getAnimatorFor(T object) {
        return (A) getAnimatorFor(object.getClass());
    }

    private <T extends IAnimatable, A extends Animator<T>> A getAnimatorFor(Class<? extends IAnimatable> clazz) {
        A animator = (A) this.animators.get(clazz);
        if (animator == null && clazz != IRenderable.class) {
            animator = this.getAnimatorFor((Class<? extends IAnimatable>) clazz.getSuperclass());
            this.animators.put(clazz, animator);
        }

        return animator;
    }

    public RenderEngine getRenderEngine() {
        return renderEngine;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }
}