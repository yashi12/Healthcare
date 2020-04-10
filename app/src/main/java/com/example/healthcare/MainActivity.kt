package com.example.healthcare

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.snackbar.Snackbar
import eu.long1.spacetablayout.SpaceTabLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentList:ArrayList<Fragment> = ArrayList()
        fragmentList.add(News())
        fragmentList.add(Enquire())
        fragmentList.add(Analysis())
        fragmentList.add(Location())
        fragmentList.add(Profile())
        val coordinatorLayout=findViewById<CoordinatorLayout>(R.id.coordinatorLayout)
        val tabLayout:SpaceTabLayout=findViewById(R.id.spaceTabLayout)

        val viewPager:ViewPager=findViewById(R.id.viewPager)
        tabLayout.initialize(viewPager ,supportFragmentManager,fragmentList,savedInstanceState)
        tabLayout.setTabFourIcon(R.drawable.ic_location_city_black_24dp)
        tabLayout.setTabFiveIcon(R.drawable.ic_people_black_24dp)
        tabLayout.tabOneOnClickListener = View.OnClickListener {
            Snackbar.make(coordinatorLayout, "Welcome to SpaceTabLayout", Snackbar.LENGTH_SHORT).show()
        }

        tabLayout.setOnClickListener {
            Toast.makeText(application, "" + tabLayout.currentPosition, Toast.LENGTH_SHORT).show()
        }

    }
}
