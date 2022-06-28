package com.example.a7red_0_0

import android.content.res.Resources
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import kotlin.random.Random

import android.R.string
import android.content.Intent
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.widget.*

import androidx.annotation.NonNull




class MainActivity : AppCompatActivity() {

    var your_hand_cards = mutableListOf<Card_Class>()
    var your_palette_cards = mutableListOf<Card_Class>()
    var opps_hand_cards = mutableListOf<Card_Class>()
    var opps_palette_cards = mutableListOf<Card_Class>()
    val deck = mutableListOf<Card_Class>()
    val hand_0 = mutableListOf<TextView>()
    val hand_1=mutableListOf<TextView>()
    val palette_0 = mutableListOf<TextView>()
    val palette_1 = mutableListOf<TextView>()
    var Shuffled_Deck=mutableListOf<Card_Class>()
    val rules_map = mapOf(
        "Red" to "greatest_all",
        "Orange" to "more_samevalues",
        "Yellow" to "more_samecolor",
        "Green" to "more_odds",
        "LBlue" to "more_diffcolor",
        "DBlue" to "more_inrow",
        "Purple" to "more_4less"
    )

    //var last_moved_to_palette: Card_Class? = null
    var is_moved_to_palette=false
    var is_moved_to_rule=false
    var last_moved_to_rule: Card_Class? = null
    var previous_rule = Card_Class()
    var rule_card = Card_Class()
    var isred=true
    var is_style_n=false
    var ider = 0
    var player=1

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        Log.d("deleting all", "AAAAAAAAAA")
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putBoolean("MyBoolean", true)
        savedInstanceState.putDouble("myDouble", 1.9)
        savedInstanceState.putInt("MyInt", 1)
        savedInstanceState.putString("MyString", "Welcome back to Android")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // get values from saved state
        var stringer = savedInstanceState.getString("MyString")
        if (stringer != null)
            Log.d("saved", stringer)

        super.onRestoreInstanceState(savedInstanceState)
    }

    var chosen_ind = -1

    //Oncreate function. Creates a whole board=================================================================================================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val r: Resources = resources

        val handsize = 7
        var hand_size_0 = 7
        var palette_size_0 = 1
        var hand_size_1 = 7
        var palette_size_1 = 1


        var intent_1: Intent =intent
