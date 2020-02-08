package com.mattrubacky.monet2.data.parsley

abstract class Bunch<Source,Store>{
    abstract var sprigs: ArrayList<Sprig<Source,Store>>
}