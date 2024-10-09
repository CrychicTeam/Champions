package top.theillusivec4.champions.common.registry;

import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import top.theillusivec4.champions.Champions;
import top.theillusivec4.champions.common.loot.ChampionLootModifier;

public class ModLootModifiers {

  public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
    DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Champions.MODID);
  public static final DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<ChampionLootModifier>> CHAMPION_LOOT = LOOT_MODIFIER_SERIALIZERS.register(
    "champion_loot", () -> ChampionLootModifier.CODEC);

  public static void register(IEventBus bus) {
    LOOT_MODIFIER_SERIALIZERS.register(bus);
  }

}
