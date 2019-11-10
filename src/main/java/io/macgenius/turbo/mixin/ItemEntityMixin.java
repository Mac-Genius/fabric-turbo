package io.macgenius.turbo.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {
    private boolean moved;

    public ItemEntityMixin(EntityType<?> entityType_1, World world_1) {
        super(entityType_1, world_1);
        this.moved = false;
    }

    @Inject(at = @At("HEAD"), method="canMerge", cancellable=true)
    private void canMerge(CallbackInfoReturnable<Boolean> info) {

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (!stackTraceElements[3].getMethodName().equals("tick") && moved) {
            moved = false;
        }
    }

    @Override
    public void setVelocity(Vec3d vec3d_1) {
        if (vec3d_1.length() > 0.16) {
            this.moved = true;
        }
        super.setVelocity(vec3d_1);
    }
}
