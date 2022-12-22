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

    private lateinit var binding: ActivityMainBinding
    private lateinit var backBinding: LayoutBackBinding
    private lateinit var frontBinding: LayoutFrontBinding

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
            .setStatusBarColorWhenClose(R.color.blue) // like layoutFront background color
            .setStatusBarColorWhenOpen(R.color.blue) // like layoutBack background color
            // Layout Front Operation
            .setLayoutFrontBackgroundOpen(R.drawable.shape_front)
            .setLayoutFrontBackgroundClose(R.color.white)
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
        replaceFragment(HomeFragment())
        val adapter = DrawerMenuAdapter(this)
        adapter.listener = object : DrawerMenuAdapter.DrawerMenuInterface {
            override fun onItemSelected(model: DrawerMenu, position: Int) {
                binding.parent.closeMenu {
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
        val list = arrayListOf<DrawerMenu>()
        list.add(DrawerMenu(R.drawable.ic_home, getString(R.string.home), null, HomeFragment()))
        list.add(
            DrawerMenu(
                R.drawable.ic_account,
                getString(R.string.account),
                null,
                HomeFragment()
            )
        )
        list.add(
            DrawerMenu(
                R.drawable.ic_my_order,
                getString(R.string.my_orders),
                null,
                HomeFragment()
            )
        )
        list.add(
            DrawerMenu(
                R.drawable.ic_notification,
                getString(R.string.notification),
                null,
                HomeFragment()
            )
        )
        list.add(DrawerMenu(R.drawable.ic_points, getString(R.string.points), null, HomeFragment()))
        list.add(DrawerMenu(R.drawable.ic_wallet, getString(R.string.wallet), null, HomeFragment()))
        list.add(
            DrawerMenu(
                R.drawable.ic_favorite,
                getString(R.string.favorite),
                null,
                HomeFragment()
            )
        )
        list.add(
            DrawerMenu(
                R.drawable.ic_contact_us,
                getString(R.string.contact_us),
                null,
                HomeFragment()
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
        adapter.setData(list)
    }

    fun replaceFragment(fragment: Fragment?) {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.fragment, fragment!!)
        transaction.commit()
    }
}