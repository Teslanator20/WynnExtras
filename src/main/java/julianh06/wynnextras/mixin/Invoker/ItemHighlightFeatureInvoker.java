package julianh06.wynnextras.mixin.Invoker;

import com.wynntils.features.inventory.ItemHighlightFeature;
import com.wynntils.utils.colors.CustomColor;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin (ItemHighlightFeature.class)
public interface ItemHighlightFeatureInvoker {
    @Invoker("getHighlightColor")
    CustomColor invokeGetHighlightColor(ItemStack itemStack, boolean hotbarHighlight);
}
