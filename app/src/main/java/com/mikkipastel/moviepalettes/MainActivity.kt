package com.mikkipastel.moviepalettes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.graphics.Palette
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val url = "https://pbs.twimg.com/media/D1HoDKGUwAUYAXy.jpg"
        imageMovie.setImageURI(url)

        GlobalScope.async {
            createPaletteAsync(getBitmapFromUrl(url))
        }

        recyclerViewSwatch.layoutManager = GridLayoutManager(
            this,
            16,
            GridLayoutManager.VERTICAL,
            false
        )
    }

    private fun getBitmapFromUrl(src: String): Bitmap {
        val url = URL(src)
        val connection = url.openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()

        val input = connection.inputStream
        return BitmapFactory.decodeStream(input)
    }

    private fun createPaletteAsync(bitmap: Bitmap) {
        Palette.from(bitmap).generate { palette ->
            setTextViewWithPalette(textviewDarkMutedSwatch, palette?.darkMutedSwatch)
            setTextViewWithPalette(textviewDarkVibrantSwatch, palette?.darkVibrantSwatch)
            setTextViewWithPalette(textviewDominantSwatch, palette?.dominantSwatch)
            setTextViewWithPalette(textviewLightMutedSwatch, palette?.lightMutedSwatch)
            setTextViewWithPalette(textviewLightVibrantSwatch, palette?.lightVibrantSwatch)
            setTextViewWithPalette(textviewMutedSwatch, palette?.mutedSwatch)
            setTextViewWithPalette(textviewVibrantSwatch, palette?.vibrantSwatch)

            recyclerViewSwatch.adapter = SwatchAdapter(palette?.swatches!!)
        }
    }

    private fun setTextViewWithPalette(textview: TextView, swatch: Palette.Swatch?) {
        textview.apply {
            if (swatch != null) {
                setTextColor(swatch.bodyTextColor)
                setBackgroundColor(swatch.rgb)
            } else {
                visibility = View.GONE
            }
        }
    }
}
