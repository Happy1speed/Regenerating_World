package net.happyspeed.regenerating_world.sounds;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, RegeneratingWorld.MODID);

    public static final Supplier<SoundEvent> HUGE_IMPACT = registerSound("huge_impact");
    public static final Supplier<SoundEvent> METAL_REVERB_1 = registerSound("metal_reverb_1");
    public static final Supplier<SoundEvent> METAL_REVERB_2 = registerSound("metal_reverb_2");

    public static final Supplier<SoundEvent> REVERB_BREAK = registerSound("reverb_break");

    public static final Supplier<SoundEvent> MYSTERY_ARP = registerSound("mystery_arp");

    public static final Supplier<SoundEvent> MUST_SEND_DATA_1 = registerSound("must_send_data_1");
    public static final Supplier<SoundEvent> MUST_SEND_DATA_2 = registerSound("must_send_data_2");
    public static final Supplier<SoundEvent> MUST_SEND_DATA_3 = registerSound("must_send_data_3");
    public static final Supplier<SoundEvent> MUST_SEND_DATA_4 = registerSound("must_send_data_4");
    public static final Supplier<SoundEvent> MUST_SEND_DATA_5 = registerSound("must_send_data_5");
    public static final Supplier<SoundEvent> MUST_SEND_DATA_6 = registerSound("must_send_data_6");

    public static SoundEvent getRandomDataSound(RandomSource source) {
        int randSound = source.nextInt(5);
        switch (randSound) {
            case 0:
                return MUST_SEND_DATA_1.get();
            case 1:
                return MUST_SEND_DATA_2.get();
            case 2:
                return MUST_SEND_DATA_3.get();
            case 3:
                return MUST_SEND_DATA_4.get();
            case 4:
                return MUST_SEND_DATA_5.get();
            case 5:
                return MUST_SEND_DATA_6.get();
        }
        return MUST_SEND_DATA_1.get();
    }

    public static final Supplier<SoundEvent> AMBIENT_WIND =
            SOUND_EVENTS.register("ambient_wind",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "ambient_wind")));

//    public static final Supplier<SoundEvent> MUST_SEND_DATA =
//            SOUND_EVENTS.register("must_send_data",
//                    () -> SoundEvent.createVariableRangeEvent(
//                            ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, "must_send_data")));

    private static Supplier<SoundEvent> registerSound(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(RegeneratingWorld.MODID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }
}

