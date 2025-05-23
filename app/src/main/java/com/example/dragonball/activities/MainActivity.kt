package com.example.dragonball.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dragonball.R
import com.example.dragonball.adapters.CharacterAdapter
import com.example.dragonball.data.Character
import com.example.dragonball.data.DragonBallApiService
import com.example.dragonball.databinding.ActivityMainBinding
import com.example.dragonball.databinding.AlertDialogBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: CharacterAdapter

    lateinit var characterList: List<Character>
    lateinit var filteredList: List<Character>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        characterList = emptyList()
        filteredList = emptyList()

        adapter = CharacterAdapter(characterList) { position ->
            navigateToDetail(characterList[position])
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        getAllCharacters()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_activity_main, menu)

        val searchViewItem = menu.findItem(R.id.menu_search)
        val searchView = searchViewItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query != null) {
                    searchByName(query)
                }
                return true
            }
        })

        return true
    }

    private fun getAllCharacters() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val apiService = getRetrofit().create(DragonBallApiService::class.java)
                val result = apiService.findAllCharacters()

                runOnUiThread {
                    characterList = result.items
                    adapter.updateData(characterList)
                }
                Log.i("HTTP", "${result.items}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun searchByName(query: String) {
        filteredList = characterList.filter { it.name.contains(query, true) }
        adapter.updateData(filteredList)
    }

    private fun navigateToDetail(character: Character) {
        Toast.makeText(this,character.name,Toast.LENGTH_LONG).show()
        val intent: Intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_CHARACTER_ID, character.id)
        startActivity(intent)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://dragonball-api.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun onBackPressed() {
        val bind  = AlertDialogBinding.inflate(layoutInflater)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Salir de la aplicación")
            .setMessage("¿Estas seguro de querer salir de la aplicación?")
            .setView(bind.root)
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton("Salir") { _, _ ->
                super.onBackPressed()
            }
            .setNeutralButton("No lo se") { _, _ ->
                val text = bind.editText.text.toString()
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            }
            .setCancelable(false) // No permite cerrar el dialogo hasta que no se llame a dialog.dismiss()
            .create()

        alertDialog.show()

    }
}