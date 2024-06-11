import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

fun getWikipediaImageUrl(title: String, callback: (String?) -> Unit) {
    val apiUrl = "https://uk.wikipedia.org/w/api.php?action=query&titles=$title&prop=pageimages&format=json&pithumbsize=500"

    val request = Request.Builder().url(apiUrl).build()
    val client = OkHttpClient()

    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            e.printStackTrace()
            callback(null)
        }

        override fun onResponse(call: Call, response: Response) {
            if (!response.isSuccessful) {
                callback(null)
                return
            }

            val responseBody = response.body()?.string()
            if (responseBody != null) {
                try {
                    val jsonObject = JSONObject(responseBody)
                    val pages = jsonObject.getJSONObject("query").getJSONObject("pages")
                    val page = pages.keys().next()
                    val pageObject = pages.getJSONObject(page)
                    val imageUrl = pageObject.optJSONObject("thumbnail")?.getString("source")
                    callback(imageUrl)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    callback(null)
                }
            } else {
                callback(null)
            }
        }
    })
}
