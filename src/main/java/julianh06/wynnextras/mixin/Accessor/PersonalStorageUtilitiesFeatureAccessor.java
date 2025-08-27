package julianh06.wynnextras.mixin.Accessor;

import com.wynntils.features.inventory.PersonalStorageUtilitiesFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin (PersonalStorageUtilitiesFeature.class)
public interface PersonalStorageUtilitiesFeatureAccessor {
    @Accessor (value = "lastPage", remap = false)
    int getLastPage();
}
