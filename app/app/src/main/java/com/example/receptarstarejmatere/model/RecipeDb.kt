package com.example.receptarstarejmatere.model

class RecipeDb: ArrayList<Recipe>() {

    val names: List<String>
    get() = map {it.name}.toList()

    init {
        add(Recipe("Palacinky", true, "40", "20", "You know what to do ;) ."))
        add(Recipe("Chleba s mastou a cibulou", false, "5", "0", "Oh yeah."))
        add(Recipe("Hummus", true, "20", "0", "Mix it all."))
        add(Recipe("Guacamole", true, "30", "0", "....."))
        add(Recipe("Bryndzove halusky", false, "120", "20", "Nom nom."))
        add(Recipe("Vianocka s lekvarom", true, "3", "0", "hihi"))
        add(Recipe("Palacinky again", true, "40", "20", "No new ideas."))
        add(Recipe("Gin tonic", true, "5", "0", "Party Party"))
        add(Recipe("Hriatô", false, "10", "5", "Baby its cold outside."))
        add(Recipe("Perky", true, "40", "20", "Ako od babky."))
        add(Recipe("Chleba s maslem", false, "2", "0", "Potkal rohlik"))
        add(Recipe("Chleba s maslem se salamem", true, "3", "0", "Potkal vanocku."))
    }
}