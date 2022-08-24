package com.paybrother

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import com.mikepenz.iconics.typeface.FontAwesome
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.SectionDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.paybrother.room.Reservation
import com.paybrother.room.database.ReservationDatabase
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.launch
import java.util.*


class HomeActivity : AppCompatActivity() {


    val datePickerFragment = DatePickerFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Drawer()
            .withActivity(this)
            .withToolbar(toolbar)
            .withActionBarDrawerToggle(true)
            .withHeader(R.layout.drawer_header)
            .addDrawerItems(
                PrimaryDrawerItem().withName(R.string.drawer_item_masters).withIcon(FontAwesome.Icon.faw_users).withBadge(
                    "99"
                ).withIdentifier(1),
                PrimaryDrawerItem().withName(R.string.drawer_item_reservations).withIcon(FontAwesome.Icon.faw_eye).withBadge(
                    "6"
                ).withIdentifier(2),
                SectionDrawerItem().withName(R.string.drawer_item_settings),
                SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog),
                DividerDrawerItem(),
                SecondaryDrawerItem().withName(R.string.drawer_item_about).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1)
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener{
                override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long,drawerItem: IDrawerItem?) {
                    when(drawerItem?.identifier){
                        1 -> supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.parent_container, HomeFragment()).commit()
                        2 -> supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.parent_container, ReservationsFragment()).commit()
                    }
                }
            })
            .build()

        supportFragmentManager.beginTransaction().add(R.id.parent_container, HomeFragment())
            .commit()

        setupClickListeners()
    }


    override fun onResume() {
        super.onResume()

        Thread {
            var reservationDb : ReservationDatabase = ReservationDatabase.getInstance(this)
            reservationDb.reservationDao().getReservationList()
        }.start()



    }

    private fun setupClickListeners(){
        fab.setOnClickListener {
            this?.supportFragmentManager?.let { datePickerFragment.show(it, "datePicker") }
        }
    }
}
