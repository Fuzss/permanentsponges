package fuzs.permanentsponges.world.item;

import net.minecraft.world.item.Item;

public class SpongeOnAStickItem extends Item {
    private final boolean vanish;

    public SpongeOnAStickItem(Properties properties, boolean vanish) {
        super(properties);
        this.vanish = vanish;
    }
}
