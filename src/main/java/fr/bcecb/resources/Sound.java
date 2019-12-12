package fr.bcecb.resources;

import fr.bcecb.util.Resources;
import org.lwjgl.openal.AL11;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.stb.STBVorbisInfo;

import java.io.IOException;
import java.io.InputStream;

import static org.lwjgl.system.MemoryUtil.NULL;

public class Sound implements IResource {
    private int id = 0;

    STBVorbisInfo info = STBVorbisInfo.create();

    public Sound() {

    }

    public void bind(int source) {
        AL11.alSourcei(source, AL11.AL_BUFFER, this.id);
    }

    public void unbind(int source) {
        AL11.alSourcei(source, AL11.AL_BUFFER, 0);
    }

    public STBVorbisInfo getInfo() {
        return info;
    }

    @Override
    public void load(InputStream inputStream) throws IOException {
        this.id = AL11.alGenBuffers();

        AL11.alBufferData(this.id, info.channels() == 1 ? AL11.AL_FORMAT_MONO16 : AL11.AL_FORMAT_STEREO16, readVorbis(inputStream, this.info), this.info.sample_rate());
    }

    @Override
    public void dispose() {
        AL11.alDeleteBuffers(this.id);
    }

    @Override
    public boolean validate() {
        return this.id > 0;
    }

    private static short[] readVorbis(InputStream inputStream, STBVorbisInfo info) throws IOException {
        int[] error = new int[1];
        long decoder = STBVorbis.stb_vorbis_open_memory(Resources.readByteBuffer(inputStream), error, null);

        if (decoder == NULL) {
            throw new RuntimeException("Failed to open Ogg Vorbis file. Error: " + error[0]);
        }

        STBVorbis.stb_vorbis_get_info(decoder, info);

        short[] pcm = new short[STBVorbis.stb_vorbis_stream_length_in_samples(decoder) * info.channels()];

        STBVorbis.stb_vorbis_get_samples_short_interleaved(decoder, info.channels(), pcm);
        STBVorbis.stb_vorbis_close(decoder);

        return pcm;
    }
}
