package ru.betterend.world.features.terrain;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import ru.bclib.api.tag.CommonBlockTags;
import ru.bclib.util.BlocksHelper;
import ru.bclib.world.features.DefaultFeature;

import java.util.Random;
import net.minecraft.util.RandomSource;

public class SingleBlockFeature extends DefaultFeature {
	private final Block block;
	
	public SingleBlockFeature(Block block) {
		this.block = block;
	}
	
	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featureConfig) {
		final RandomSource random = featureConfig.random();
		final BlockPos pos = featureConfig.origin();
		final WorldGenLevel world = featureConfig.level();
		if (!world.getBlockState(pos.below()).is(CommonBlockTags.GEN_END_STONES)) {
			return false;
		}
		
		BlockState state = block.defaultBlockState();
		if (block.getStateDefinition().getProperty("waterlogged") != null) {
			boolean waterlogged = !world.getFluidState(pos).isEmpty();
			state = state.setValue(BlockStateProperties.WATERLOGGED, waterlogged);
		}
		BlocksHelper.setWithoutUpdate(world, pos, state);
		
		return true;
	}
}
