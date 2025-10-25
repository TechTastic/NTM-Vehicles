package io.github.techtastic.ntm_vehicles;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.List;

public class MixinLoader implements ILateMixinLoader {
    @Override
    public List<String> getMixinConfigs() {
        List<String> configs = new ArrayList<>();
        configs.add("mixins.ntm_vehicles.json");
        return configs;
    }
}
