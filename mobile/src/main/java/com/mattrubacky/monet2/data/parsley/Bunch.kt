package com.mattrubacky.monet2.data.parsley

import kotlin.reflect.full.createType

abstract class Bunch<Source,Store>{
    abstract var sprigs: ArrayList<Sprig<Source,Store>>
}