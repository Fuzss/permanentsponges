package fuzs.permanentsponges.config;

import fuzs.puzzleslib.api.config.v3.Config;
import fuzs.puzzleslib.api.config.v3.ConfigCore;

public class ServerConfig implements ConfigCore {
    @Config
    public final SpongeConfig aquaticSponge = new SpongeConfig(2, 4, true);
    @Config
    public final SpongeConfig magmaticSponge = new SpongeConfig(3, 6, false);
    @Config(description = {"The cooldown in ticks after using a sponge on a stick item, just like ender pearls.", "Set to '0' to disable the cooldown."})
    @Config.IntRange(min = 0, max = 72000)
    public int stickCooldownTicks = 20;
    @Config(description = "Nearby sponges will immediately suck up liquids placed from buckets.")
    public boolean preventEmptyingBuckets = false;

    public static class SpongeConfig implements ConfigCore {
        @Config(description = "The radius in blocks centered on where the sponge on a stick was clicked where all liquids will be removed.")
        @Config.IntRange(min = 1, max = 16)
        public int stickRadius;
        @Config(description = "The radius in blocks centered on the sponge where all liquids will be removed.")
        @Config.IntRange(min = 1, max = 16)
        public int blockRadius;
        @Config(description = "Should the sponge be destroyed after removing a hot liquid such as lava.")
        public boolean destroyTouchingHot;

        public SpongeConfig(int stickRadius, int blockRadius, boolean destroyTouchingHot) {
            this.stickRadius = stickRadius;
            this.blockRadius = blockRadius;
            this.destroyTouchingHot = destroyTouchingHot;
        }
    }
}
