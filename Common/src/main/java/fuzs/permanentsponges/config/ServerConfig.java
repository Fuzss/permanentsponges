package fuzs.permanentsponges.config;

import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;

public class ServerConfig implements ConfigCore {
    @Config
    public final SpongeConfig aquaticSponge = new SpongeConfig(2, 4, true);
    @Config
    public final SpongeConfig magmaticSponge = new SpongeConfig(3, 5, false);
    @Config(description = {"Liquid blocks checked/removed per tick for sponge blocks, useful to put less load on the level in a single tick, especially when a large radius is set.", "Set to -1 to run all updates instantaneously like with the sponges on a stick."})
    @Config.IntRange(min = -1, max = 64)
    public int updatesPerTick = 12;
    @Config(description = {"The cooldown in ticks after using a sponge on a stick item, just like ender pearls.", "Set to 0 to disable the cooldown."})
    @Config.IntRange(min = 0, max = 72000)
    public int stickCooldownTicks = 20;

    public static class SpongeConfig implements ConfigCore {
        @Config(description = "The radius in blocks centered on where the sponge on a stick was clicked where all liquids will be removed.")
        @Config.IntRange(min = 1, max = 8)
        public int stickRadius;
        @Config(description = "The radius in blocks centered on the sponge where all liquids will be removed.")
        @Config.IntRange(min = 1, max = 24)
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
