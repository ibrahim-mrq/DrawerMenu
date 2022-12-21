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

    private fun initDrawerMenuList(binding: LayoutBackBinding) {
        val adapter = DrawerMenuAdapter(this)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        val list = arrayListOf<DrawerMenu>()
        list.add(DrawerMenu(R.drawable.ic_menu, "My Account"))
        list.add(DrawerMenu(R.drawable.ic_menu, "Favorite"))
        list.add(DrawerMenu(R.drawable.ic_menu, "About App"))
        list.add(DrawerMenu(R.drawable.ic_menu, "Share App"))
        list.add(DrawerMenu(R.drawable.ic_menu, "Setting"))
        list.add(DrawerMenu(R.drawable.ic_menu, "Contact Us"))
        adapter.setData(list)
    }

}