package fuzs.permanentsponges.world.level.block.sponge;

import fuzs.permanentsponges.PermanentSponges;
import fuzs.permanentsponges.config.ServerConfig;

public enum SpongeMaterial {
    AQUATIC, MAGMATIC;

    public int getStickDistance() {
        return this.getSpongeConfig().stickRadius;
    }

    public int getBlockDistance() {
        return this.getSpongeConfig().blockRadius;
    }

    public boolean shouldDestroyTouchingHot() {
        return this.getSpongeConfig().destroyTouchingHot;
    }

    private ServerConfig.SpongeConfig getSpongeConfig() {
        return switch (this) {
            case AQUATIC -> PermanentSponges.CONFIG.get(ServerConfig.class).aquaticSponge;
            case MAGMATIC -> PermanentSponges.CONFIG.get(ServerConfig.class).magmaticSponge;
        };
    }
}
