package net.happyspeed.regenerating_world.util;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES =
        DeferredRegister.create(Registries.ATTRIBUTE, RegeneratingWorld.MODID);


    public static final DeferredHolder<Attribute, Attribute> AIR_MOBILITY =
            ATTRIBUTES.register("air_mobility", () ->
                    new RangedAttribute("attribute.name.regenerating_world.air_mobility",
                            0.0D, 0.0D, 1.0D
                    ).setSyncable(true));


}
