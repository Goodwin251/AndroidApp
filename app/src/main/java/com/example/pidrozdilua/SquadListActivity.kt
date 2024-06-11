package com.example.pidrozdilua

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pidrozdilua.adapters.ArticleAdapter
import com.example.pidrozdilua.adapters.CategoryAdapter
import com.example.pidrozdilua.models.Category
import com.example.pidrozdilua.models.Link
import com.example.pidrozdilua.utils.JsonUtils
import com.google.gson.Gson
import java.io.InputStreamReader

class SquadListActivity : AppCompatActivity() {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_squad_list)

        val backButton: ImageView = findViewById(R.id.back_button)
        val homeButton: ImageView = findViewById(R.id.home_button)

        backButton.setOnClickListener {
            finish()
        }

        homeButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        val categoryList: RecyclerView = findViewById(R.id.category_list)
        categoryList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val articleList: RecyclerView = findViewById(R.id.article_list)
        articleList.layoutManager = LinearLayoutManager(this)

        val categories = loadCategoriesFromJson()
        categoryAdapter = CategoryAdapter(categories) { category ->
            updateArticleList(category.links)
        }
        categoryList.adapter = categoryAdapter

        // Initialize with the first category's links
        if (categories.isNotEmpty()) {
            updateArticleList(categories[0].links)
        }
    }

    private fun loadCategoriesFromJson(): List<Category> {
        val inputStream = resources.openRawResource(R.raw.categories)
        val reader = InputStreamReader(inputStream)
        val gson = Gson()
        val data = gson.fromJson(reader, JsonUtils.Categories::class.java)
        reader.close()
        return data.categories
    }

    private fun updateArticleList(links: List<Link>) {
        articleAdapter = ArticleAdapter(links)
        findViewById<RecyclerView>(R.id.article_list).adapter = articleAdapter
    }
}
