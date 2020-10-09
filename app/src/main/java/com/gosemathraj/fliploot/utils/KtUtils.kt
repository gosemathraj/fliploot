package com.gosemathraj.fliploot.utils

/*
*   Safe Try-Catch block
*/
fun safeExecute(body:()->Unit){
    try{
        body()
    }catch (ex : Exception){
        ex.printStackTrace()
    }
}