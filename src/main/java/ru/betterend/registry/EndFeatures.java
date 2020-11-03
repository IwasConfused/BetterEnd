package ru.betterend.registry;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import ru.betterend.world.features.BlueVineFeature;
import ru.betterend.world.features.CavePlantFeature;
import ru.betterend.world.features.DoublePlantFeature;
import ru.betterend.world.features.DragonTreeBushFeature;
import ru.betterend.world.features.DragonTreeFeature;
import ru.betterend.world.features.EndFeature;
import ru.betterend.world.features.EndLakeFeature;
import ru.betterend.world.features.EndLilyFeature;
import ru.betterend.world.features.EndLotusFeature;
import ru.betterend.world.features.EndLotusLeafFeature;
import ru.betterend.world.features.LacugroveFeature;
import ru.betterend.world.features.MossyGlowshroomFeature;
import ru.betterend.world.features.PythadendronBushFeature;
import ru.betterend.world.features.PythadendronTreeFeature;
import ru.betterend.world.features.RoundCaveFeature;
import ru.betterend.world.features.SinglePlantFeature;
import ru.betterend.world.features.UnderwaterPlantFeature;
import ru.betterend.world.features.VineFeature;

public class EndFeatures {
	// Trees //
	public static final EndFeature MOSSY_GLOWSHROOM = new EndFeature("mossy_glowshroom", new MossyGlowshroomFeature(), 3);
	public static final EndFeature PYTHADENDRON_TREE = new EndFeature("pythadendron_tree", new PythadendronTreeFeature(), 2);
	public static final EndFeature LACUGROVE = new EndFeature("lacugrove", new LacugroveFeature(), 4);
	public static final EndFeature DRAGON_TREE = new EndFeature("dragon_tree", new DragonTreeFeature(), 3);
	
	// Bushes //
	public static final EndFeature PYTHADENDRON_BUSH = new EndFeature("pythadendron_bush", new PythadendronBushFeature(), 4);
	public static final EndFeature DRAGON_TREE_BUSH = new EndFeature("dragon_tree_bush", new DragonTreeBushFeature(), 15);
	
	// Plants //
	public static final EndFeature UMBRELLA_MOSS = new EndFeature("umbrella_moss", new DoublePlantFeature(EndBlocks.UMBRELLA_MOSS, EndBlocks.UMBRELLA_MOSS_TALL, 5), 5);
	public static final EndFeature CREEPING_MOSS = new EndFeature("creeping_moss", new SinglePlantFeature(EndBlocks.CREEPING_MOSS, 5), 5);
	public static final EndFeature BLUE_VINE = new EndFeature("blue_vine", new BlueVineFeature(), 1);
	public static final EndFeature CHORUS_GRASS = new EndFeature("chorus_grass", new SinglePlantFeature(EndBlocks.CHORUS_GRASS, 4), 5);
	public static final EndFeature CAVE_GRASS = new EndFeature("cave_grass", new CavePlantFeature(EndBlocks.CAVE_GRASS, 7), 7);
	public static final EndFeature CRYSTAL_GRASS = new EndFeature("crystal_grass", new SinglePlantFeature(EndBlocks.CRYSTAL_GRASS, 8, false), 5);
	public static final EndFeature SHADOW_PLANT = new EndFeature("shadow_plant", new SinglePlantFeature(EndBlocks.SHADOW_PLANT, 6), 9);
	public static final EndFeature MURKWEED = new EndFeature("murkweed", new SinglePlantFeature(EndBlocks.MURKWEED, 3), 2);
	public static final EndFeature NEEDLEGRASS = new EndFeature("needlegrass", new SinglePlantFeature(EndBlocks.NEEDLEGRASS, 3), 2);
	
	// Vines //
	public static final EndFeature DENSE_VINE = new EndFeature("dense_vine", new VineFeature(EndBlocks.DENSE_VINE, 24), 3);
	public static final EndFeature TWISTED_VINE = new EndFeature("twisted_vine", new VineFeature(EndBlocks.TWISTED_VINE, 24), 3);
	
