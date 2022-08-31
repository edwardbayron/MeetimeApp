package com.paybrother

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.mikepenz.iconics.typeface.FontAwesome
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.SectionDrawerItem
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.paybrother.room.database.ReservationDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    val datePickerFragment = DatePickerFragment()
    private var roomDb : ReservationDatabase? = null

    // Request code for READ_CONTACTS. It can be any number > 0.
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        roomDb = ReservationDatabase.getInstance(this)

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
                PrimaryDrawerItem().withName(R.string.drawer_item_contacts).withIcon(FontAwesome.Icon.faw_eye).withBadge(
                    "6"
                ).withIdentifier(3),
                SectionDrawerItem().withName(R.string.drawer_item_settings),
                SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog),
                DividerDrawerItem(),
                SecondaryDrawerItem().withName(R.string.drawer_item_about).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1)
            ).withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener{
                override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long,drawerItem: IDrawerItem?) {
                    when(drawerItem?.identifier){
                        1 -> supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.parent_container, HomeFragment()).commit()
                        2 -> supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.parent_container, ReservationsFragment()).commit()
                        3 -> supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.parent_container, ContactsFragment()).commit()
                    }
                }
            })
            .build()

        supportFragmentManager.beginTransaction().add(R.id.parent_container, HomeFragment())
            .commit()

        setupClickListeners()
        permissionsContacts()
    }


    override fun onResume() {
        super.onResume()

        Thread {
            roomDb?.reservationDao()?.getReservationList()
        }.start()



    }

    private fun setupClickListeners(){
        fab.setOnClickListener {
            this.supportFragmentManager.let { datePickerFragment.show(it, "datePicker") }
        }
    }



    private fun permissionsContacts(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        }
    }
}
