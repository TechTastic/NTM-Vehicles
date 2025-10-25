package io.github.techtastic.ntm_vehicles.mixin;

import io.github.techtastic.ntm_vehicles.NTMVehicles;
import io.github.techtastic.ntm_vehicles.Util;
import minecrafttransportsimulator.baseclasses.Point3D;
import minecrafttransportsimulator.blocks.components.ABlockBase;
import minecrafttransportsimulator.entities.instances.EntityBullet;
import minecrafttransportsimulator.entities.instances.PartGun;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityBullet.class)
public class MixinEntityBullet {
    @Inject(method = "performBlockHitLogic", at = @At("TAIL"), remap = false)
    private static void ntm_vehicles$addCustomBlockHitLogic(PartGun gun, int bulletNumber, Point3D blockPosition, ABlockBase.Axis blockSide, CallbackInfo ci) {
        Util.useHitLogic(gun, blockPosition);
    }

    @Inject(method = "performGenericHitLogic", at = @At("TAIL"), remap = false)
    private static void ntm_vehicles$addCustomGenericHitLogic(PartGun gun, int bulletNumber, Point3D position, ABlockBase.Axis hitSide, EntityBullet.HitType hitType, CallbackInfo ci) {
        Util.useHitLogic(gun, position);
    }
}
