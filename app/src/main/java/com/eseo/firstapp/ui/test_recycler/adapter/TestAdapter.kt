package com.eseo.firstapp.ui.test_recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eseo.firstapp.R

class TestAdapter(private val stringList: Array<String>, private val onClick: ((selectedString: String) -> Unit)? = null) : RecyclerView.Adapter<TestAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun showItem(texte: String, onClick: ((selectedString: String) -> Unit)? = null) {
            itemView.findViewById<TextView>(R.id.title).text = texte

            if(onClick != null) {
                itemView.setOnClickListener {
                    onClick(texte)
                }
            }
        }
    }

    // Retourne une « vue » / « layout » pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    // Connect la vue ET la données
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.showItem(stringList[position], onClick)
    }

    override fun getItemCount(): Int {
        return stringList.size
    }

}
