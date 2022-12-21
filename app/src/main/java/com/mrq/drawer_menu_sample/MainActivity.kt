package com.mrq.drawer_menu_sample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrq.drawer_menu_sample.adapters.DrawerMenuAdapter
import com.mrq.drawer_menu_sample.databinding.ActivityMainBinding
import com.mrq.drawer_menu_sample.databinding.LayoutBackBinding
import com.mrq.drawer_menu_sample.databinding.LayoutFrontBinding
import com.mrq.drawer_menu_sample.model.DrawerMenu

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val backBinding = LayoutBackBinding.inflate(layoutInflater)
        val frontBinding = LayoutFrontBinding.inflate(layoutInflater)
        initDrawerMenuList(backBinding)

        binding.parent
            .context(this)
            .layoutFront(frontBinding.root)
            .layoutBack(backBinding.root)
            .setViewOpenMenu(frontBinding.menu)
            .setAnimationRight(false)
            .isLayoutFrontClickToClose(true)
            .setOnLayoutFrontClickToClose(onAnimationFinish = {
                Toast.makeText(this, "asddddd", Toast.LENGTH_SHORT).show()
            })
            .changeMenuIconToBackIcon(true)
            .build()

        frontBinding.close.setOnClickListener {
            binding.parent.closeMenu {
                Toast.makeText(this, "closeMenu", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun initDrawerMenuList(backBinding: LayoutBackBinding) {
        val adapter = DrawerMenuAdapter(this)
        adapter.listener = object : DrawerMenuAdapter.DrawerMenuInterface {
            override fun onItemSelected(model: DrawerMenu, position: Int) {
                binding.parent.closeMenu {
                    Toast.makeText(this@MainActivity, "closeMenu", Toast.LENGTH_SHORT).show()
                }
            }
        }
        backBinding.recyclerview.adapter = adapter
        backBinding.recyclerview.setHasFixedSize(true)
        backBinding.recyclerview.layoutManager = LinearLayoutManager(this)
        val list = arrayListOf<DrawerMenu>()
        list.add(DrawerMenu(R.drawable.ic_account, getString(R.string.account)))
        list.add(DrawerMenu(R.drawable.ic_my_order, getString(R.string.my_orders)))
        list.add(DrawerMenu(R.drawable.ic_notification, getString(R.string.notification)))
        list.add(DrawerMenu(R.drawable.ic_points, getString(R.string.points)))
        list.add(DrawerMenu(R.drawable.ic_wallet, getString(R.string.wallet)))
        adapter.setData(list)
    }

}