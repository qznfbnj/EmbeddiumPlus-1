package me.srrapero720.embeddiumplus.foundation.leaves_culling;

import net.minecraft.resources.ResourceLocation;

public interface ICulleableLeaves { // SI, EL NOMBRE FUE INTENCIONADO
    ResourceLocation embplus$getResourceLocation();
    int embplus$activeNeighbors();
}
