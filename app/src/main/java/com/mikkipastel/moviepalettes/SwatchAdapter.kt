package com.mikkipastel.moviepalettes

import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_swatch.*


class SwatchAdapter(private val swatchList: MutableList<Palette.Swatch>) : RecyclerView.Adapter<SwatchViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        SwatchViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_swatch, parent, false))

    override fun getItemCount(): Int = swatchList.count()

    override fun onBindViewHolder(holder: SwatchViewHolder, position: Int) {
        holder.bind(swatchList[position])
    }

}

class SwatchViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
    fun bind(swatch: Palette.Swatch) {
        rootview.setBackgroundColor(swatch.rgb)
    }
}