package com.example.pidrozdilua

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pidrozdilua.databinding.ActivityArticleBinding
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.URLDecoder

class ArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val articleTitle = intent.getStringExtra("article_title") ?: return

        binding.articleTitle.text = articleTitle
        binding.articleTitle.textSize = 16f

        getFullArticleContent(articleTitle) { articleContent ->
            runOnUiThread {
                if (articleContent != null) {
                    binding.articleContent.text = articleContent.text
                    binding.articleContent.textSize = 16f
                } else {
                    Toast.makeText(this, "Failed to load article", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Обробник натискань для back_button_article
        binding.backButtonArticle.setOnClickListener {
            finish() // Повертаємось до попередньої активності
        }

        // Обробник натискань для home_button_article
        binding.homeButtonArticle.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java)) // Переходимо на MainActivity
        }
    }

    private fun getFullArticleContent(title: String, callback: (ArticleContent?) -> Unit) {
        val url = "https://uk.wikipedia.org/w/api.php?action=query&prop=extracts&explaintext&format=json&titles=$title"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                callback(null)
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val jsonResponse = responseBody.string()
                    if (jsonResponse.isNotEmpty()) {
                        try {
                            val jsonObject = JSONObject(jsonResponse)
                            val pages = jsonObject.getJSONObject("query").getJSONObject("pages")
                            val page = pages.keys().next()
                            val pageObject = pages.getJSONObject(page)
                            val extract = pageObject.getString("extract")
                            val decodedExtract = URLDecoder.decode(extract, "UTF-8")
                            callback(ArticleContent(decodedExtract))
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            callback(null)
                        }
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }

        })
    }

    data class ArticleContent(val text: String)
}
