package com.example.a7red_0_0

import android.content.res.Resources
import android.os.Parcel
import android.os.Parcelable
import android.util.Log


class Card_Class(Color:String="Red", Number:Int=7, Rule:String="greatest_all", Index:Int=0): Parcelable {

    var color=Color
    var ru_color="Красный"
    var rule=Rule
    var ru_rule=""
    var number=Number
    //var width=72f
    //var height=100f
    var width=0
    var height=0
    var textSize=25
    var index=Index
    var backgrounder=R.drawable.red
    var backgrounder_n=R.drawable.red_n
    var value=0

    constructor(parcel: Parcel) : this() {
        color = parcel.readString()?:""
        ru_color = parcel.readString()?:""
        rule = parcel.readString()?:""
        ru_rule = parcel.readString()?:""
        number = parcel.readInt()
        //width = parcel.readFloat()
        //height = parcel.readFloat()
        width=parcel.readInt()
        height=parcel.readInt()
        textSize = parcel.readInt()
        index = parcel.readInt()
        backgrounder = parcel.readInt()
        value = parcel.readInt()
        backgrounder_n=parcel.readInt()
    }

    init {
        if(number<8 && number>0)
            number=number
        else
            number=7

        value=number*10

        val screenheight = Resources.getSystem().getDisplayMetrics().heightPixels

        height=(screenheight*0.14).toInt()
        width=(height*0.73).toInt()
        Log.d("size",(height.toString()+" "+width.toString()))

        when(color)
        {
            "Red"->backgrounder=R.drawable.red
            "Orange"-> backgrounder = R.drawable.orange
            "Yellow"->backgrounder=R.drawable.yellow
            "Green"->backgrounder=R.drawable.green
            "LBlue"->backgrounder=R.drawable.lblue
            "DBlue"->backgrounder=R.drawable.dblue
            "Purple"->backgrounder=R.drawable.purple
            "Black"->backgrounder=R.drawable.black
            "Brown"->backgrounder=R.drawable.brown
            "Turquoise"->backgrounder=R.drawable.turquoise
            "Pink"->backgrounder=R.drawable.pink
            "White"->backgrounder=R.drawable.white
            else->backgrounder=R.drawable.red
        }
        when(color)
        {
            "Red"->ru_color="Красный"
            "Orange"->ru_color="Оранжевый"
            "Yellow"->ru_color="Жёлтый"
            "Green"->ru_color="Зелёный"
            "LBlue"->ru_color="Голубой"
            "DBlue"->ru_color="Синий"
            "Purple"->ru_color="Фиолетовый"
            "Black"->ru_color="Чёрный"
            "Brown"->ru_color="Коричневый"
            "Turquoise"->ru_color="Бирюзовый"
            "Pink"->ru_color="Розовый"
            "White"->ru_color="Белый"
            else->ru_color="Красный"
        }
        when(color)
        {
            "Red"->value+=7
            "Orange"->value+=6
            "Yellow"->value+=5
            "Green"->value+=4
            "LBlue"->value+=3
            "DBlue"->value+=2
            "Purple"->value+=1
            "Black"->backgrounder=R.drawable.black
            "Brown"->backgrounder=R.drawable.brown
            "Turquoise"->backgrounder=R.drawable.turquoise
            "Pink"->backgrounder=R.drawable.pink
            "White"->backgrounder=R.drawable.white
            else->backgrounder=R.drawable.red
        }
        when(color)
        {
            "Red"->backgrounder_n=R.drawable.red_n
            "Orange"-> backgrounder_n = R.drawable.orange_n
            "Yellow"->backgrounder_n=R.drawable.yellow_n
            "Green"->backgrounder_n=R.drawable.green_n
            "LBlue"->backgrounder_n=R.drawable.lblue_n
            "DBlue"->backgrounder_n=R.drawable.dblue_n
            "Purple"->backgrounder_n=R.drawable.purple_n
            else->backgrounder_n=R.drawable.red_n
        }
        var mas=rule.split("_").toTypedArray()
        for(word in mas) {
            if (word == "more")
                ru_rule = ru_rule + "больше "
            else
                if (word == "less")
                    ru_rule = ru_rule + "меньше "
                else
                    if (word == "greatest")
                        ru_rule = ru_rule + "наибольшее из "
                    else
                        if (word == "all")
                            ru_rule = ru_rule + "всех карт "
                        else
                            if (word == "odds")
                                ru_rule = ru_rule + "чётных "
                            else
                                if (word == "inrow")
                                    ru_rule = ru_rule + "карт по порядку "
                                else
                                if (word == "evens")
                                    ru_rule = ru_rule + "нечётных "
                                else {
                                    ru_rule = ru_rule + "карт "
                                    if (word[0].isDigit()) {
                                        ru_rule = ru_rule +"номиналом "+ word[0]+" "
                                        var subword = word.drop(1)
                                        if (subword == "less")
                                            ru_rule = ru_rule + "или меньше "
                                        if (subword == "more")
                                            ru_rule = ru_rule + "или больше "
                                    } else {
                                        var subworder = word.take(4)

                                        if (subworder == "same") {
                                            ru_rule = ru_rule + "одного "
                                            var subworder_1 = word.drop(4)
                                            if (subworder_1 == "values")
                                                ru_rule = ru_rule + "номинала "
                                            if (subworder_1 == "color")
                                                ru_rule = ru_rule + "цвета "
                                        }
                                        if (subworder == "diff") {
                                            ru_rule = ru_rule + "разных "
                                            var subworder_1 = word.drop(4)
                                            if (subworder_1 == "values")
                                                ru_rule = ru_rule + "чисел "
                                            if (subworder_1 == "color")
                                                ru_rule = ru_rule + "цветов "
                                        }
                                    }
                                }
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(color)
        parcel.writeString(ru_color)
        parcel.writeString(rule)
        parcel.writeString(ru_rule)
        parcel.writeInt(number)
        //parcel.writeFloat(width)
        //parcel.writeFloat(height)
        parcel.writeInt(width)
        parcel.writeInt(height)
        parcel.writeInt(textSize)
        parcel.writeInt(index)
        parcel.writeInt(backgrounder)
        parcel.writeInt(value)
        parcel.writeInt(backgrounder_n)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Card_Class>
        {
            override fun createFromParcel(parcel: Parcel): Card_Class {
                return Card_Class(parcel)
            }

            override fun newArray(size: Int): Array<Card_Class?> {
                return arrayOfNulls(size)
            }
        }
    }

}