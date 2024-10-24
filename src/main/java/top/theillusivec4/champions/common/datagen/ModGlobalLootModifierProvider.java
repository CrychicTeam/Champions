package top.theillusivec4.champions.common.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import top.theillusivec4.champions.Champions;
import top.theillusivec4.champions.common.loot.ChampionLootModifier;
import top.theillusivec4.champions.common.loot.ChampionPropertyCondition;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
  public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
    super(output, registries, Champions.MODID);
  }

  @Override
  protected void start() {
    this.add("champion_loot_modifier", new ChampionLootModifier(new LootItemCondition[]{
      new ChampionPropertyCondition(LootContext.EntityTarget.THIS, Optional.empty(), Optional.empty())
    }));
  }
}
