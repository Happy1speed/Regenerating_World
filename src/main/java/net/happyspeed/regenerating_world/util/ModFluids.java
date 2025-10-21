package net.happyspeed.regenerating_world.util;

import net.happyspeed.regenerating_world.RegeneratingWorld;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid.Properties;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModFluids {

    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, RegeneratingWorld.MODID);
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, RegeneratingWorld.MODID);
    public static final DeferredRegister<Item> BUCKETS = DeferredRegister.createItems(RegeneratingWorld.MODID);
    public static final DeferredRegister<Block> SOURCEBLOCKS = DeferredRegister.createBlocks(RegeneratingWorld.MODID);

    public static final DeferredHolder<FluidType, FluidType> SPRUCE_SAP_LIQUID_TYPE = FLUID_TYPES.register("spruce_sap_liquid", () -> new FluidType(FluidType.Properties.create().descriptionId("fluid.regenerating_world.spruce_sap_liquid")));

    public static final DeferredHolder<Fluid, FlowingFluid> SPRUCE_SAP_LIQUID_SOURCE = FLUIDS.register("spruce_sap_liquid_source", () -> new BaseFlowingFluid.Source(liquidPropertiesSpruceSap()));
    public static final DeferredHolder<Fluid, FlowingFluid> SPRUCE_SAP_LIQUID_FLOWING = FLUIDS.register("spruce_sap_liquid_flowing", () -> new BaseFlowingFluid.Flowing(liquidPropertiesSpruceSap()));

    public static final DeferredHolder<Item, BucketItem> SPRUCE_SAP_LIQUID_BUCKET = BUCKETS.register("spruce_sap_liquid_bucket", () -> new BucketItem(SPRUCE_SAP_LIQUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final DeferredHolder<Block, LiquidBlock> SPRUCE_SAP_LIQUID_BLOCK = SOURCEBLOCKS.register("spruce_sap_liquid_block", () -> new LiquidBlock(SPRUCE_SAP_LIQUID_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));



    public static final DeferredHolder<FluidType, FluidType> APPULAR_CIDER_ACID_LIQUID_TYPE = FLUID_TYPES.register("appular_cider_acid_liquid", () -> new FluidType(FluidType.Properties.create().descriptionId("fluid.regenerating_world.appular_cider_acid_liquid")));

    public static final DeferredHolder<Fluid, FlowingFluid> APPULAR_CIDER_ACID_LIQUID_SOURCE = FLUIDS.register("appular_cider_acid_liquid_source", () -> new BaseFlowingFluid.Source(liquidPropertiesAppular()));
    public static final DeferredHolder<Fluid, FlowingFluid> APPULAR_CIDER_ACID_LIQUID_FLOWING = FLUIDS.register("appular_cider_acid_liquid_flowing", () -> new BaseFlowingFluid.Flowing(liquidPropertiesAppular()));

    public static final DeferredHolder<Item, BucketItem> APPULAR_CIDER_ACID_LIQUID_BUCKET = BUCKETS.register("appular_cider_acid_liquid_bucket", () -> new BucketItem(APPULAR_CIDER_ACID_LIQUID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final DeferredHolder<Block, LiquidBlock> APPULAR_CIDER_ACID_LIQUID_BLOCK = SOURCEBLOCKS.register("appular_cider_acid_liquid_block", () -> new LiquidBlock(APPULAR_CIDER_ACID_LIQUID_SOURCE.get(), BlockBehaviour.Properties.ofFullCopy(Blocks.WATER)));


    public static void register(IEventBus modbus) {
        FLUID_TYPES.register(modbus);
        FLUIDS.register(modbus);
        BUCKETS.register(modbus);
        SOURCEBLOCKS.register(modbus);
        modbus.addListener(ModFluids::clientExt);
    }

    private static final IClientFluidTypeExtensions liquidExt = new IClientFluidTypeExtensions() {
        @Override
        public ResourceLocation getStillTexture() {
            return RegeneratingWorld.rl("block/spruce_sap_still");
        }

        @Override
        public ResourceLocation getFlowingTexture() {
            return RegeneratingWorld.rl("block/spruce_sap_flow");
        }
    };

    private static final IClientFluidTypeExtensions liquidExt2 = new IClientFluidTypeExtensions() {
        @Override
        public ResourceLocation getStillTexture() {
            return RegeneratingWorld.rl("block/appular_cider_acid_still");
        }

        @Override
        public ResourceLocation getFlowingTexture() {
            return RegeneratingWorld.rl("block/appular_cider_acid_flow");
        }
    };

    private static void clientExt(RegisterClientExtensionsEvent event) {
        event.registerFluidType(liquidExt, SPRUCE_SAP_LIQUID_TYPE.get());
        event.registerFluidType(liquidExt2, APPULAR_CIDER_ACID_LIQUID_TYPE.get());
    }

    private static Properties liquidPropertiesSpruceSap() {
        return new Properties(SPRUCE_SAP_LIQUID_TYPE, SPRUCE_SAP_LIQUID_SOURCE, SPRUCE_SAP_LIQUID_FLOWING).bucket(SPRUCE_SAP_LIQUID_BUCKET).block(SPRUCE_SAP_LIQUID_BLOCK);
    }

    private static Properties liquidPropertiesAppular() {
        return new Properties(APPULAR_CIDER_ACID_LIQUID_TYPE, APPULAR_CIDER_ACID_LIQUID_SOURCE, APPULAR_CIDER_ACID_LIQUID_FLOWING).bucket(APPULAR_CIDER_ACID_LIQUID_BUCKET).block(APPULAR_CIDER_ACID_LIQUID_BLOCK);
    }
}