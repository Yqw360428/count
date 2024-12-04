package com.someca.count.bean

data class InitBean(
    val cause : Int,
    val recognize : String,
    val cheap : Cheap
){
    data class Cheap(
        val alteration : String? = null,
        val revise : Boolean? = null
    ){
        override fun toString(): String {
            return "Cheap(alteration=$alteration, revise=$revise)"
        }
    }

    override fun toString(): String {
        return "InitBean(cause=$cause, recognize='$recognize', cheap=$cheap)"
    }


}