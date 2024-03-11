import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Senzori {
    var numeSenzor: String = ""
    var tensiune: String = ""

    constructor() // Constructor gol necesar pentru Firebase

    constructor(numeSenzor: String, tensiune: String) {
        this.numeSenzor = numeSenzor
        this.tensiune = tensiune
    }

    fun obtineTensiune(): String {
        return tensiune
    }
    fun obtineNumeSenzor():String{
        return numeSenzor
    }
}


//commit1


