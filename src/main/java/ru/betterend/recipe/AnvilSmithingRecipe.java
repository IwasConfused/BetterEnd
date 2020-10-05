package ru.betterend.recipe;

import com.google.gson.JsonObject;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import ru.betterend.registry.ItemTagRegistry;

public class AnvilSmithingRecipe implements Recipe<Inventory> {
	
	public final static RecipeType<AnvilSmithingRecipe> TYPE = EndRecipeManager.registerType("smithing");
	public final static Serializer SERIALIZER = EndRecipeManager.registerSerializer("smithing", new Serializer());
	
	private final Identifier id;
	private final Ingredient input;
	private final ItemStack output;
	private final int damage;
	private final int level;
	
	public AnvilSmithingRecipe(Identifier identifier, Ingredient input, ItemStack output, int level, int damage) {
		this.id = identifier;
		this.input = input;
		this.output = output;
		this.level = level;
		this.damage = damage;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public boolean isIgnoredInRecipeBook() {
		return false;
	}

	@Override
	public ItemStack getOutput() {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean matches(Inventory craftingInventory, World world) {
		return this.matches(craftingInventory);
	}
	
	@Override
	public ItemStack craft(Inventory craftingInventory) {
		if (!matches(craftingInventory)) return ItemStack.EMPTY;
		ItemStack hammer = craftingInventory.getStack(1);
		int damage = hammer.getDamage() + this.damage;
		if (damage >= hammer.getMaxDamage()) return ItemStack.EMPTY;		
		hammer.setDamage(damage);
		return this.output.copy();
	}
	
	public ItemStack craft(Inventory craftingInventory, PlayerEntity player) {
		if (!matches(craftingInventory)) return ItemStack.EMPTY;
		
		ItemStack hammer = craftingInventory.getStack(1);
		int damage = hammer.getDamage() + this.damage;
		if (damage >= hammer.getMaxDamage()) return ItemStack.EMPTY;
		
		hammer.damage(this.damage, player, ((entity) -> {
			entity.sendEquipmentBreakStatus(null);
		}));
		return this.output.copy();
	}
	
	public boolean matches(Inventory craftingInventory) {
		ItemStack hammer = craftingInventory.getStack(1);
		System.out.println(ItemTagRegistry.HAMMERS.values());
		if (hammer.isEmpty() || !ItemTagRegistry.HAMMERS.contains(hammer.getItem())) {
			return false;
		}
		int level = ((ToolItem) hammer.getItem()).getMaterial().getMiningLevel();
		return level >= this.level && this.input.test(craftingInventory.getStack(0));
	}

	@Override
	@Environment(EnvType.CLIENT)
	public boolean fits(int width, int height) {
		return true;
	}

	@Override
	public Identifier getId() {
		return this.id;
	}

	@Override
	public RecipeType<?> getType() {
		return TYPE;
	}

	public static class Serializer implements RecipeSerializer<AnvilSmithingRecipe> {
		@Override
		public AnvilSmithingRecipe read(Identifier id, JsonObject json) {
			Ingredient input = Ingredient.fromJson(JsonHelper.getObject(json, "input"));
			String resultStr = JsonHelper.getString(json, "result");
			Identifier resultId = new Identifier(resultStr);
			ItemStack output = new ItemStack(Registry.ITEM.getOrEmpty(resultId).orElseThrow(() -> {
				return new IllegalStateException("Item: " + resultStr + " does not exists!");
			}));
			int level = JsonHelper.getInt(json, "level", 0);
			int damage = JsonHelper.getInt(json, "damage", 1);
			
			return new AnvilSmithingRecipe(id, input, output, level, damage);
		}

		@Override
		public AnvilSmithingRecipe read(Identifier id, PacketByteBuf packetBuffer) {
			Ingredient input = Ingredient.fromPacket(packetBuffer);
			ItemStack output = packetBuffer.readItemStack();
			int level = packetBuffer.readVarInt();
			int damage = packetBuffer.readVarInt();
			
			return new AnvilSmithingRecipe(id, input, output, level, damage);
		}

		@Override
		public void write(PacketByteBuf packetBuffer, AnvilSmithingRecipe recipe) {
			recipe.input.write(packetBuffer);
			packetBuffer.writeItemStack(recipe.output);
			packetBuffer.writeVarInt(recipe.level);
			packetBuffer.writeVarInt(recipe.damage);
		}
	}
}