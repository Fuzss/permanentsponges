package fuzs.permanentsponges;

import fuzs.permanentsponges.config.ServerConfig;
import fuzs.permanentsponges.init.ModRegistry;
import fuzs.permanentsponges.world.level.block.sponge.SpongeScheduler;
import fuzs.puzzleslib.api.config.v3.ConfigHolder;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.event.v1.level.ServerChunkEvents;
import fuzs.puzzleslib.api.event.v1.level.ServerLevelEvents;
import fuzs.puzzleslib.api.event.v1.level.ServerLevelTickEvents;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PermanentSponges implements ModConstructor {
    public static final String MOD_ID = "permanentsponges";
    public static final String MOD_NAME = "Permanent Sponges";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);
    public static final ConfigHolder CONFIG = ConfigHolder.builder(MOD_ID).server(ServerConfig.class);

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
        registerHandlers();
    }

    private static void registerHandlers() {
        ServerLevelTickEvents.END.register(SpongeScheduler.INSTANCE::onServerWorld$Tick);
        ServerChunkEvents.UNLOAD.register(SpongeScheduler.INSTANCE::onChunk$Unload);
        ServerLevelEvents.UNLOAD.register(SpongeScheduler.INSTANCE::onServerWorld$Unload);
    }

    @Override
    public void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
        context.registerCreativeModeTab(CreativeModeTabConfigurator.from(MOD_ID).icon(() -> new ItemStack(ModRegistry.MAGMATIC_SPONGE_ON_A_STICK_ITEM.get())).displayItems((itemDisplayParameters, output) -> {
            output.accept(ModRegistry.AQUEOUS_SPONGE_ITEM.get());
            output.accept(ModRegistry.MAGMATIC_SPONGE_ITEM.get());
            output.accept(ModRegistry.AQUEOUS_SPONGE_ON_A_STICK_ITEM.get());
            output.accept(ModRegistry.MAGMATIC_SPONGE_ON_A_STICK_ITEM.get());
        }));
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
