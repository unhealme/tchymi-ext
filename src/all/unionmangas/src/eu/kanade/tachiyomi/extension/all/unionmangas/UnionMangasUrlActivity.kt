package eu.kanade.tachiyomi.extension.all.unionmangas

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlin.system.exitProcess

class UnionMangasUrlActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val host = intent?.data?.host
        val pathSegments = intent?.data?.pathSegments

        if (host != null && pathSegments != null) {
            val intent = Intent().apply {
                action = "eu.kanade.tachiyomi.SEARCH"
                putExtra("query", slug(pathSegments))
                putExtra("filter", packageName)
            }

            try {
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                Log.e("UnionMangasUrlActivity", e.toString())
            }
        }

        finish()
        exitProcess(0)
    }

    private fun slug(pathSegments: List<String>) = "${UnionMangas.slugPrefix}${pathSegments.last()}"
}
