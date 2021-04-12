package com.eseo.firstapp.ui.parametres_recycler

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eseo.firstapp.ui.MapActivity
import com.eseo.firstapp.R
import com.eseo.firstapp.data.model.SettingsItem
import com.eseo.firstapp.ui.parametres_recycler.adapter.ParameterAdapter

class ParameterActivity : AppCompatActivity() {

    companion object{
        fun getStartIntent(context: Context): Intent {
            return Intent(context, ParameterActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parameter)
        supportActionBar?.apply {
            setTitle(R.string.app_name)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        val rv = findViewById<RecyclerView>(R.id.liste_parametres)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = ParameterAdapter(arrayOf(
            SettingsItem("Paramètre Wifi", R.drawable.wifi) {
                val targetIntent = Intent().apply {
                    action = Settings.ACTION_WIFI_SETTINGS
                }
                startActivity(targetIntent);
        },
            SettingsItem("Carte", R.drawable.map) {
                startActivity(MapActivity.getStartIntent(this))
            },
            SettingsItem("Paramètres", R.drawable.settings) {
                val targetIntent = Intent().apply {
                    action = Settings.ACTION_SETTINGS
                }
                startActivity(targetIntent);
            },
            SettingsItem("Site de l'ESEO", R.drawable.logo_eseo) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://eseo.fr/")));
            }
        ))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}