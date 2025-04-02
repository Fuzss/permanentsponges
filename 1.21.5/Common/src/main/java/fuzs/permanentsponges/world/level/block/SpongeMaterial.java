package fuzs.permanentsponges.world.level.block;

import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.config.ServerConfig;
import fuzs.permanentsponges.init.ModRegistry;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.village.poi.PoiType;

public enum SpongeMaterial {
    AQUATIC {
        @Override
        ServerConfig.SpongeConfig getSpongeConfig() {
            return PermanentSponges.CONFIG.get(ServerConfig.class).aquaticSponge;
        }

        @Override
        public Holder.Reference<PoiType> getPoiType() {
            return ModRegistry.AQUEOUS_SPONGE_POI_TYPE;
        }
    }, MAGMATIC {
        @Override
        ServerConfig.SpongeConfig getSpongeConfig() {
            return PermanentSponges.CONFIG.get(ServerConfig.class).magmaticSponge;
        }

        @Override
        public Holder.Reference<PoiType> getPoiType() {
            return ModRegistry.MAGMATIC_SPONGE_POI_TYPE;
        }
    };

    public int getStickDistance() {
        return this.getSpongeConfig().stickRadius;
    }

    public int getBlockDistance() {
        return this.getSpongeConfig().blockRadius;
    }

    public boolean shouldDestroyTouchingHot() {
        return this.getSpongeConfig().destroyTouchingHot;
    }

    abstract ServerConfig.SpongeConfig getSpongeConfig();

    public abstract Holder.Reference<PoiType> getPoiType();
}
