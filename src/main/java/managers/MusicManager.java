package managers;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MusicManager {

    private static Clip currentClip;
    private static String currentResource;
    
    public static void playMenuTheme() {
        play("music/menu_theme.mp3");
    }
    
    public static void playDayStageTheme() {
        play("music/menu_theme.mp3");
    }

    public static void play(String resource) {
        if (resource.equals(currentResource) && currentClip != null && currentClip.isRunning())
            return;

        stop();
        currentResource = resource;

        try {
            InputStream is = MusicManager.class.getClassLoader().getResourceAsStream(resource);
            AudioInputStream mp3In = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            AudioFormat base = mp3In.getFormat();
            AudioFormat pcm = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                base.getSampleRate(), 16,
                base.getChannels(), base.getChannels() * 2,
                base.getSampleRate(), false);
            AudioInputStream pcmIn = AudioSystem.getAudioInputStream(pcm, mp3In);

            currentClip = AudioSystem.getClip();
            currentClip.open(pcmIn);
            currentClip.loop(Clip.LOOP_CONTINUOUSLY);
            currentClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void playSFX(String resource) {
        if (resource.equals(currentResource) && currentClip != null && currentClip.isRunning())
            return;

        currentResource = resource;

        try {
            InputStream is = MusicManager.class.getClassLoader().getResourceAsStream(resource);
            AudioInputStream mp3In = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
            AudioFormat base = mp3In.getFormat();
            AudioFormat pcm = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                base.getSampleRate(), 16,
                base.getChannels(), base.getChannels() * 2,
                base.getSampleRate(), false);
            AudioInputStream pcmIn = AudioSystem.getAudioInputStream(pcm, mp3In);

            currentClip = AudioSystem.getClip();
            currentClip.open(pcmIn);
            currentClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.close();
            currentClip = null;
        }
        currentResource = null;
    }

    public static void setVolume(float gain) {
        if (currentClip == null) return;
        FloatControl ctrl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);
        float clamped = Math.max(ctrl.getMinimum(), Math.min(ctrl.getMaximum(), gain));
        ctrl.setValue(clamped);
    }
}
