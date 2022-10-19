package fuzs.permanentsponges;

import fuzs.permanentsponges.config.ServerConfig;
import fuzs.permanentsponges.init.ModRegistry;
import fuzs.puzzleslib.config.ConfigHolder;
import fuzs.puzzleslib.core.CoreServices;
import fuzs.puzzleslib.core.ModConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PermanentSponges implements ModConstructor {
    public static final String MOD_ID = "permanentsponges";
    public static final String MOD_NAME = "Permanent Sponges";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    @SuppressWarnings("Convert2MethodRef")
    public static final ConfigHolder CONFIG = CoreServices.FACTORIES.serverConfig(ServerConfig.class, () -> new ServerConfig());

    @Override
    public void onConstructMod() {
        ModRegistry.touch();
    }
}
