package com.eseo.firstapp.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.eseo.firstapp.R
import com.eseo.firstapp.ui.parametres_recycler.ParameterActivity


class MainActivity : AppCompatActivity() {

    companion object{
        fun getStartIntent(context: Context): Intent{
            return Intent(context, MainActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener {
            MaterialDialog(this).show{
                input()
                title(R.string.app_name)
                message(R.string.your_message)
                positiveButton(R.string.yes){
                    Toast.makeText(this@MainActivity, getString(R.string.your_entry)+getInputField().text, Toast.LENGTH_LONG).show()
                }
                negativeButton(R.string.no)
            }
        }

        findViewById<ImageView>(R.id.param).setOnClickListener {
                    val targetIntent = Intent().apply {
                        action = android.provider.Settings.ACTION_SETTINGS
                    }
                    startActivity(targetIntent);
                }

        findViewById<TextView>(R.id.map_eseo).setOnClickListener {
            MaterialDialog(this).show{
                title(R.string.app_name)
                message(R.string.localisation_message)
                positiveButton(R.string.yes){
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:47.493620760672485, -0.5512148512520484")));
                }
                negativeButton(R.string.no)
            }
        }

        findViewById<TextView>(R.id.link_eseo).setOnClickListener {
            MaterialDialog(this).show{
                title(R.string.app_name)
                message(R.string.link_message)
                positiveButton(R.string.yes){
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://eseo.fr/")));
                }
                negativeButton(R.string.no)
            }
        }

        findViewById<Button>(R.id.go_to_locate).setOnClickListener {
            startActivity(MapActivity.getStartIntent(this))
        }

        findViewById<Button>(R.id.reclyclerview).setOnClickListener {
            startActivity(ParameterActivity.getStartIntent(this))
        }
    }

}