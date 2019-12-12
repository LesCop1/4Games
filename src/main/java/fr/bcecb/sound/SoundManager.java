package fr.bcecb.sound;

import fr.bcecb.resources.ResourceHandle;
import fr.bcecb.resources.ResourceManager;
import fr.bcecb.resources.Sound;
import org.lwjgl.openal.*;

import java.nio.ByteBuffer;

public class SoundManager implements AutoCloseable {
    private final ResourceManager resourceManager;

    private final long device;

    private final long context;

    private final int source;

    public SoundManager(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;

        this.device = ALC11.alcOpenDevice((ByteBuffer) null);

        ALCCapabilities deviceCaps = ALC.createCapabilities(device);

        this.context = ALC11.alcCreateContext(device, (int[]) null);
        ALC11.alcMakeContextCurrent(context);

        AL.createCapabilities(deviceCaps);

        this.source = AL11.alGenSources();
    }

    public void playSound(ResourceHandle<Sound> soundResourceHandle) {
        if (soundResourceHandle != null) {
            Sound sound = resourceManager.getResource(soundResourceHandle);

            if (sound != null) {
                sound.bind(this.source);
                {
                    AL11.alSourcePlay(this.source);
                }
                sound.unbind(this.source);
            }
        }
    }

    public void stopSound() {
        AL11.alSourceStop(this.source);
    }

    @Override
    public void close() {
        AL11.alSourceStop(this.source);
        AL11.alDeleteSources(this.source);

        ALC11.alcMakeContextCurrent(0);
        ALC11.alcDestroyContext(this.context);
        ALC11.alcCloseDevice(this.device);
    }
}
