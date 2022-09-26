package it.uniba.app.avventura;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;


/**
 * <p>Music class.</p>
 *
 * @author Mario Venezia
 * @version $Id: $Id
 */
public class Music implements Runnable {
    /**
     * <p>playMusic.</p>
     *This method load and play the music.
     *
     * @param filepath a {@link java.lang.String} object.
     */
    public static void playMusic(String filepath) {
        try {
            File music = new File(filepath);
            if(music.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(music);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-15.0f); // Reduce volume by 10 decibels.
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        }


    /**
     * {@inheritDoc}
     *
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     * @see Thread#run()
     */
    @Override
    public void run() {
        playMusic("./resources/music/horror.wav");
    }
}
