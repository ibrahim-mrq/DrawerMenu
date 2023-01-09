package com.mrq.drawer_menu_sample.model

import android.app.Activity
import androidx.fragment.app.Fragment

data class DrawerMenu(
    var icon: Int,
    var title: String,
    var activity: Activity?,
    var fragment: Fragment?,
)