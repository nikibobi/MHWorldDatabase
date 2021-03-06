package com.gatheringhallstudios.mhworlddatabase.features.weapons.detail

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import com.gatheringhallstudios.mhworlddatabase.AppSettings
import com.gatheringhallstudios.mhworlddatabase.R
import com.gatheringhallstudios.mhworlddatabase.adapters.common.CategoryAdapter
import com.gatheringhallstudios.mhworlddatabase.util.RecyclerViewFragment
import com.gatheringhallstudios.mhworlddatabase.components.ChildDivider
import com.gatheringhallstudios.mhworlddatabase.components.DashedDividerDrawable
import com.gatheringhallstudios.mhworlddatabase.features.weapons.WeaponTreeListAdapterDelegate
import com.gatheringhallstudios.mhworlddatabase.util.tree.RenderedTreeNode
import com.gatheringhallstudios.mhworlddatabase.util.tree.createTreeRenderList
import com.gatheringhallstudios.mhworlddatabase.getRouter

/**
 * Fragment used to display the weapon family tab.
 * Weapon families are used to figure out the crafting path.
 */
class WeaponDetailFamilyFragment : RecyclerViewFragment() {
    /**
     * Returns the viewmodel owned by the parent fragment
     */
    private val viewModel: WeaponDetailViewModel by lazy {
        ViewModelProviders.of(parentFragment!!).get(WeaponDetailViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CategoryAdapter(
                WeaponTreeListAdapterDelegate(
                        showTrueAttackValues = AppSettings.showTrueAttackValues,
                        onLongSelect = null,
                        onSelected = { getRouter().navigateWeaponDetail(it.id)}
                )
        )
        setAdapter(adapter)

        recyclerView.addItemDecoration(ChildDivider(DashedDividerDrawable(context!!)))

        viewModel.weaponFamilyData.observe(this, Observer { data ->
            adapter.clear()
            if (data == null) return@Observer

            val familyNodes = createTreeRenderList(data.familyPath)
            val finalNodes = data.finalWeapons.map { RenderedTreeNode(it) }

            adapter.addSection(familyNodes)
            if (finalNodes.isNotEmpty()) {
                adapter.addSection(getString(R.string.weapon_final_upgrades), finalNodes)
            }
        })
    }
}