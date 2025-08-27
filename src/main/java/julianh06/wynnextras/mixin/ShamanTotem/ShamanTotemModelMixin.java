package julianh06.wynnextras.mixin.ShamanTotem;

import com.wynntils.models.abilities.ShamanTotemModel;
import julianh06.wynnextras.config.WynnExtrasConfig;
import net.minecraft.util.math.Position;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin (ShamanTotemModel.class)
public class ShamanTotemModelMixin {
    @Inject(method = "isClose", at = @At("HEAD"), remap = false, cancellable = true)
    void isClose(Position pos1, Position pos2, CallbackInfoReturnable<Boolean> cir) {
//        if(WynnExtrasConfig.INSTANCE.totemRangeVisualizerToggle) {
//            cir.setReturnValue(true);
//        }
    }
}
