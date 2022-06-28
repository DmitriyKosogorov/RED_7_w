package com.example.a7red_0_0

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.TextView

class pass_turn_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass_turn)

        var intent_1: Intent =intent
        //var deck=intent_1.getParcelableArrayListExtra<Card_Class>("deck")
        var rule_card=intent_1.getParcelableExtra<Card_Class>("rule")
        var hand_0=intent_1.getParcelableArrayListExtra<Card_Class>("hand_1")
        var hand_1=intent_1.getParcelableArrayListExtra<Card_Class>("hand_0")
        var palette_0=intent_1.getParcelableArrayListExtra<Card_Class>("palette_1")
        var palette_1=intent_1.getParcelableArrayListExtra<Card_Class>("palette_0")
        var isred=intent_1.getBooleanExtra("isred", false)
        var is_style_n=intent_1.getBooleanExtra("style_n", false)
        var player=intent_1.getIntExtra("player", 1)
        findViewById<TextView>(R.id.pass_text).text="Игрок "+player.toString()+", вы готовы начать?"

        val pass_button=findViewById<Button>(R.id.button)
        pass_button.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            //intent.putParcelableArrayListExtra("deck", Shuffled_Deck as java.util.ArrayList<out Parcelable>)
            intent.putExtra("rule", rule_card)

            intent.putParcelableArrayListExtra("hand_0", hand_0 as java.util.ArrayList<out Parcelable>)
            intent.putParcelableArrayListExtra("hand_1", hand_1 as java.util.ArrayList<out Parcelable>)
            intent.putParcelableArrayListExtra("palette_1", palette_1 as java.util.ArrayList<out Parcelable>)
            intent.putParcelableArrayListExtra("palette_0", palette_0 as java.util.ArrayList<out Parcelable>)
            intent.putExtra("isred", isred)
            intent.putExtra("player", player)
            intent.putExtra("style_n", is_style_n)

            startActivity(intent)
        }
    }
}