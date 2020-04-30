package com.example.receptarstarejmatere.application

import android.app.Application
import com.example.receptarstarejmatere.database.DataGenerator
import com.example.receptarstarejmatere.database.MyDb
import com.example.receptarstarejmatere.database.repository.RecipeRepository
import java.util.*

class App: Application() {
    // init db
    // init repo with db
    companion object {
        lateinit var instance: App
            private set
        lateinit var database: MyDb
            private set
        lateinit var recipe_repository: RecipeRepository
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        val db = MyDb.invoke(this)
        database = db
        recipe_repository = RecipeRepository.getInstance(database)

        insertTestData(recipe_repository)
    }

    private fun insertTestData(repo : RecipeRepository){
        val recipes = DataGenerator.generateRecipes()
        Thread {
            repo.insertAll(Collections.unmodifiableList(recipes))
        }.start()
    }
}

// TODO:
//nechame livedata (same sa postaraju o async),
//coroutine z repa prec (bude livedata),
//repa spravit pre vsetkych (kvoli architekture)
//spravit app: Application, kde sa to bude vsetko initializovat