/*
        findViewById<TextView>(R.id.your_hand).height=rule_card.height
        findViewById<TextView>(R.id.your_palette).height=rule_card.height
        findViewById<TextView>(R.id.opps_hand).height=rule_card.height
        findViewById<TextView>(R.id.opps_palette).height=rule_card.height

 */

        var hand_0_intent=intent_1.getParcelableArrayListExtra<Card_Class>("hand_0")
        var hand_1_intent=intent_1.getParcelableArrayListExtra<Card_Class>("hand_1")
        var palette_0_intent=intent_1.getParcelableArrayListExtra<Card_Class>("palette_0")
        var palette_1_intent=intent_1.getParcelableArrayListExtra<Card_Class>("palette_1")
        isred=intent_1.getBooleanExtra("isred",isred)
        if(hand_0_intent!=null)
        {
            //Log.d("intenter", hand_0_intent.size.toString())
            your_hand_cards=hand_0_intent
            hand_size_0=your_hand_cards.size
        }
        if(hand_1_intent!=null)
        {
            opps_hand_cards=hand_1_intent
            hand_size_1=opps_hand_cards.size
        }
        if(palette_0_intent!=null)
        {
            your_palette_cards=palette_0_intent
            palette_size_0=your_palette_cards.size
        }
        if(palette_1_intent!=null)
        {
            opps_palette_cards=palette_1_intent
            palette_size_1=opps_palette_cards.size
        }

        player=intent_1.getIntExtra("player", 1)
        Log.d("isred", isred.toString())
        if(isred==false)
        {
            Log.d("isred","here")
            var view=findViewById<TextView>(R.id.your_hand_text)
            view.setBackgroundColor(Color.parseColor("#990000FF"))
            findViewById<TextView>(R.id.your_palette_text).setBackgroundColor(Color.parseColor("#990000FF"))
            findViewById<TextView>(R.id.opps_hand_text).setBackgroundColor(Color.parseColor("#99FF0000"))
            findViewById<TextView>(R.id.opps_palette_text).setBackgroundColor(Color.parseColor("#99FF0000"))
        }

        is_style_n=intent_1.getBooleanExtra("style_n",is_style_n)

        val layout = findViewById<ConstraintLayout>(R.id.root)
        val pal_back = findViewById<TextView>(R.id.your_palette_back)

        val screenwidth = Resources.getSystem().getDisplayMetrics().widthPixels

        var screenpart = screenwidth / handsize

        val numbers = listOf(1, 2, 3, 4, 5, 6, 7)
        val colors = listOf(
            "Red",
            "Orange",
            "Yellow",
            "Green",
            "LBlue",
            "DBlue",
            "Purple",
            "Black",
            "Brown",
            "Turquoise",
            "Pink",
            "White"
        )
        //val ru_colors=mapOf("Red" to "Красный", "Orange" to "Оранжевый", "Yellow" to "Жёлтый", "Green" to "Зелёный", "LBlue" to "Голубой", "DBlue" to "Синий", "Purple" to "Фиолетовый", "Black" to "Чёрный", "Brown" to "Коричневый","Turquoise" to "Бирюзовый", "Pink" to "Розовый", "White" to "Белый")

        //creating deck



        if(player==1)
            player=2
        else
            player=1


        for (i in 0..6) {
            for (j in 1..7)
                deck.add(Card_Class(colors[i], j, rules_map[colors[i]]!!, i + j - 1))
        }
        Shuffled_Deck = deck.shuffled().toMutableList()

        //creating your hand

        for (i in 0..(hand_size_0 - 1)) {

            var card:Card_Class
            if(hand_0_intent!=null)
                card=hand_0_intent[i]
            else
                card = Shuffled_Deck[i]

            hand_0.add(TextView(this))
            if(hand_0_intent==null)
                your_hand_cards.add(card)
            Log.d("backgrounder_n", card.backgrounder_n.toString())
            /*
            val width_px =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, card.width, r.displayMetrics)
            val height_px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                card.height,
                r.displayMetrics
            )

             */
            val width_px=card.width
            val height_px=card.height
            Log.d("size", screenpart.toString())
            Log.d("size", width_px.toString())
            val params = ConstraintLayout.LayoutParams(
                //ViewGroup.LayoutParams.WRAP_CONTENT,
                //ViewGroup.LayoutParams.WRAP_CONTENT
                width_px.toInt(), height_px.toInt()
            )

            params.bottomToBottom = R.id.your_hand
            hand_0[i].setId(ider)
            ider += 1

            if (i > 0) {
                params.leftToRight = hand_0[i - 1].id
                //if (screenpart < width_px)
                params.setMargins((screenpart - width_px).toInt(), 0, 0, 0)
            } else {
                params.leftToRight = R.id.your_hand
            }

            hand_0[i].layoutParams = params
            hand_0[i].setText(card.number.toString())
            hand_0[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 40f)
            if(is_style_n==false)
                hand_0[i].setBackgroundResource(card.backgrounder)
            else
                hand_0[i].setBackgroundResource(card.backgrounder_n)
            hand_0[i].setTextColor(Color.WHITE)
            hand_0[i].setGravity(Gravity.CENTER)

            hand_0[i].setOnClickListener {
                val mBuilder = AlertDialog.Builder(this)
                val mLayout = LinearLayout(this)

                val card_image = TextView(this)

                val Color_txt = TextView(this)
                val Number_txt = TextView(this)
                val Rule_txt = TextView(this)

                if(is_style_n==false)
                    card_image.setBackgroundResource(card.backgrounder)
                else
                    card_image.setBackgroundResource(card.backgrounder_n)
                card_image.setGravity(Gravity.CENTER)
                card_image.setTextColor(Color.WHITE)
                card_image.setText(card.number.toString())
                card_image.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80f)

                Color_txt.text = ("Цвет: " + card.ru_color)
                Number_txt.text = ("Число: " + card.number)
                Rule_txt.text = ("Правило: " + card.ru_rule)
                mLayout.orientation = LinearLayout.VERTICAL
                mLayout.addView(card_image)
                mLayout.addView(Color_txt)
                mLayout.addView(Number_txt)
                mLayout.addView(Rule_txt)
                mLayout.setPadding(50, 40, 50, 10)

                mBuilder.setView(mLayout)

                mBuilder.setNeutralButton("Закрыть") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
                //show dialog
                mBuilder.create().show()
                //return@setOnLongClickListener true

            }

            hand_0[i].setOnLongClickListener {
                select_card_inhand(i)
                true
            }

            layout?.addView(hand_0[i])

        }

        //Creating first player's palette

        screenpart = screenwidth / palette_size_0
        var top_deck_index = 7
        for (i in 0..(palette_size_0 - 1)) {

            var card:Card_Class
            if(palette_0_intent!=null)
                card=palette_0_intent[i]
            else
                card = Shuffled_Deck[top_deck_index + i]

            palette_0.add(TextView(this))
            if(palette_0_intent==null)
                your_palette_cards.add(card)

            val width_px=card.width
            val height_px=card.height
            val params = ConstraintLayout.LayoutParams(width_px.toInt(), height_px.toInt())

            palette_0[i].setId(ider)
            ider += 1

            params.bottomToBottom = R.id.your_palette

            if (i > 0) {
                params.leftToRight = palette_0[i - 1].id
                //if (screenpart < width_px)

            } else {
                params.leftToRight = R.id.your_palette
            }
            params.setMargins((screenpart - width_px).toInt(), 0, 0, 0)
            palette_0[i].layoutParams = params
            palette_0[i].setText(card.number.toString())
            palette_0[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 40f)
            if(is_style_n==false)
                palette_0[i].setBackgroundResource(card.backgrounder)
            else
                palette_0[i].setBackgroundResource(card.backgrounder_n)
            palette_0[i].setTextColor(Color.WHITE)
            palette_0[i].setGravity(Gravity.CENTER)

            palette_0[i].setOnClickListener {
                if (chosen_ind != -1 && is_moved_to_palette==false)
                    put_into_palette()
                else {
                    val mBuilder = AlertDialog.Builder(this)
                    val mLayout = LinearLayout(this)

                    val card_image = TextView(this)

                    val Color_txt = TextView(this)
                    val Number_txt = TextView(this)
                    val Rule_txt = TextView(this)

                    if(is_style_n==false)
                        card_image.setBackgroundResource(card.backgrounder)
                    else
                        card_image.setBackgroundResource(card.backgrounder_n)
                    card_image.setGravity(Gravity.CENTER)
                    card_image.setTextColor(Color.WHITE)
                    card_image.setText(card.number.toString())
                    card_image.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80f)

                    Color_txt.text = ("Цвет: " + card.ru_color)
                    Number_txt.text = ("Число: " + card.number)
                    Rule_txt.text = ("Правило: " + card.ru_rule)
                    mLayout.orientation = LinearLayout.VERTICAL
                    mLayout.addView(card_image)
                    mLayout.addView(Color_txt)
                    mLayout.addView(Number_txt)
                    mLayout.addView(Rule_txt)
                    mLayout.setPadding(50, 40, 50, 10)

                    mBuilder.setView(mLayout)

                    mBuilder.setNeutralButton("Закрыть") { dialogInterface, i ->
                        dialogInterface.dismiss()
                    }
                    //show dialog
                    mBuilder.create().show()
                }
            }

            layout?.addView(palette_0[i])

        }

        //creating opponent's palette
        screenpart = screenwidth / palette_size_1
        top_deck_index = 8
        for (i in 0..(palette_size_1 - 1)) {

            var card:Card_Class
            if(palette_1_intent!=null)
                card=palette_1_intent[i]
            else
                card = Shuffled_Deck[top_deck_index + i]

            palette_1.add(TextView(this))

            if(palette_1_intent==null)
                opps_palette_cards.add(card)

            val width_px=card.width
            val height_px=card.height
            val params = ConstraintLayout.LayoutParams(width_px.toInt(), height_px.toInt())

            palette_1[i].setId(ider)
            ider += 1

            params.bottomToBottom = R.id.opps_palette

            if (i > 0) {
                params.leftToRight = palette_1[i - 1].id
                //if (screenpart < width_px)

            } else {
                params.leftToRight = R.id.opps_palette
            }
            params.setMargins((screenpart - width_px).toInt(), 0, 0, 0)
            palette_1[i].layoutParams = params
            palette_1[i].setText(card.number.toString())
            palette_1[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 40f)
            if(is_style_n==false)
                palette_1[i].setBackgroundResource(card.backgrounder)
            else
                palette_1[i].setBackgroundResource(card.backgrounder_n)
            palette_1[i].setTextColor(Color.WHITE)
            palette_1[i].setGravity(Gravity.CENTER)

            palette_1[i].setOnClickListener {
                val mBuilder = AlertDialog.Builder(this)
                val mLayout = LinearLayout(this)

                val card_image = TextView(this)

                val Color_txt = TextView(this)
                val Number_txt = TextView(this)
                val Rule_txt = TextView(this)

                if(is_style_n==false)
                    card_image.setBackgroundResource(card.backgrounder)
                else
                    card_image.setBackgroundResource(card.backgrounder_n)
                card_image.setGravity(Gravity.CENTER)
                card_image.setTextColor(Color.WHITE)
                card_image.setText(card.number.toString())
                card_image.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80f)

                Color_txt.text = ("Цвет: " + card.ru_color)
                Number_txt.text = ("Число: " + card.number)
                Rule_txt.text = ("Правило: " + card.ru_rule)
                mLayout.orientation = LinearLayout.VERTICAL
                mLayout.addView(card_image)
                mLayout.addView(Color_txt)
                mLayout.addView(Number_txt)
                mLayout.addView(Rule_txt)
                mLayout.setPadding(50, 40, 50, 10)

                mBuilder.setView(mLayout)

                mBuilder.setNeutralButton("Закрыть") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
                //show dialog
                mBuilder.create().show()

            }

            layout?.addView(palette_1[i])

        }


        //Creating opponent's hand
        screenpart = screenwidth / hand_size_1
        top_deck_index = 20
        for (i in 0..(hand_size_1 - 1)) {


            var card:Card_Class
            if(hand_1_intent!=null)
                card=hand_1_intent[i]
            else
                card = Shuffled_Deck[top_deck_index + i]

            hand_1.add(TextView(this))
            if(hand_0_intent==null)
                opps_hand_cards.add(card)
            val width_px=card.width
            val height_px=card.height
            val params = ConstraintLayout.LayoutParams(
                //ViewGroup.LayoutParams.WRAP_CONTENT,
                //ViewGroup.LayoutParams.WRAP_CONTENT
                width_px.toInt(), height_px.toInt()
            )

            params.bottomToBottom = R.id.opps_hand
            hand_1[i].setId(ider)
            ider += 1

            if (i > 0) {
                params.leftToRight = hand_1[i - 1].id
                //if (screenpart < width_px)
                params.setMargins((screenpart - width_px).toInt(), 0, 0, 0)
            } else {
                params.leftToRight = R.id.opps_hand
            }

            hand_1[i].layoutParams = params
            if(is_style_n==false)
                hand_1[i].setBackgroundResource(R.drawable.backside)
            else
                hand_1[i].setBackgroundResource(R.drawable.backside_n)

            layout?.addView(hand_1[i])

        }



        pal_back.setOnClickListener {
            if(is_moved_to_palette==false)
                put_into_palette()
        }


        val current_rule = findViewById<TextView>(R.id.rule)
        if(is_style_n==false)
            current_rule.setBackgroundResource(rule_card.backgrounder)
        else
            current_rule.setBackgroundResource(rule_card.backgrounder_n)
        val Rule_carder=intent_1.getParcelableExtra<Card_Class>("rule")
        if(Rule_carder!=null)
        {
            rule_card=Rule_carder
            if(is_style_n==false)
                current_rule.setBackgroundResource(rule_card.backgrounder)
            else
                current_rule.setBackgroundResource(rule_card.backgrounder_n)
            current_rule.text=rule_card.number.toString()
            set_rule_text(rule_card.ru_rule)
        }
        current_rule.setOnClickListener {
            if (chosen_ind != -1 && is_moved_to_rule==false) {
                is_moved_to_rule=true
                pal_back.setBackgroundColor(0x00000000)
                val layout = findViewById<ConstraintLayout>(R.id.root)
                current_rule.text = your_hand_cards[chosen_ind].number.toString()
                if(is_style_n==false)
                    current_rule.setBackgroundResource(your_hand_cards[chosen_ind].backgrounder)
                else
                    current_rule.setBackgroundResource(your_hand_cards[chosen_ind].backgrounder_n)
                previous_rule = rule_card
                rule_card = your_hand_cards[chosen_ind]
                last_moved_to_rule = rule_card
                set_rule_text(rule_card.ru_rule)
                layout.removeView(hand_0[chosen_ind])
                remove_from_hand()
                chosen_ind = -1
            }
        }
        var cancel_button=findViewById<Button>(R.id.cancel_button)
        cancel_button.setOnClickListener{
            put_back_in_hand_from_rule()
            put_back_in_hand_from_palette()
        }
        val win_button=findViewById<Button>(R.id.pass_button)
        win_button.setOnClickListener{
            if(check_winner(rule_card,your_palette_cards)==0)
                pass_turn()
            else
            {
                var winner_text=findViewById<TextView>(R.id.win_text)
                winner_text.text="Вы не выигрываете по текущему правилу"
            }
        }
        findViewById<Button>(R.id.help_button).setOnClickListener {
            if(can_win())
                call_help()
        }
        if(check_winner(rule_card,your_palette_cards)!=1)
            pass_turn()
        if(can_win()==false)
        {
            findViewById<TextView>(R.id.win_text).text="Вы не можете победить"
        }
    }

