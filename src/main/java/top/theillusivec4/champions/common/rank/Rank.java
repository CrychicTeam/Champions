package top.theillusivec4.champions.common.rank;

import net.minecraft.core.Holder;
import net.minecraft.util.Tuple;
import net.minecraft.world.effect.MobEffect;
import top.theillusivec4.champions.api.IAffix;

import java.util.ArrayList;
import java.util.List;

public class Rank {

  private final int tier;
  private final int defaultColor;
  private final int numAffixes;
  private final int growthFactor;
  private final float chance;
  private final List<Tuple<Holder<MobEffect>, Integer>> effects;
  private final List<IAffix> presetAffixes;

  public Rank() {
    this(0, 0, 0, 0, 0, new ArrayList<>(), new ArrayList<>());
  }

  public Rank(int tier, int numAffixes, int growthFactor, float chance, int defaultColor,
              List<Tuple<Holder<MobEffect>, Integer>> effects, List<IAffix> presetAffixes) {
    this.tier = tier;
    this.numAffixes = numAffixes;
    this.growthFactor = growthFactor;
    this.chance = chance;
    this.defaultColor = defaultColor;
    this.effects = effects;
    this.presetAffixes = presetAffixes;
  }

  public int getTier() {
    return tier;
  }

  public int getDefaultColor() {
    return defaultColor;
  }

  public int getNumAffixes() {
    return numAffixes;
  }

  public int getGrowthFactor() {
    return growthFactor;
  }

  public float getChance() {
    return chance;
  }

  public List<Tuple<Holder<MobEffect>, Integer>> getEffects() {
    return effects;
  }

  public List<IAffix> getPresetAffixes() {
    return presetAffixes;
  }

  @Override
  public boolean equals(Object obj) {
    return obj == this || obj != null && obj.getClass() == this.getClass();
  }

  @Override
  public int hashCode() {
    return 1;
  }

  @Override
  public String toString() {
    return "Rank[]";
  }

}
