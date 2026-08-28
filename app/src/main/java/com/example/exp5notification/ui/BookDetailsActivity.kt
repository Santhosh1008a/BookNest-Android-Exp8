package com.example.exp5notification.ui

import android.animation.ValueAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.exp5notification.R
import com.example.exp5notification.data.Book
import com.example.exp5notification.notifications.NotificationHelper

/**
 * BookDetailsActivity — Experiment 6: Basic Android Views
 *
 * Demonstrates the following standard Android Views:
 *  1. TextView        — title, author, genre, description, rating, progress, user info
 *  2. ImageView       — book cover (loaded via Coil)
 *  3. EditText        — personal note input
 *  4. CheckBox        — Add to Favorites
 *  5. RadioButton /
 *     RadioGroup      — Reading Status (Want to Read / Currently Reading / Completed)
 *  6. Switch          — Reading Reminder toggle
 *  7. Spinner         — Genre selector (10 genres)
 *  8. RatingBar       — 1-5 star user rating
 *  9. ProgressBar     — Reading progress (animated, determinate horizontal)
 * 10. Button          — "Read Book" (Toast) and "Save Changes" (collects all values)
 */
class BookDetailsActivity : AppCompatActivity() {

    // ─── Views ───────────────────────────────────────────────────────────────
    private lateinit var ivCover: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvGenre: TextView
    private lateinit var tvRating: TextView
    private lateinit var tvYear: TextView
    private lateinit var tvDesc: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvUserUsn: TextView
    private lateinit var tvProgressValue: TextView
    private lateinit var tvRatingResult: TextView
    private lateinit var pbReading: ProgressBar
    private lateinit var ratingBar: RatingBar
    private lateinit var rgStatus: RadioGroup
    private lateinit var spinnerGenre: Spinner
    private lateinit var cbFavorite: CheckBox
    private lateinit var switchReminder: Switch
    private lateinit var etNote: EditText
    private lateinit var btnReadBook: Button
    private lateinit var btnSaveChanges: Button
    private lateinit var btnReadFree: Button
    private lateinit var btnAddLibrary: Button

    // ─── State ───────────────────────────────────────────────────────────────
    private var currentBook: Book? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        bindViews()

        // Retrieve user info passed from LoginActivity → MainActivity → here
        val userName = intent.getStringExtra("USER_NAME") ?: "Sandy"
        val userUsn  = intent.getStringExtra("USER_USN")  ?: ""
        tvUserName.text = userName
        tvUserUsn.text  = if (userUsn.isNotEmpty()) "USN: $userUsn" else "BookNest Reader"

        @Suppress("DEPRECATION")
        val book = intent.getParcelableExtra<Book>("BOOK_DATA")

