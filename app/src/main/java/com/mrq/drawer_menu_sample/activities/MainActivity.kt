package com.mrq.drawer_menu_sample.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrq.drawer_menu_sample.R
import com.mrq.drawer_menu_sample.adapters.DrawerMenuAdapter
import com.mrq.drawer_menu_sample.databinding.ActivityMainBinding
import com.mrq.drawer_menu_sample.databinding.LayoutBackBinding
import com.mrq.drawer_menu_sample.databinding.LayoutFrontBinding
import com.mrq.drawer_menu_sample.fragments.HomeFragment
import com.mrq.drawer_menu_sample.model.DrawerMenu

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { DrawerMenuAdapter() }
    private lateinit var binding: ActivityMainBinding
    private lateinit var backBinding: LayoutBackBinding
    private lateinit var frontBinding: LayoutFrontBinding

    private var toast: Toast? = null
    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        backBinding = LayoutBackBinding.inflate(layoutInflater)
        frontBinding = LayoutFrontBinding.inflate(layoutInflater)
        initDrawerMenuList()

        binding.parent
            .context(this)
            .layoutFront(frontBinding.root) // layout.xml
            .layoutBack(backBinding.root) // layout.xml
            // Set Animation Lift Or Right
            .setAnimationRight(false)
            // Status Bar Color
            .isChangeStatusBarColorWhenOpenOrColes(true)
            .setStatusBarColorWhenClose(R.color.ora) // like layoutFront background color
            .setStatusBarColorWhenOpen(R.color.blue) // like layoutBack background color
            .isStatusBarDarkWhenOpen(true)
            .isStatusBarDarkWhenClose(false)
            // Layout Front Operation
            .setLayoutFrontBackgroundOpen(R.drawable.shape_front)
            .setLayoutFrontBackgroundClose(R.color.ora)
            .isLayoutFrontClickToClose(true)
            .setOnLayoutFrontClickToClose(onAnimationFinish = {
                Toast.makeText(this, "onAnimationFinish", Toast.LENGTH_SHORT).show()
            })
            // Menu Icon Operation
            .setViewOpenMenu(frontBinding.appbar.menu)
            .changeMenuIconToBackIcon(true)
            // Animation Speed
            .setDuration(1000)
            .build()

    }

    private fun initDrawerMenuList() {
        replaceFragment(HomeFragment.newInstance(getString(R.string.home)))
        adapter.listener = object : DrawerMenuAdapter.DrawerMenuInterface {
            override fun onItemSelected(model: DrawerMenu, position: Int) {
                binding.parent.closeMenuWhenAnimationFinish {
                    if (model.activity != null) {
                        startActivity(Intent(this@MainActivity, model.activity!!::class.java))
                    } else {
                        replaceFragment(model.fragment)
                        frontBinding.appbar.title.text = model.title
                    }
                }
            }
        }
        backBinding.recyclerview.adapter = adapter
        backBinding.recyclerview.setHasFixedSize(true)
        backBinding.recyclerview.layoutManager = LinearLayoutManager(this)
        adapter.setData(setData())
    }

    private fun setData(): ArrayList<DrawerMenu> {
        val list = arrayListOf<DrawerMenu>()
        list.add(
            DrawerMenu(
                R.drawable.ic_home, getString(R.string.home), null,
                HomeFragment.newInstance(getString(R.string.home))
            )
        )
        list.add(
            DrawerMenu(
                R.drawable.ic_account,
                getString(R.string.account),
                null,
                HomeFragment.newInstance(getString(R.string.account))
            )
        )
        list.add(
            DrawerMenu(
                R.drawable.ic_my_order,
                getString(R.string.my_orders),
                null,
                HomeFragment.newInstance(getString(R.string.my_orders))
            )
        )
        list.add(
            DrawerMenu(
                R.drawable.ic_notification,
                getString(R.string.notification),
                null,
                HomeFragment.newInstance(getString(R.string.notification))
            )
        )
        list.add(
            DrawerMenu(
                R.drawable.ic_points,
                getString(R.string.points),
                null,
                HomeFragment.newInstance(getString(R.string.points))
            )
        )
        list.add(
            DrawerMenu(
                R.drawable.ic_wallet,
                getString(R.string.wallet),
                null,
                HomeFragment.newInstance(getString(R.string.wallet))
            )
        )
        list.add(
            DrawerMenu(
                R.drawable.ic_favorite,
                getString(R.string.favorite),
                null,
                HomeFragment.newInstance(getString(R.string.favorite))
            )
        )
        list.add(
            DrawerMenu(
                R.drawable.ic_contact_us,
                getString(R.string.contact_us),
                null,
                HomeFragment.newInstance(getString(R.string.contact_us))
            )
        )
        list.add(
            DrawerMenu(
                R.drawable.ic_setting,
                getString(R.string.setting),
                SettingActivity(),
                null
            )
        )
        return list
    }

    fun replaceFragment(fragment: Fragment?) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
//        transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right)
        transaction.replace(R.id.fragment, fragment!!)
        transaction.commit()
    }

    override fun onBackPressed() {
        if (binding.parent.isOpen()) {
            binding.parent.closeMenu()
        } else {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                toast!!.cancel()
                super.onBackPressed()
                finish()
            } else {
                toast = Toast.makeText(this, getString(R.string.back_exit), Toast.LENGTH_SHORT)
                toast!!.show()
            }
            backPressedTime = System.currentTimeMillis()
        }
    }
}