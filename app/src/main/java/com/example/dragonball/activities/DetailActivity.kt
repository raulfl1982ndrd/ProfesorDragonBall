package com.example.dragonball.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dragonball.R
import com.example.dragonball.adapters.TranformationAdapter
import com.example.dragonball.data.Character
import com.example.dragonball.data.DragonBallProvider
import com.example.dragonball.data.Trasnformation
import com.example.dragonball.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var nameDetailTextView: TextView
    lateinit var character:Character
    lateinit var transformationList: List<Trasnformation>
    private lateinit var recyclerView: RecyclerView
    private lateinit var tranformationAdapter: TranformationAdapter

    //private var chart: RadarChart? = null
/*    val progressValueIntelligence = findViewById<TextView>(R.id.progressValueIntelligence)
    val progressValueStrength = findViewById<TextView>(R.id.progressValueStrength)
    val progressValueSpeed = findViewById<TextView>(R.id.progressValueSpeed)
    val progressValueDurability = findViewById<TextView>(R.id.progressValueDurability)
    val progressValuePower = findViewById<TextView>(R.id.progressValuePower)
    val progressValueCombat = findViewById<TextView>(R.id.progressValueCombat)
*/
    val max = 100
    /**
     * width of the main web lines
     */
    private var mWebLineWidth = 2.5f

    companion object {
        const val EXTRA_CHARACTER_ID:String  = "EXTRA_CHARACTER_ID"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar?.hide()
        val id: Int = intent.getIntExtra(EXTRA_CHARACTER_ID,-1)!!
        recyclerView = binding.content.transformationrecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        getCharacterById(id)

    }

    private fun loadData() {
        binding.nameDetailTextView.text = character.name
        Picasso.get().load(character.image).into(binding.photoImageView)

        // Biography
        binding.content.NameTextView.text = character.name
        binding.content.raceTextView.text = character.race
        binding.content.genderTextView.text = character.gender
        binding.content.affiliationTextView.text = character.affiliation
        binding.content.kiTextView.text = character.ki
        binding.content.MaxkiTextView.text = character.maxki
        binding.content.descripctionTextView.text = character.description
        Picasso.get().load(character.originPlanet.image).into(binding.content.planetImageView)
        binding.content.planetNameTextView.text = character.originPlanet.name
        binding.content.planetDescriptionTextView.text = character.originPlanet.description
        binding.content.isDestroyedTextView.text = if (character.originPlanet.isDestroyed) "Si" else "No"
        //Toast.makeText(this@DetailActivity,character.maxki, Toast.LENGTH_LONG).show()

        transformationList = character.transformations

        if (transformationList.isNullOrEmpty())
            binding.content.transformationLabel.visibility = View.GONE
        else
            binding.content.transformationLabel.visibility = View.VISIBLE

        tranformationAdapter = TranformationAdapter(transformationList)
        recyclerView.adapter = tranformationAdapter
        //getAllCharacters()
    }

    fun getCharacterById(id:Int){

        binding.content.progress.visibility = View.VISIBLE
        // Llamada en segundo plano
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = DragonBallProvider().getCharacterApiService()
                val result = apiService.getCharacterById(id)
                character = result
                runOnUiThread {
                    /*binding.progress.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.emptyPlaceholder.visibility = View.GONE*/
                    /*if(result.response == "success")
                        superHero = result.
                    else superHero = emptyList()*/
                    Toast.makeText(this@DetailActivity,result.name, Toast.LENGTH_LONG).show()
                    loadData()
                    binding.content.progress.visibility = View.GONE
                }
                Log.i("HTTP", "${result.id}-->${result.name}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}