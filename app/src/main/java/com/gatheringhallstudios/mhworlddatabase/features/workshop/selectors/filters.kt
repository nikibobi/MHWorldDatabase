package com.gatheringhallstudios.mhworlddatabase.features.workshop.selectors

import com.gatheringhallstudios.mhworlddatabase.data.models.*
import com.gatheringhallstudios.mhworlddatabase.data.types.ElementStatus
import com.gatheringhallstudios.mhworlddatabase.data.types.Rank
import com.gatheringhallstudios.mhworlddatabase.data.types.WeaponType
import com.gatheringhallstudios.mhworlddatabase.util.tree.Filter

class ArmorNameFilter(private val name: String) : Filter<ArmorFull> {
    override fun runFilter(obj: ArmorFull): Boolean {
        return obj.armor.name?.contains(name, ignoreCase = true) ?: false
    }
}

class ArmorRankFilter(private val ranks: Set<Rank>) : Filter<ArmorFull> {
    override fun runFilter(obj: ArmorFull): Boolean {
        return ranks.contains(obj.armor.rank)
    }
}

class ArmorElementalDefenseFilter(private val elementDefs: Set<ElementStatus>) : Filter<ArmorFull> {
    override fun runFilter(obj: ArmorFull): Boolean {
        return elementDefs.all {
            return when (it) {
                ElementStatus.FIRE -> {
                    obj.armor.fire > 0
                }
                ElementStatus.WATER -> {
                    obj.armor.water > 0
                }
                ElementStatus.THUNDER -> {
                    obj.armor.thunder > 0
                }
                ElementStatus.ICE -> {
                    obj.armor.ice > 0
                }
                ElementStatus.DRAGON -> {
                    obj.armor.dragon > 0
                }
                else -> {
                    return false
                }
            }
        }
    }
}

class ArmorSkillsFilter(private val skills: Set<SkillTree>) : Filter<ArmorFull> {
    override fun runFilter(obj: ArmorFull): Boolean {
        return obj.skills.map { it.skillTree.id }.containsAll(skills.map { it.id })
    }
}

class DecorationNameFilter(private val name: String) : Filter<Decoration> {
    override fun runFilter(obj: Decoration): Boolean {
        return obj.name?.contains(name, ignoreCase = true) ?: false
    }
}

class DecorationSlotLevelFilter(private val slotLevels: Set<Int>) : Filter<Decoration> {
    override fun runFilter(obj: Decoration): Boolean {
        return slotLevels.contains(obj.slot)
    }
}

class CharmNameFilter(private val name: String) : Filter<CharmFull> {
    override fun runFilter(obj: CharmFull): Boolean {
        return obj.charm.name?.contains(name, ignoreCase = true) ?: false
    }
}

class CharmSkillsFilter(private val skills: Set<SkillTree>) : Filter<CharmFull> {
    override fun runFilter(obj: CharmFull): Boolean {
        return obj.skills.map { it.skillTree.id }.containsAll(skills.map { it.id })
    }
}

class WeaponNameFilter(private val name: String) : Filter<WeaponFull> {
    override fun runFilter(obj: WeaponFull): Boolean {
        return obj.weapon.name.contains(name, ignoreCase = true)
    }
}

class WeaponSlotFilter(private val slotLevels: Set<Int>) : Filter<WeaponFull> {
    override fun runFilter(obj: WeaponFull): Boolean {
        return obj.weapon.slots.active.any { slotLevels.contains(it) }
    }
}

class WeaponTypeFilter(private val weaponTypes: Set<WeaponType>) : Filter<WeaponFull> {
    override fun runFilter(obj: WeaponFull): Boolean {
        return weaponTypes.contains(obj.weapon.weapon_type)
    }
}

class WeaponElementFilter(private val elements: Set<ElementStatus>) : Filter<WeaponFull> {
    override fun runFilter(obj: WeaponFull): Boolean {
        return elements.contains(obj.weapon.element1) || elements.contains(obj.weapon.element2)
    }
}

class WeaponRankFilter(private val ranks: Set<Rank>) : Filter<WeaponFull> {
    override fun runFilter(obj: WeaponFull): Boolean {
        ranks.forEach {
            return when (it) {
                Rank.HIGH -> {
                    obj.weapon.rarity in 5..8
                }
                Rank.LOW -> {
                    obj.weapon.rarity in 1..4
                }
                Rank.MASTER -> {
                    obj.weapon.rarity > 8
                }
            }
        }

        return false
    }
}