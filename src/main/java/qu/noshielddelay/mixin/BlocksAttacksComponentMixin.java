package qu.noshielddelay.mixin;

import net.minecraft.component.type.BlocksAttacksComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import qu.noshielddelay.config.ModConfig;

import java.util.List;
import java.util.Optional;

@Mixin(BlocksAttacksComponent.class)
public abstract class BlocksAttacksComponentMixin {
    // Shadow the field, allowing writes
    @Shadow @Mutable
    private float blockDelaySeconds;

    @Inject(
            method = "blockDelaySeconds()F",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onBlockDelaySeconds(CallbackInfoReturnable<Float> cir) {
        if (ModConfig.ENABLED) {
            cir.setReturnValue(ModConfig.DELAY);
        }
    }

    @Inject(
            method = "<init>(FFLjava/util/List;" +
                    "Lnet/minecraft/component/type/BlocksAttacksComponent$ItemDamage;" +
                    "Ljava/util/Optional;Ljava/util/Optional;Ljava/util/Optional;)V",
            at = @At("RETURN")
    )
    private void onInit(
            float f, float g, List<?> list, BlocksAttacksComponent.ItemDamage itemDamage, Optional<?> bypassedBy, Optional<?> blockSound, Optional<?> disableSound, CallbackInfo ci
    ) {
        if (!ModConfig.ENABLED) {
            return;
        }
        this.blockDelaySeconds = ModConfig.DELAY;
    }
}