	// Water //
	public static final EndFeature BUBBLE_CORAL = new EndFeature("bubble_coral", new UnderwaterPlantFeature(EndBlocks.BUBBLE_CORAL, 10), 10);
	public static final EndFeature BUBBLE_CORAL_RARE = new EndFeature("bubble_coral_rare", new UnderwaterPlantFeature(EndBlocks.BUBBLE_CORAL, 3), 4);
	public static final EndFeature END_LILY = new EndFeature("end_lily", new EndLilyFeature(10), 10);
	public static final EndFeature END_LILY_RARE = new EndFeature("end_lily_rare", new EndLilyFeature(3), 4);
	public static final EndFeature END_LOTUS = new EndFeature("end_lotus", new EndLotusFeature(7), 5);
	public static final EndFeature END_LOTUS_LEAF = new EndFeature("end_lotus_leaf", new EndLotusLeafFeature(20), 25);
	
	// Features //
	public static final EndFeature END_LAKE = EndFeature.makeLakeFeature("end_lake", new EndLakeFeature(), 4);
	public static final EndFeature END_LAKE_RARE = EndFeature.makeLakeFeature("end_lake_rare", new EndLakeFeature(), 40);
	public static final EndFeature ROUND_CAVE = EndFeature.makeRawGenFeature("round_cave", new RoundCaveFeature(), 2);
	public static final EndFeature ROUND_CAVE_RARE = EndFeature.makeRawGenFeature("round_cave_rare", new RoundCaveFeature(), 25);
	
	// Ores //
	public static final EndFeature ENDER_ORE = EndFeature.makeOreFeature("ender_ore", EndBlocks.ENDER_ORE, 6, 3, 0, 4, 96);
	public static final EndFeature VIOLECITE_LAYER = EndFeature.makeLayerFeature("violecite_layer", EndBlocks.VIOLECITE, 15, 4, 96, 8);
	public static final EndFeature FLAVOLITE_LAYER = EndFeature.makeLayerFeature("flavolite_layer", EndBlocks.FLAVOLITE, 12, 4, 96, 6);
	
	// Other //
	//public static final EndFeature ETERNAL_PORTAL = EndFeature.makeChansedFeature("eternal_portal", new EternalPortalFeature(), 500);
	
	public static void registerBiomeFeatures(Identifier id, Biome biome, List<List<Supplier<ConfiguredFeature<?, ?>>>> features) {
		if (id.getNamespace().equals("minecraft")) {
			String path = id.getPath();
			if (path.equals("end_highlands") || path.equals("end_midlands") || path.equals("small_end_islands")) {
				int pos = GenerationStep.Feature.VEGETAL_DECORATION.ordinal();
				if (pos < features.size()) {
					List<Supplier<ConfiguredFeature<?, ?>>> list = features.get(pos);
					// If only chorus plants are enabled
					if (list.size() == 1) {
						features.get(pos).clear();
					}
				}
			}
		}
		
		addFeature(FLAVOLITE_LAYER, features);
		addFeature(ENDER_ORE, features);
		addFeature(ROUND_CAVE_RARE, features);
		addFeature(CAVE_GRASS, features);
		//addFeature(ETERNAL_PORTAL, features);
	}
	
	private static void addFeature(EndFeature feature, List<List<Supplier<ConfiguredFeature<?, ?>>>> features) {
		int index = feature.getFeatureStep().ordinal();
		if (features.size() > index) {
			features.get(index).add(() -> {
				return feature.getFeatureConfigured();
			});
		}
		else {
			List<Supplier<ConfiguredFeature<?, ?>>> newFeature = Lists.newArrayList();
			newFeature.add(() -> {
				return feature.getFeatureConfigured();
			});
			features.add(newFeature);
		}
	}
	
	public static void register() {}
}
