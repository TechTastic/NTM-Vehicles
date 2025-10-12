package io.github.techtastic.ntm_vehicles.mixin;

import io.github.techtastic.ntm_vehicles.ducks.INuclear;
import minecrafttransportsimulator.jsondefs.JSONBullet;
import minecrafttransportsimulator.packloading.JSONParser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(JSONBullet.class)
public class MixinJSONBullet implements INuclear {
    @Unique
    @JSONParser.JSONDescription("The blast radius of an NTM nuclear explosion.")
    public int nuclearBlastRadius = 120;

    @Override
    public int getNuclearBlastRadius() {
        return nuclearBlastRadius;
    }
}