//function which work with menu================================================================================
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

//function which work with menu elements=========================================================================
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.restart -> {
                startActivity(Intent(this, MainActivity::class.java))
                return true
            }
            R.id.manual -> {
                startActivity(Intent(this, manual::class.java))
                return true
            }
            R.id.change_style -> {
                change_style_on()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    //function which changes style of cards======================================================================
    fun change_style_on()
    {
        if(is_style_n==true)
        {
            is_style_n=false
        }
        else
        {
            is_style_n=true
        }
        val intent = Intent(this@MainActivity, MainActivity::class.java)
        //intent.putParcelableArrayListExtra("deck", Shuffled_Deck as java.util.ArrayList<out Parcelable>)
        intent.putExtra("rule", rule_card)
        intent.putParcelableArrayListExtra("hand_0", your_hand_cards as java.util.ArrayList<out Parcelable>)
        intent.putParcelableArrayListExtra("hand_1", opps_hand_cards as java.util.ArrayList<out Parcelable>)
        intent.putParcelableArrayListExtra("palette_1", opps_palette_cards as java.util.ArrayList<out Parcelable>)
        intent.putParcelableArrayListExtra("palette_0", your_palette_cards as java.util.ArrayList<out Parcelable>)
        if(isred==true)
            intent.putExtra("isred", true)
        else
            intent.putExtra("isred", false)
        intent.putExtra("style_n",is_style_n)
        startActivity(intent)

    }

    //function which changes main rules text. used when rule card changes==================================================================
    fun set_rule_text(Text:String)
    {
        val rule_text = findViewById<TextView>(R.id.rule_text)
        val lengther = Text.length
        if (lengther < 20)
            rule_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
        else
            if (lengther < 40)
                rule_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            else
                rule_text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
        rule_text.text = "Правило: " + Text
    }


    //function of revealing selected card in hand. used in onlongclicklistener in hand=====================================================
    fun select_card_inhand(i:Int)
    {
        val screenwidth = Resources.getSystem().getDisplayMetrics().widthPixels
        val pal_back = findViewById<TextView>(R.id.your_palette_back)
        val screenpart = screenwidth / hand_0.size
        if (chosen_ind == i) {
            val param = hand_0[i].layoutParams as ViewGroup.MarginLayoutParams
            if(i>0)
                param.setMargins((screenpart - hand_0[0].width).toInt(), 0, 0, 0)
            else
                param.setMargins(0, 0, 0, 0)
            hand_0[i].layoutParams = param
            chosen_ind = -1
            pal_back.setBackgroundColor(0x00000000)
        } else {
            if (chosen_ind != -1) {
                val param = hand_0[chosen_ind].layoutParams as ViewGroup.MarginLayoutParams
                if(i>0)
                    param.setMargins((screenpart - hand_0[0].width).toInt(), 0, 0, 0)
                else
                    param.setMargins(0, 0, 0, 0)
                hand_0[chosen_ind].layoutParams = param
            }

            val param_1 = hand_0[i].layoutParams as ViewGroup.MarginLayoutParams
            if(i>0)
                param_1.setMargins((screenpart - hand_0[0].width).toInt(), 0, 0, 50)
            else
                param_1.setMargins(0, 0, 0, 50)
            hand_0[i].layoutParams = param_1
            chosen_ind = i
            pal_back.setBackgroundColor(0x4400FF00)
        }

    }


    //function of returning card from rule area to hand area. used incancel_button function=================================================
    fun put_back_in_hand_from_rule() {
        if (last_moved_to_rule != null) {
            is_moved_to_rule=false
            val r: Resources = resources
            val layout = findViewById<ConstraintLayout>(R.id.root)
            val pal_back = findViewById<TextView>(R.id.your_palette_back)
            var card = last_moved_to_rule!!
            val screenwidth = Resources.getSystem().getDisplayMetrics().widthPixels
            /*
            val width_px =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, card.width, r.displayMetrics)

             */
            val width_px=card.width
            hand_0.add(TextView(this))
            your_hand_cards.add(card)
            val i = hand_0.size - 1
            val screenpart = screenwidth / hand_0.size
            val params = ConstraintLayout.LayoutParams(
                //ViewGroup.LayoutParams.WRAP_CONTENT,
                //ViewGroup.LayoutParams.WRAP_CONTENT
                hand_0[0].width, hand_0[0].height
            )

            params.bottomToBottom = R.id.your_hand
            hand_0[i].setId(ider)
            ider += 1

            if (i > 0) {
                params.leftToRight = hand_0[i - 1].id
                //if (screenpart < width_px)
                params.setMargins((screenpart - width_px).toInt(), 0, 0, 0)
            } else {
                params.leftToRight = R.id.your_hand
            }

            hand_0[i].layoutParams = params
            hand_0[i].setText(card.number.toString())
            hand_0[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 40f)
            if(is_style_n==false)
                hand_0[i].setBackgroundResource(card.backgrounder)
            else
                hand_0[i].setBackgroundResource(card.backgrounder_n)
            hand_0[i].setTextColor(Color.WHITE)
            hand_0[i].setGravity(Gravity.CENTER)

            hand_0[i].setOnClickListener {
                val mBuilder = AlertDialog.Builder(this)
                val mLayout = LinearLayout(this)

                val card_image = TextView(this)

                val Color_txt = TextView(this)
                val Number_txt = TextView(this)
                val Rule_txt = TextView(this)

                if(is_style_n==false)
                    card_image.setBackgroundResource(card.backgrounder)
                else
                    card_image.setBackgroundResource(card.backgrounder_n)
                card_image.setGravity(Gravity.CENTER)
                card_image.setTextColor(Color.WHITE)
                card_image.setText(card.number.toString())
                card_image.setTextSize(TypedValue.COMPLEX_UNIT_SP, 80f)

                Color_txt.text = ("Цвет: " + card.ru_color)
                Number_txt.text = ("Число: " + card.number)
                Rule_txt.text = ("Правило: " + card.ru_rule)
                mLayout.orientation = LinearLayout.VERTICAL
                mLayout.addView(card_image)
                mLayout.addView(Color_txt)
                mLayout.addView(Number_txt)
                mLayout.addView(Rule_txt)
                mLayout.setPadding(50, 40, 50, 10)

                mBuilder.setView(mLayout)

                mBuilder.setNeutralButton("Закрыть") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
                //show dialog
                mBuilder.create().show()

            }

            hand_0[i].setOnLongClickListener {
                select_card_inhand(i)
                true
            }

            layout?.addView(hand_0[i])

            last_moved_to_rule = null
            rule_card = previous_rule
            val rule = findViewById<TextView>(R.id.rule)
            if(is_style_n==false)
                rule.setBackgroundResource(rule_card.backgrounder)
            else
                rule.setBackgroundResource(rule_card.backgrounder_n)
            rule.setText(rule_card.number.toString())

            set_rule_text(rule_card.ru_rule)
/*
            for(card in hand_0)
            {
                var params=card.layoutParams as ViewGroup.MarginLayoutParams
                if(i>0)
                    params.setMargins((screenpart - card.width).toInt(), 0, 0, 0)
                else
                    params.setMargins(0, 0, 0, 0)
                card.layoutParams=params
            }

 */

        }

    }

    //function of returning card from palette area to hand area. used in cancel_button function=================================================
    fun put_back_in_hand_from_palette()
    {
        if(is_moved_to_palette) {
            val screenwidth = Resources.getSystem().getDisplayMetrics().widthPixels
            hand_0.add(palette_0[palette_0.size - 1])
            val screenpart = (screenwidth / hand_0.size).toInt()
            your_hand_cards.add(your_palette_cards[your_palette_cards.size - 1])
            hand_0[hand_0.size-1].setOnLongClickListener {
                select_card_inhand(hand_0.size - 1)
                true
            }
                var i = hand_0.size - 1
                val params = ConstraintLayout.LayoutParams(hand_0[0].width, hand_0[0].height)
                if (i > 0) {
                    params.setMargins((screenpart - hand_0[0].width).toInt(), 0, 0, 0)
                    params.leftToRight = hand_0[i - 1].id
                }
                else {
                    params.setMargins(0, 0, 0, 0)
                    params.leftToRight = R.id.your_hand
                }
                params.bottomToBottom=R.id.your_hand

                hand_0[i].layoutParams = params

                palette_0.removeAt(palette_0.size - 1)
                your_palette_cards.removeAt(your_palette_cards.size - 1)
                is_moved_to_palette=false

            }


    }

    //function which cause removing card in hand/ used in situations, when card is moved from hand to another area===========================
    fun remove_from_hand() {
        if (chosen_ind != -1) {
            val screenwidth = Resources.getSystem().getDisplayMetrics().widthPixels
            val screenpart = (screenwidth / hand_0.size).toInt()
            val pal_back = findViewById<TextView>(R.id.your_palette_back)
            if (chosen_ind < hand_0.size - 1) {
                val params = ConstraintLayout.LayoutParams(
                    hand_0[chosen_ind + 1].width, hand_0[chosen_ind + 1].height
                )
                params.bottomToBottom = R.id.your_hand
                if (chosen_ind > 0) {
                    params.leftToRight = hand_0[chosen_ind - 1].id
                    params.setMargins((screenpart - hand_0[chosen_ind + 1].width).toInt(), 0, 0, 0)
                }
                else {
                    params.leftToRight = R.id.your_hand
                    params.setMargins(0, 0, 0, 0)
                }

                hand_0[chosen_ind + 1].layoutParams = params
            }
            hand_0.removeAt(chosen_ind)
            your_hand_cards.removeAt(chosen_ind)

            for (i in 0..(hand_0.size - 1)) {
                hand_0[i].setOnLongClickListener {
                    select_card_inhand(i)
                    true
                }
            }
        }
    }

    //function which puts a card into your palette area=====================================================================================
    fun put_into_palette() {

        if (chosen_ind != -1) {
            val pal_back = findViewById<TextView>(R.id.your_palette_back)
            pal_back.setBackgroundColor(0)
            palette_0.add(hand_0[chosen_ind])
            your_palette_cards.add(your_hand_cards[chosen_ind])
            val screenwidth = Resources.getSystem().getDisplayMetrics().widthPixels


            var screenpart = (screenwidth / palette_0.size).toInt()
            for (i in 0..(palette_0.size - 1)) {
                val params_0 = ConstraintLayout.LayoutParams(
                    //ViewGroup.LayoutParams.WRAP_CONTENT,
                    //ViewGroup.LayoutParams.WRAP_CONTENT
                    palette_0[0].width, palette_0[0].height
                )

                params_0.bottomToBottom = R.id.your_palette

                if (i > 0) {
                    params_0.leftToRight = palette_0[i - 1].id

                } else {
                    params_0.leftToRight = R.id.your_palette
                }

                params_0.setMargins((screenpart - palette_0[0].width).toInt(), 0, 0, 0)

                palette_0[i].layoutParams = params_0
                palette_0[i].setOnLongClickListener(null)
            }
            remove_from_hand()
            chosen_ind = -1
            is_moved_to_palette=true

        }
    }


    //function which checks, who is winner================================================================================================
    fun check_winner(target_rule:Card_Class, check_palette:MutableList<Card_Class>): Int {
        //val target_rule=rule_card.rule
        var mas=target_rule.rule.split("_").toTypedArray()
        var winner=1
        if(mas[0]=="greatest")
        {
            if(mas[1]=="all")
            {
                //greatest_all
                var max_value=0
                for(card in check_palette)
                {
                    if(card.value>max_value)
                    {
                        winner=0
                        max_value=card.value
                    }
                }
                for(card in opps_palette_cards)
                {
                    if(card.value>max_value)
                    {
                        winner=1
                        max_value=card.value
                    }
                }
            }
        }
        else
            if(mas[0]=="more")
            {
                if(mas[1]=="odds")
                {
                    //more_odds
                    var odds_1=0
                    var odds_0=0
                    var greatest_odd_0=0
                    var greatest_odd_1=0
                    for(card in check_palette)
                    {
                        if(card.number%2==0)
                        {
                            odds_0+=1
                            if(card.value>greatest_odd_0)
                                greatest_odd_0=card.value
                        }
                    }
                    for(card in opps_palette_cards)
                    {
                        if(card.number%2==0)
                        {
                            odds_1+=1
                            if(card.value>greatest_odd_1)
                                greatest_odd_1=card.value
                        }
                    }
                    if(odds_1>odds_0)
                        winner=1
                    else
                        if(odds_0>odds_1)
                            winner=0
                    else
                        {
                            if(greatest_odd_0>greatest_odd_1)
                                winner=0
                            else
                                winner=1
                        }
                }
                else
                    if(mas[1]=="inrow")
                    {
                        //more_inrow
                        var numbers_0= mutableSetOf<Int>()
                        var numbers_1= mutableSetOf<Int>()
                        var greatest_1=0
                        var greatest_0=0
                        for(card in check_palette)
                        {
                            numbers_0.add(card.number)

                        }
                        var mas_0=numbers_0.sorted().toList()
                        for(card in opps_palette_cards)
                        {
                            numbers_1.add(card.number)
                        }
                        var mas_1=numbers_1.sorted().toList()
                        Log.d("List_1",mas_0.toString())
                        Log.d("List_2",mas_1.toString())
                        var length_0=0
                        var length_1=0
                        var check_length=1
                        greatest_0=mas_0[0]
                        greatest_1=mas_1[0]
                        for(i in 1..(mas_0.size-1))
                        {
                            Log.d("greatest_0",greatest_1.toString())
                            if(mas_0[i]==mas_0[i-1]+1) {
                                check_length += 1
                            }
                            else
                            {
                                if(check_length>length_0)
                                {
                                    length_0=check_length
                                    greatest_0=mas_0[i-1]
                                }
                                if(check_length==length_0)
                                {
                                    if(greatest_0<mas_0[i-1])
                                        greatest_0=mas_0[i-1]
                                }
                                check_length=1
                            }
                        }
                        if(check_length>length_0)
                        {
                            length_0=check_length
                            greatest_0=mas_0[mas_0.size-1]
                        }
                        if(check_length==length_0)
                        {
                            if(greatest_0<mas_0[mas_0.size-1])
                                greatest_0=mas_0[mas_0.size-1]
                        }

                        Log.d("greatest_0",greatest_1.toString())
                        check_length=1
                        for(i in 1..(mas_1.size-1))
                        {
                            Log.d("greatest_1",greatest_1.toString())
                            if(mas_1[i]==mas_1[i-1]+1) {
                                check_length += 1
                            }
                            else
                            {
                                if(check_length>length_1)
                                {
                                    length_1=check_length
                                    greatest_1=mas_1[i-1]
                                }
                                if(check_length==length_1)
                                {
                                    if(greatest_1<mas_1[i-1])
                                        greatest_1=mas_1[i-1]
                                }
                                check_length=1
                            }
                        }
                        Log.d("greatest_check_opps", mas_1.toString())
                        if(check_length>length_1) {
                            length_1 = check_length
                            greatest_1 = mas_1[mas_1.size-1]
                        }
                        if(check_length==length_1)
                        {
                            if(greatest_1<mas_1[mas_1.size-1])
                                greatest_1=mas_1[mas_1.size-1]
                        }
                        Log.d("greatest_1",greatest_1.toString())
                        Log.d("Lengths", length_0.toString()+" "+length_1.toString())
                        if(length_0>length_1)
                            winner=0
                        else
                            if(length_1>length_0)
                                winner=1
                        else
                            {
                                Log.d("greatest_all", greatest_0.toString()+" "+greatest_1.toString())
                                var your_great_card=0
                                var opps_great_card=0
                                for(card in check_palette)
                                {
                                    if(card.number==greatest_0 && card.value>your_great_card)
                                        your_great_card=card.value
                                }
                                for(card in opps_palette_cards)
                                {
                                    Log.d("more_in", greatest_1.toString())
                                    if(card.number==greatest_1 && card.value>opps_great_card)
                                        opps_great_card=card.value
                                }
                                if(your_great_card>opps_great_card)
                                    winner=0
                                else
                                    winner=1
                                Log.d("more_in", your_great_card.toString()+" "+opps_great_card.toString())
                            }

                    }
                else
                    {
                        var word=mas[1]
                        if(word[0].isDigit()) {
                            //more_4less
                            var number = word[0].toInt()-'0'.toInt()
                            var subword = word.drop(1)
                            if (subword == "less")
                            {
                                var your_all=0
                                var opps_all=0
                                var your_greatest=0
                                var opps_greatest=0
                                Log.d("more_4less",your_all.toString()+" "+opps_all.toString())
                                for(card in check_palette)
                                {
                                    if(card.number<=number)
                                    {
                                        your_all+=1
                                        if(card.value>your_greatest)
                                            your_greatest=card.value
                                    }
                                }
                                for(card in opps_palette_cards)
                                {
                                    Log.d("more_4less", card.number.toString()+" "+number)
                                    if(card.number<=number)
                                    {
                                        opps_all+=1
                                        if(card.value>opps_greatest)
                                            opps_greatest=card.value
                                    }
                                }

                                if(your_all>opps_all)
                                    winner=0
                                else
                                    if(opps_all>your_all)
                                        winner=1
                                else
                                    {
                                        if(your_greatest>opps_greatest)
                                            winner=0
                                        else
                                            winner=1
                                    }
                            }
                        }
                        else
                        {
                            var word=mas[1].take(4)
                            var subword=mas[1].drop(4)
                            if(word=="same")
                            {
                                if(subword=="color")
                                {
                                    //more_samecolor
                                    //var colors=listOf("Red","Orange","Yellow","Green","LBlue","DBlue","Purple")
                                    var your_all=0
                                    var opps_all=0
                                    var your_greatest=0
                                    var opps_greatest=0
                                    var checker=0
                                    var greatest_save=0


                                        for(i in 0..check_palette.size-2)
                                        {
                                            checker=0
                                            greatest_save=0
                                            for(j in i..check_palette.size-1)
                                            {
                                                if(check_palette[i].color==check_palette[j].color)
                                                {
                                                    checker+=1
                                                    if(greatest_save<check_palette[i].value)
                                                        greatest_save=check_palette[i].value
                                                    if(greatest_save<check_palette[j].value)
                                                        greatest_save=check_palette[j].value
                                                }
                                            }
                                            if(checker>your_all || (checker==your_all && your_greatest<greatest_save))
                                            {
                                                your_all=checker
                                                your_greatest=greatest_save
                                            }

                                        }

                                        for(i in 0..opps_palette_cards.size-2)
                                        {
                                            checker=0
                                            greatest_save=0
                                            for(j in i..opps_palette_cards.size-1)
                                            {
                                                if(opps_palette_cards[i].color==opps_palette_cards[j].color)
                                                {
                                                    checker+=1
                                                    if(greatest_save<opps_palette_cards[i].value)
                                                        greatest_save=opps_palette_cards[i].value
                                                    if(greatest_save<opps_palette_cards[j].value)
                                                        greatest_save=opps_palette_cards[j].value
                                                }
                                            }
                                            if(checker>opps_all || (checker==opps_all && opps_greatest<greatest_save))
                                            {
                                                opps_all=checker
                                                opps_greatest=greatest_save
                                            }

                                        }

                                        if(your_all>opps_all)
                                            winner=0
                                    else
                                        if(opps_all>your_all)
                                            winner=1
                                    else
                                        {
                                            if(your_greatest>opps_greatest)
                                                winner=0
                                            else
                                                winner=1
                                        }

                                }
                                else
                                    if(subword=="values")
                                    {
                                        //more_samevalue
                                        var your_all=0
                                        var opps_all=0
                                        var your_greatest=0
                                        var opps_greatest=0
                                        var checker=0
                                        var greatest_save=0


                                        for(i in 0..check_palette.size-2)
                                        {
                                            checker=1
                                            greatest_save=0
                                            for(j in i..check_palette.size-1)
                                            {
                                                if(check_palette[i].number==check_palette[j].number)
                                                {
                                                    checker+=1
                                                    if(greatest_save<check_palette[i].value)
                                                        greatest_save=check_palette[i].value
                                                    if(greatest_save<check_palette[j].value)
                                                        greatest_save=check_palette[j].value
                                                }
                                            }
                                            if(checker>your_all || (checker==your_all && your_greatest<greatest_save))
                                            {
                                                your_all=checker
                                                your_greatest=greatest_save
                                            }

                                        }

                                        for(i in 0..opps_palette_cards.size-2)
                                        {
                                            checker=1
                                            greatest_save=0
                                            for(j in i..opps_palette_cards.size-1)
                                            {
                                                if(opps_palette_cards[i].number==opps_palette_cards[j].number)
                                                {
                                                    checker+=1
                                                    if(greatest_save<opps_palette_cards[i].value)
                                                        greatest_save=opps_palette_cards[i].value
                                                    if(greatest_save<opps_palette_cards[j].value)
                                                        greatest_save=opps_palette_cards[j].value
                                                }
                                            }
                                            if(checker>opps_all || (checker==opps_all && opps_greatest<greatest_save))
                                            {
                                                opps_all=checker
                                                opps_greatest=greatest_save
                                            }

                                        }
                                        Log.d("more_samevalue", your_all.toString()+" "+opps_all.toString())
                                        if(your_all>opps_all)
                                            winner=0
                                        else
                                            if(opps_all>your_all)
                                                winner=1
                                            else
                                            {
                                                if(your_greatest>opps_greatest)
                                                    winner=0
                                                else
                                                    winner=1
                                            }
                                    }
                            }
                            else
                                if(word=="diff")
                                {
                                    //more_diffcolor
                                    var your_all=0
                                    var opps_all=0
                                    var your_greatest=0
                                    var opps_greatest=0
                                    var your_colors= mutableSetOf<String>()
                                    var opps_colors= mutableSetOf<String>()
                                    for(card in check_palette)
                                    {
                                        your_colors.add(card.color)
                                        if(card.value>your_greatest)
                                            your_greatest=card.value
                                    }
                                    your_all=your_colors.size

                                    for(card in opps_palette_cards)
                                    {
                                        opps_colors.add(card.color)
                                        if(card.value>opps_greatest)
                                            opps_greatest=card.value
                                    }
                                    opps_all=opps_colors.size

                                    if(your_all>opps_all)
                                        winner=0
                                    else
                                        if(opps_all>your_all)
                                            winner=1
                                        else
                                        {
                                            if(your_greatest>opps_greatest)
                                                winner=0
                                            else
                                                winner=1
                                        }


                                }
                        }
                    }
            }
        return winner
    }


    //function which check the player can win====================================================================================
    fun can_win():Boolean
    {
        if(your_hand_cards.size==0)
            return false
        var hand_for_check=your_hand_cards
        var palette_for_check=your_palette_cards
        for(card in hand_for_check)
        {
            palette_for_check.add(card)
            if(check_winner(rule_card, palette_for_check)==0)
            {
                palette_for_check.removeAt(palette_for_check.size-1)
                return true
            }
            palette_for_check.removeAt(palette_for_check.size-1)
        }
        for(i in 0..(hand_for_check.size-1))
        {
            for(j in (1..hand_for_check.size-1))
            {
                if(i!=j)
                {
                    palette_for_check.add(hand_for_check[j])
                    if(check_winner(hand_for_check[i], palette_for_check)==0)
                    {
                        palette_for_check.removeAt(palette_for_check.size-1)
                        return true
                    }
                    palette_for_check.removeAt(palette_for_check.size-1)
                }
            }
        }
        return false
    }

    //function which help you maks turn=======================================================================
    fun call_help()
    {
        Log.d("help", "called")
        var hand_for_check=your_hand_cards.toMutableList()
        var palette_for_check=your_palette_cards.toMutableList()
        for(i in 0..hand_for_check.size-1)
        {
            Log.d("help called","1")
            palette_for_check.add(hand_for_check[i])
            if(check_winner(rule_card, palette_for_check)==0)
            {
                palette_for_check.removeAt(palette_for_check.size-1)
                select_card_inhand(i)
                return
            }
            palette_for_check.removeAt(palette_for_check.size-1)
        }
        for(i in 0..hand_for_check.size-1)
        {
            Log.d("help called","2")
            Log.d("help_called_2",i.toString())
            if(check_winner(hand_for_check[i], palette_for_check)==0)
            {
                select_card_inhand(i)
                return
            }
        }
        for(i in 0..hand_for_check.size-1)
        {
            Log.d("help called","3")
            for(j in (1..hand_for_check.size-1))
            {
                if(i!=j)
                {
                    palette_for_check.add(hand_for_check[j])
                    if(check_winner(hand_for_check[i], palette_for_check)==0)
                    {
                        palette_for_check.removeAt(palette_for_check.size-1)
                        select_card_inhand(i)
                        return
                    }
                    palette_for_check.removeAt(palette_for_check.size-1)
                }
            }
        }
        return
    }

    //pass turn function. cause to change activity=================================================================================
    fun pass_turn()
    {
        val intent = Intent(this@MainActivity, pass_turn_activity::class.java)
        //intent.putParcelableArrayListExtra("deck", Shuffled_Deck as java.util.ArrayList<out Parcelable>)
        intent.putExtra("rule", rule_card)
        intent.putParcelableArrayListExtra("hand_0", your_hand_cards as java.util.ArrayList<out Parcelable>)
        intent.putParcelableArrayListExtra("hand_1", opps_hand_cards as java.util.ArrayList<out Parcelable>)
        intent.putParcelableArrayListExtra("palette_1", opps_palette_cards as java.util.ArrayList<out Parcelable>)
        intent.putParcelableArrayListExtra("palette_0", your_palette_cards as java.util.ArrayList<out Parcelable>)
        if(isred==true)
            intent.putExtra("isred", false)
        else
            intent.putExtra("isred", true)
        intent.putExtra("player", player)
        intent.putExtra("style_n",is_style_n)
        startActivity(intent)
    }

}