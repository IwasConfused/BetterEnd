package ru.betterend.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import ru.bclib.items.BaseArmorItem;
import ru.betterend.effects.EndStatusEffects;
import ru.betterend.item.material.EndArmorMaterial;

public class CrystaliteArmor extends BaseArmorItem {
	
	public final static MutableComponent CHEST_DESC;
	public final static MutableComponent BOOTS_DESC;
	
	public CrystaliteArmor(EquipmentSlot equipmentSlot, Properties settings) {
		super(EndArmorMaterial.CRYSTALITE, equipmentSlot, settings);
	}
	
	public static boolean hasFullSet(LivingEntity owner) {
		for (ItemStack armorStack : owner.getArmorSlots()) {
			if (!(armorStack.getItem() instanceof CrystaliteArmor)) {
				return false;
			}
		}
		return true;
	}
	
	public static void applySetEffect(LivingEntity owner) {
		if ((owner.tickCount & 63) == 0) {
			owner.addEffect(new MobEffectInstance(EndStatusEffects.CRYSTALITE_HEALTH_REGEN));
		}
	}
	
	static {
		Style descStyle = Style.EMPTY.applyFormats(ChatFormatting.DARK_AQUA, ChatFormatting.ITALIC);
		CHEST_DESC = Component.translatable("tooltip.armor.crystalite_chest");
		CHEST_DESC.setStyle(descStyle);
		BOOTS_DESC = Component.translatable("tooltip.armor.crystalite_boots");
		BOOTS_DESC.setStyle(descStyle);
	}
}
