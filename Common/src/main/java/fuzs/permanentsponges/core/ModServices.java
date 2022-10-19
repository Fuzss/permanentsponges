package fuzs.permanentsponges.core;

import com.google.common.collect.Maps;
import fuzs.puzzleslib.util.PuzzlesUtil;

import java.util.Collections;
import java.util.Map;

public final class ModServices {
    private static final Map<Class<?>, Object> SERVICES_BY_TYPE = Collections.synchronizedMap(Maps.newIdentityHashMap());

    @SuppressWarnings("unchecked")
    public static <T> T service(Class<? extends T> clazz) {
        // ensures a service is only created when it's first used to prevent early class loading
        return (T) SERVICES_BY_TYPE.computeIfAbsent(clazz, PuzzlesUtil::loadServiceProvider);
    }
}
