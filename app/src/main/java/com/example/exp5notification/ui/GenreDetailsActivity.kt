package com.example.exp5notification.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.exp5notification.MainActivity
import com.example.exp5notification.R
import com.example.exp5notification.data.Book
import com.example.exp5notification.data.BookRepository

class GenreDetailsActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "GenreDetailsActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate called")
        setContentView(R.layout.activity_genre_details)

        val genreName = intent.getStringExtra("GENRE_NAME") ?: "Fiction"
        val userName  = intent.getStringExtra("USER_NAME")  ?: "Sandy"
        val userUsn   = intent.getStringExtra("USER_USN")   ?: ""

        findViewById<TextView>(R.id.tv_genre_title).text = genreName
        findViewById<View>(R.id.btn_back).setOnClickListener { finish() }

        val openSettings = {
            val intent = Intent(this, UserSettingsActivity::class.java).apply {
                putExtra("USER_NAME", userName)
                putExtra("USER_USN",  userUsn)
            }
            startActivity(intent)
        }

        findViewById<View>(R.id.btn_genre_logout)?.setOnClickListener { openSettings() }

        applyGenreTheme(genreName)
        setupBookList(genreName, userName, userUsn)
    }

    private fun applyGenreTheme(genre: String) {
        findViewById<MagicBackgroundView>(R.id.magic_bg)?.setGenreTheme(genre)
        val gradient = findViewById<View>(R.id.genre_gradient)
        
        when {
            genre.contains("Fiction", ignoreCase = true) && !genre.contains("Science", ignoreCase = true) ->
                gradient.setBackgroundResource(R.drawable.genre_gradient_fiction)
            genre.contains("Fantasy", ignoreCase = true) ->
                gradient.setBackgroundResource(R.drawable.genre_gradient_fantasy)
            genre.contains("Mystery", ignoreCase = true) ->
                gradient.setBackgroundResource(R.drawable.genre_gradient_mystery)
            genre.contains("Sci-Fi", ignoreCase = true) || genre.contains("Science", ignoreCase = true) ->
                gradient.setBackgroundResource(R.drawable.genre_gradient_scifi)
            genre.contains("Romance", ignoreCase = true) ->
                gradient.setBackgroundResource(R.drawable.genre_gradient_romance)
            genre.contains("Self-Help", ignoreCase = true) || genre.contains("Self", ignoreCase = true) ->
                gradient.setBackgroundResource(R.drawable.genre_gradient_selfhelp)
            genre.contains("Technology", ignoreCase = true) || genre.contains("Tech", ignoreCase = true) ->
                gradient.setBackgroundResource(R.drawable.genre_gradient_tech)
            genre.contains("History", ignoreCase = true) ->
                gradient.setBackgroundResource(R.drawable.genre_gradient_history)
            genre.contains("Classics", ignoreCase = true) ->
                gradient.setBackgroundResource(R.drawable.genre_gradient_classics)
            genre.contains("Academic", ignoreCase = true) ->
                gradient.setBackgroundResource(R.drawable.genre_gradient_academic)
            else ->
                gradient.setBackgroundResource(R.drawable.genre_gradient_default)
        }
    }

    private fun setupBookList(genre: String, userName: String, userUsn: String) {
        val rvBooks = findViewById<RecyclerView>(R.id.rv_books)
        rvBooks.layoutManager = LinearLayoutManager(this)

        val books = BookRepository.getBooksByGenre(genre)
        rvBooks.adapter = BookAdapter(books) { book ->
            val intent = Intent(this, BookDetailsActivity::class.java).apply {
                putExtra("BOOK_DATA", book)
                putExtra("USER_NAME", userName)
                putExtra("USER_USN",  userUsn)
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val userName = intent.getStringExtra("USER_NAME") ?: "Sandy"
        val userUsn  = intent.getStringExtra("USER_USN")  ?: ""
        return when (item.itemId) {
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra("USER_NAME", userName)
                    putExtra("USER_USN", userUsn)
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
                startActivity(intent)
                true
            }
            R.id.action_browse -> {
                finish()
                true
            }
            R.id.action_favorites -> {
                val favs = BookRepository.getAllBooks().filter { it.isFavorite }
                val favTitles = if (favs.isNotEmpty()) favs.joinToString { it.title } else "No favorites added yet"
                Toast.makeText(this, "Favorites: $favTitles", Toast.LENGTH_LONG).show()
                true
            }
            R.id.action_settings -> {
                val intent = Intent(this, UserSettingsActivity::class.java).apply {
                    putExtra("USER_NAME", userName)
                    putExtra("USER_USN", userUsn)
                }
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class BookAdapter(
        private val books: List<Book>,
        private val onClick: (Book) -> Unit
    ) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val ivCover: ImageView = view.findViewById(R.id.iv_book_cover)
            val tvTitle: TextView = view.findViewById(R.id.tv_book_title)
            val tvAuthor: TextView = view.findViewById(R.id.tv_book_author)
            val tvRating: TextView = view.findViewById(R.id.tv_book_rating)
            val ivFav: ImageView = view.findViewById(R.id.iv_favorite)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_book, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val book = books[position]
            holder.ivCover.load(book.coverImage)
            holder.tvTitle.text = book.title
            holder.tvAuthor.text = book.author
            holder.tvRating.text = book.rating.toString()
            
            holder.itemView.setOnClickListener { onClick(book) }
        }

        override fun getItemCount() = books.size
    }

    override fun onStart() { super.onStart(); Log.d(TAG, "onStart called") }
    override fun onResume() { super.onResume(); Log.d(TAG, "onResume called") }
    override fun onPause() { super.onPause(); Log.d(TAG, "onPause called") }
    override fun onStop() { super.onStop(); Log.d(TAG, "onStop called") }
    override fun onDestroy() { super.onDestroy(); Log.d(TAG, "onDestroy called") }
}
