package com.example.a7red_0_0

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class manual : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual)
        var rules_list=resources.getStringArray(R.array.manual_pages)

        var manual_text=findViewById<TextView>(R.id.manual_text)
        var manual_img=findViewById<ImageView>(R.id.manual_img)
        var img_name="manual_1"

        var page_number=0

        var pages=findViewById<TextView>(R.id.manual_page_number)
        pages.text=(page_number+1).toString()+"/"+(rules_list.size).toString()

        manual_text.text=rules_list[0]
        findViewById<Button>(R.id.back_button).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        findViewById<Button>(R.id.manual_right).setOnClickListener {
            if(page_number<rules_list.size-1) {
                page_number += 1
                pages.text = (page_number+1).toString() + "/" + (rules_list.size).toString()
                manual_text.text = rules_list[page_number]
                img_name="manual_"+page_number.toString()
                val imager = resources.getIdentifier(
                    img_name, "drawable",
                    packageName
                )
                if(imager!=null)
                {
                    manual_img.setImageResource(imager)
                }
            }
        }
        findViewById<Button>(R.id.manual_left).setOnClickListener {
            if(page_number>0) {
                page_number -= 1
                pages.text = (page_number + 1).toString() + "/" + (rules_list.size).toString()
                manual_text.text = rules_list[page_number]

                img_name = "manual_" + page_number.toString()
                val imager = resources.getIdentifier(
                    img_name, "drawable",
                    packageName
                )
                if (imager != null) {
                    manual_img.setImageResource(imager)
                }
            }
        }


    }
}