        if (book == null) {
            finish()
            return
        }
        currentBook = book
        populateBookInfo(book)
        setupGenreSpinner(book.genre)
        setupListeners(book)
        playEntryAnimations(book.progress)
    }

    // ─── Bind all Views ──────────────────────────────────────────────────────
    private fun bindViews() {
        ivCover         = findViewById(R.id.iv_detail_cover)
        tvTitle         = findViewById(R.id.tv_detail_title)
        tvAuthor        = findViewById(R.id.tv_detail_author)
        tvGenre         = findViewById(R.id.tv_detail_genre)
        tvRating        = findViewById(R.id.tv_detail_rating)
        tvYear          = findViewById(R.id.tv_detail_year)
        tvDesc          = findViewById(R.id.tv_detail_desc)
        tvUserName      = findViewById(R.id.tv_user_name)
        tvUserUsn       = findViewById(R.id.tv_user_usn)
        tvProgressValue = findViewById(R.id.tv_progress_value)
        tvRatingResult  = findViewById(R.id.tv_rating_result)
        pbReading       = findViewById(R.id.pb_reading)
        ratingBar       = findViewById(R.id.ratingbar_book)
        rgStatus        = findViewById(R.id.rg_reading_status)
        spinnerGenre    = findViewById(R.id.spinner_genre)
        cbFavorite      = findViewById(R.id.cb_favorite)
        switchReminder  = findViewById(R.id.switch_reminder)
        etNote          = findViewById(R.id.et_personal_note)
        btnReadBook     = findViewById(R.id.btn_read_book)
        btnSaveChanges  = findViewById(R.id.btn_save_changes)
        btnReadFree     = findViewById(R.id.btn_read_free)
        btnAddLibrary   = findViewById(R.id.btn_add_library)
    }

    // ─── Populate static book info ────────────────────────────────────────────
    private fun populateBookInfo(book: Book) {
        // VIEW 2: ImageView — load cover with fade
        ivCover.load(book.coverImage) {
            crossfade(true)
            crossfade(400)
        }

        // VIEW 1: TextViews for book metadata
        tvTitle.text  = book.title
        tvAuthor.text = "By ${book.author}"
        tvGenre.text  = book.genre
        tvRating.text = "⭐ ${book.rating}"
        tvYear.text   = book.publicationYear.toString()
        tvDesc.text   = book.description

        // Pre-fill favorite state from book model
        cbFavorite.isChecked = book.isFavorite
    }

    // ─── Setup Spinner (VIEW 7) ────────────────────────────────────────────
    private fun setupGenreSpinner(bookGenre: String) {
        val genres = listOf(
            "Fiction", "Fantasy", "Mystery", "Science Fiction",
            "Romance", "Self-Help", "Technology", "History", "Classics", "Academic"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, genres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerGenre.adapter = adapter

        // Pre-select the book's genre
        val matchIndex = genres.indexOfFirst { it.equals(bookGenre, ignoreCase = true) }
        if (matchIndex >= 0) spinnerGenre.setSelection(matchIndex)

        // VIEW 7 Listener: setOnItemSelectedListener
        spinnerGenre.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, pos: Int, id: Long) {
                val selected = genres[pos]
                Toast.makeText(this@BookDetailsActivity, "Genre: $selected", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) { /* no-op */ }
        }
    }

    // ─── Setup all event listeners ────────────────────────────────────────────
    private fun setupListeners(book: Book) {

        // Back button
        findViewById<android.view.View>(R.id.btn_detail_back).setOnClickListener { finish() }

        val openSettings = {
            val name = intent.getStringExtra("USER_NAME") ?: "Sandy"
            val usn  = intent.getStringExtra("USER_USN")  ?: ""
            val intent = Intent(this, UserSettingsActivity::class.java).apply {
                putExtra("USER_NAME", name)
                putExtra("USER_USN",  usn)
            }
            startActivity(intent)
        }

        // Profile / Settings button → UserSettingsActivity
        findViewById<android.view.View>(R.id.btn_detail_logout)?.setOnClickListener { openSettings() }

        // Favorite ImageButton (heart icon)
        findViewById<android.view.View>(R.id.btn_detail_fav).setOnClickListener { view ->
            view.animate().scaleX(1.3f).scaleY(1.3f).setDuration(120).withEndAction {
                view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(120).start()
            }.start()
            cbFavorite.isChecked = !cbFavorite.isChecked
        }

        // VIEW 8 Listener: RatingBar — setOnRatingBarChangeListener
        ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                val label = when (rating.toInt()) {
                    1 -> "Poor"
                    2 -> "Fair"
                    3 -> "Good"
                    4 -> "Very Good"
                    5 -> "Excellent!"
                    else -> "Tap stars to rate"
                }
                tvRatingResult.text = "You rated: ${rating.toInt()} / 5 stars  ($label)"
            }
        }

        // VIEW 5 Listener: RadioGroup — setOnCheckedChangeListener
        rgStatus.setOnCheckedChangeListener { _, checkedId ->
            val status = when (checkedId) {
                R.id.rb_want_to_read       -> "Want to Read"
                R.id.rb_currently_reading  -> "Currently Reading"
                R.id.rb_completed          -> "Completed"
                else                       -> "Unknown"
            }
            Toast.makeText(this, "Status: $status", Toast.LENGTH_SHORT).show()
        }

        // VIEW 4 Listener: CheckBox — setOnCheckedChangeListener
        cbFavorite.setOnCheckedChangeListener { _, isChecked ->
            // Animate the checkbox's container for subtle feedback
            cbFavorite.animate().scaleX(1.15f).scaleY(1.15f).setDuration(100).withEndAction {
                cbFavorite.animate().scaleX(1.0f).scaleY(1.0f).setDuration(100).start()
            }.start()
            val msg = if (isChecked) "❤️ Added to Favorites" else "Removed from Favorites"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        // VIEW 6 Listener: Switch — setOnCheckedChangeListener
        switchReminder.setOnCheckedChangeListener { _, isOn ->
            val msg = if (isOn) "🔔 Reading Reminder ON" else "Reading Reminder OFF"
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        // VIEW 10 (a): Button — Read Book → Toast
        btnReadBook.setOnClickListener { view ->
            view.animate().scaleX(0.94f).scaleY(0.94f).setDuration(80).withEndAction {
                view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(80).start()
            }.start()
            Toast.makeText(this, "📖 Opening book: ${book.title}...", Toast.LENGTH_SHORT).show()
        }

        // VIEW 10 (b): Button — Save Changes → collect all View values
        btnSaveChanges.setOnClickListener { view ->
            view.animate().scaleX(0.94f).scaleY(0.94f).setDuration(80).withEndAction {
                view.animate().scaleX(1.0f).scaleY(1.0f).setDuration(80).start()
            }.start()
            collectAndShowSummary()
        }

        // Exp 5 preserved: Read Free online
        btnReadFree.setOnClickListener {
            val url = book.freeReadingUrl
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }

        // Exp 5 preserved: Add to Library → notification
        btnAddLibrary.setOnClickListener {
            val notificationHelper = NotificationHelper(this)
            notificationHelper.showReadingReminderNotification(book.title, book.progress)
            Toast.makeText(this, "${book.title} added to your library", Toast.LENGTH_SHORT).show()
        }
    }

    // ─── Collect values from all Views and display summary (VIEW 10b) ─────────
    private fun collectAndShowSummary() {
        // VIEW 3: EditText — personal note
        val note = etNote.text.toString().trim()

        // VIEW 4: CheckBox
        val isFav = cbFavorite.isChecked

        // VIEW 5: RadioGroup → find checked RadioButton text
        val checkedRadioId = rgStatus.checkedRadioButtonId
        val readingStatus = if (checkedRadioId != -1) {
            findViewById<RadioButton>(checkedRadioId).text.toString()
        } else "Not set"

        // VIEW 6: Switch
        val reminderOn = switchReminder.isChecked

        // VIEW 7: Spinner
        val selectedGenre = spinnerGenre.selectedItem?.toString() ?: "None"

        // VIEW 8: RatingBar
        val userRating = ratingBar.rating.toInt()

        // VIEW 9: ProgressBar current value
        val progress = pbReading.progress

        val summary = buildString {
            append("✅ Changes Saved!\n\n")
            append("📚 Status: $readingStatus\n")
            append("🎭 Genre: $selectedGenre\n")
            append("⭐ Rating: $userRating / 5\n")
            append("📖 Progress: $progress%\n")
            append("❤️ Favorite: ${if (isFav) "Yes" else "No"}\n")
            append("🔔 Reminder: ${if (reminderOn) "ON" else "OFF"}\n")
            if (note.isNotEmpty()) append("📝 Note: $note")
        }

        Toast.makeText(this, summary, Toast.LENGTH_LONG).show()
    }

    // ─── Entry animations ──────────────────────────────────────────────────────
    private fun playEntryAnimations(targetProgress: Int) {
        // Cover fade in
        ivCover.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(600)
            .setInterpolator(DecelerateInterpolator())
            .start()

        // Details card slide-up + fade in
        val detailsCard = findViewById<android.view.View>(R.id.card_details)
        detailsCard.translationY = 80f
        detailsCard.animate()
            .alpha(1f)
            .translationY(0f)
            .setDuration(500)
            .setStartDelay(200)
            .setInterpolator(DecelerateInterpolator())
            .start()

        // VIEW 9: ProgressBar animate from 0 → targetProgress
        val animator = ValueAnimator.ofInt(0, targetProgress)
        animator.duration = 1200
        animator.startDelay = 400
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener { anim ->
            val value = anim.animatedValue as Int
            pbReading.progress = value
            tvProgressValue.text = "$value%"
        }
        animator.start()
    }
}
