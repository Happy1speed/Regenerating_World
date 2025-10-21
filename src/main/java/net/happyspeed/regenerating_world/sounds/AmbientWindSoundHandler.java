package net.happyspeed.regenerating_world.sounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;

public class AmbientWindSoundHandler {
    private static SoundInstance currentAmbient;

    public static void playAmbientIfNotPlaying() {
        var mc = Minecraft.getInstance();
        var soundManager = mc.getSoundManager();

        if (currentAmbient != null && soundManager.isActive(currentAmbient)) {
            return;
        }
        currentAmbient = SimpleSoundInstance.forAmbientAddition(ModSounds.AMBIENT_WIND.get());
        soundManager.play(currentAmbient);
    }
}
