package com.paybrother.main.app.navigation

import com.paybrother.R

sealed class BottomNavItem(var title:String, var icon:Int, var screen_route:String){
    object Home : BottomNavItem("Home", R.drawable.ic_home,"home")
    object MyNetwork: BottomNavItem("Empty screen",R.drawable.ic_my_network,"my_network")
    object AddPost: BottomNavItem("New Event",R.drawable.ic_post,"add_post")
    object Notification: BottomNavItem("Notifications",R.drawable.ic_notification,"notification")
    object Jobs: BottomNavItem("Contacts",R.drawable.ic_job,"jobs")
}

sealed class HomeNavItem(var title: String, var screen_route: String){
    object EditScreen: HomeNavItem("EditScree", "editScreen")
}
