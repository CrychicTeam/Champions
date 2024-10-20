package top.theillusivec4.champions.client.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import top.theillusivec4.champions.Champions;
import top.theillusivec4.champions.api.IAffix;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.client.ChampionsOverlay;
import top.theillusivec4.champions.client.config.ClientChampionsConfig;
import top.theillusivec4.champions.common.capability.ChampionCapability;

import java.util.Set;
import java.util.stream.Collectors;

public class HUDHelper {

  private static final ResourceLocation GUI_BAR_TEXTURES = new ResourceLocation(
    "textures/gui/bars.png");
  private static final ResourceLocation GUI_STAR = new ResourceLocation(Champions.MODID,
    "textures/gui/staricon.png");

  public static boolean renderHealthBar(GuiGraphics guiGraphics, final LivingEntity livingEntity) {
    return ChampionCapability.getCapability(livingEntity).map(champion -> {
      IChampion.Client clientChampion = champion.getClient();
      return clientChampion.getRank().map(rank -> {
        int num = rank.getA();
        Set<String> affixSet = clientChampion.getAffixes().stream().map(IAffix::getIdentifier)
          .collect(Collectors.toSet());

        if (num > 0 || affixSet.size() > 0) {
          Minecraft client = Minecraft.getInstance();
          int i = client.getWindow().getGuiScaledWidth();
          int k = i / 2 - 91;
          int j = 21;
          int xOffset = ClientChampionsConfig.hudXOffset;
          int yOffset = ClientChampionsConfig.hudYOffset;
          int color = rank.getB();
          float r = (float) ((color >> 16) & 0xFF) / 255f;
          float g = (float) ((color >> 8) & 0xFF) / 255f;
          float b = (float) ((color) & 0xFF) / 255f;

          RenderSystem.defaultBlendFunc();
          RenderSystem.setShaderColor(r, g, b, 1.0F);
          RenderSystem.enableBlend();
          RenderSystem.setShader(GameRenderer::getPositionTexShader);
          RenderSystem.setShaderTexture(0, GUI_BAR_TEXTURES);
          ChampionsOverlay.startX = xOffset + k;
          ChampionsOverlay.startY = yOffset + 1;

          guiGraphics.blit(GUI_BAR_TEXTURES, xOffset + k, yOffset + j, 0, 60, 182, 5, 256, 256);
          int healthOffset =
            (int) ((livingEntity.getHealth() / livingEntity.getMaxHealth()) * 183.0F);

          if (healthOffset > 0) {
            guiGraphics.blit(GUI_BAR_TEXTURES, xOffset + k, yOffset + j, 0, 65, healthOffset, 5, 256,
              256);
          }

          RenderSystem.setShaderTexture(0, GUI_STAR);

          if (num <= 18) {
            int startStarsX = xOffset + i / 2 - 5 - 5 * (num - 1);

            for (int tier = 0; tier < num; tier++) {
              guiGraphics.blit(GUI_STAR, startStarsX, yOffset + 1, 0, 0, 9, 9, 9, 9);
              startStarsX += 10;
            }
          } else {
            int startStarsX = xOffset + i / 2 - 5;
            String count = "x" + num;
            guiGraphics.blit(GUI_STAR, startStarsX - client.font.width(count) / 2,
              yOffset + 1, 0, 0, 9, 9, 9, 9);
            guiGraphics.drawString(client.font, count,
              startStarsX + 10 - client.font.width(count) / 2.0F, yOffset + 2,
              16777215, true);
          }
          Component customName = livingEntity.getCustomName();
          String name;

          if (customName == null) {
            name = Component.translatable("rank.champions.title." + num).getString();
            name += " " + livingEntity.getName().getString();
          } else {
            name = customName.getString();
          }
          guiGraphics.drawString(client.font, name,
            xOffset + (float) (i / 2 - client.font.width(name) / 2),
            yOffset + (float) (j - 9), color, true);
          RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
          StringBuilder builder = new StringBuilder();

          for (String affix : affixSet) {
            builder.append(
              Component.translatable("affix." + Champions.MODID + "." + affix).getString());
            builder.append(" ");
          }
          String affixes = builder.toString().trim();
          guiGraphics.drawString(client.font, affixes,
            xOffset + (float) (i / 2 - client.font.width(affixes) / 2),
            yOffset + (float) (j + 6), 16777215, true);
          RenderSystem.disableBlend();
          return true;
        }
        return false;
      }).orElse(false);
    }).orElse(false);
  }
}
