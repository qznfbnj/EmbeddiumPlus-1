package me.srrapero720.embeddiumplus.mixins.impl.leaves_culling;

import me.srrapero720.embeddiumplus.EmbyConfig;
import me.srrapero720.embeddiumplus.foundation.leaves_culling.ICulleableLeaves;
import me.srrapero720.embeddiumplus.foundation.leaves_culling.LeavesCulling;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("deprecation")
@Mixin(LeavesBlock.class)
public class LeavesBlockMixin extends Block implements ICulleableLeaves {
    // TODO: cull less leaves (maybe delegate to 2.0.0)
    @Unique private ResourceLocation embPlus$resLoc;
    @Unique private int leaves_neighbor;

    public LeavesBlockMixin(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentState, Direction direction) {
        if (adjacentState.getBlock() instanceof ICulleableLeaves leaves) {
            return LeavesCulling.should(embplus$cast(), this, (LeavesBlock) leaves, leaves) || super.skipRendering(state, adjacentState, direction);
        }
        return super.skipRendering(state, adjacentState, direction);
    }

    @Override
    public void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
    }

    @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        super.onNeighborChange(state, level, pos, neighbor);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
    }

    @Override
    public ResourceLocation embplus$getResourceLocation() {
        return embPlus$resLoc != null ? embPlus$resLoc : (embPlus$resLoc = ForgeRegistries.BLOCKS.getKey(this));
    }

    @Override
    public int embplus$activeNeighbors() {
        return leaves_neighbor;
    }

    public LeavesBlock embplus$cast() {
        return (LeavesBlock) (Object) this;
    }
}



