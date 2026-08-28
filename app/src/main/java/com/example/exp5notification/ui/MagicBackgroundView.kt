package com.example.exp5notification.ui

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

/**
 * MagicBackgroundView — Genre-Aware Animated Canvas View
 *
 * Dynamically renders genre-specific floating animated particles:
 *  - ❤️ Romance: Floating pulsing Hearts
 *  - ⚔️ History / Classics: Floating Swords, Shields & Royal Crowns
 *  - 🪄 Fantasy: Floating 4-point Magic Stars & Sparkles
 *  - 🚀 Sci-Fi / Tech: Floating Cyber Rings, Hexagons & Code Dots
 *  - 🔍 Mystery: Floating Detective Shadow Orbs & Question Sparkles
 *  - ☀️ Self-Help / Academic: Floating Sunbursts & Wisdom Sparkles
 *  - 📖 Fiction / Default: Floating Soft Light Particles
 */
class MagicBackgroundView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    enum class GenreShape {
        HEART,       // Romance
        WARRIOR,     // History / Classics (Swords & Shields & Crowns)
        MAGIC_STAR,  // Fantasy
        CYBER_RING,  // Sci-Fi / Tech
        MYSTERY_ORB, // Mystery
        SUNBURST,    // Self-Help / Academic
        DEFAULT_DOT  // Fiction / General
    }

    private data class Particle(
        var x: Float,
        var y: Float,
        val size: Float,
        val speedY: Float,
        val speedX: Float,
        var alpha: Float,
        val alphaSpeed: Float,
        val shape: GenreShape,
        val color: Int,
        var rotation: Float = 0f,
        val rotSpeed: Float = 0f
    )

    private val particles = mutableListOf<Particle>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var animator: ValueAnimator? = null
    private var currentShapeMode = GenreShape.DEFAULT_DOT

    // Genre-tailored palette
    private var primaryColor = 0xFF3B82F6.toInt()
    private var secondaryColor = 0xFF64FFDA.toInt()
    private var accentColor = 0xFF8B5CF6.toInt()

    // Reusable Path objects to prevent allocations in onDraw
    private val heartPath = Path()
    private val shieldPath = Path()
    private val swordPath = Path()
    private val crownPath = Path()
    private val starPath = Path()
    private val hexPath = Path()

    fun setGenreTheme(genreName: String) {
        val lower = genreName.lowercase()
        when {
            lower.contains("romance") -> {
                currentShapeMode = GenreShape.HEART
                primaryColor   = 0xFFFB7185.toInt() // Rose Coral
                secondaryColor = 0xFFF43F5E.toInt() // Deep Pink
                accentColor    = 0xFFFDA4AF.toInt() // Soft Rose
            }
            lower.contains("history") || lower.contains("classics") -> {
                currentShapeMode = GenreShape.WARRIOR
                primaryColor   = 0xFFD97706.toInt() // History Gold
                secondaryColor = 0xFFB91C1C.toInt() // Crimson Red
                accentColor    = 0xFFF59E0B.toInt() // Bronze Amber
            }
            lower.contains("fantasy") -> {
                currentShapeMode = GenreShape.MAGIC_STAR
                primaryColor   = 0xFF8B5CF6.toInt() // Fantasy Purple
                secondaryColor = 0xFFA855F7.toInt() // Violet
                accentColor    = 0xFF64FFDA.toInt() // Cyan Sparkle
            }
            lower.contains("sci-fi") || lower.contains("science") || lower.contains("tech") -> {
                currentShapeMode = GenreShape.CYBER_RING
                primaryColor   = 0xFF06B6D4.toInt() // Cyber Cyan
                secondaryColor = 0xFF3B82F6.toInt() // Neon Blue
                accentColor    = 0xFF10B981.toInt() // Matrix Green
            }
            lower.contains("mystery") -> {
                currentShapeMode = GenreShape.MYSTERY_ORB
                primaryColor   = 0xFFF59E0B.toInt() // Mystery Amber
                secondaryColor = 0xFF6366F1.toInt() // Indigo Shadow
                accentColor    = 0xFF818CF8.toInt() // Soft Indigo
            }
            lower.contains("self") || lower.contains("academic") -> {
                currentShapeMode = GenreShape.SUNBURST
                primaryColor   = 0xFF10B981.toInt() // Emerald
                secondaryColor = 0xFF34D399.toInt() // Soft Mint
                accentColor    = 0xFFFBBF24.toInt() // Sun Amber
            }
            else -> {
                currentShapeMode = GenreShape.DEFAULT_DOT
                primaryColor   = 0xFF3B82F6.toInt()
                secondaryColor = 0xFF64FFDA.toInt()
                accentColor    = 0xFF8B5CF6.toInt()
            }
        }
        if (width > 0 && height > 0) {
            buildParticles(width.toFloat(), height.toFloat())
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w == 0 || h == 0) return
        buildParticles(w.toFloat(), h.toFloat())
        startAnimation()
    }

    private fun buildParticles(w: Float, h: Float) {
        particles.clear()
        val palette = listOf(primaryColor, secondaryColor, accentColor)
        val count = if (currentShapeMode == GenreShape.DEFAULT_DOT) 35 else 28

        repeat(count) {
            val color = palette[Random.nextInt(palette.size)]
            val size = when (currentShapeMode) {
                GenreShape.HEART -> Random.nextFloat() * 18f + 12f
                GenreShape.WARRIOR -> Random.nextFloat() * 22f + 14f
                GenreShape.MAGIC_STAR -> Random.nextFloat() * 16f + 10f
                GenreShape.CYBER_RING -> Random.nextFloat() * 20f + 12f
                GenreShape.MYSTERY_ORB -> Random.nextFloat() * 24f + 12f
                GenreShape.SUNBURST -> Random.nextFloat() * 18f + 10f
                GenreShape.DEFAULT_DOT -> Random.nextFloat() * 6f + 2f
            }

            particles += Particle(
                x = Random.nextFloat() * w,
                y = Random.nextFloat() * h,
                size = size,
                speedY = -(Random.nextFloat() * 0.8f + 0.3f), // float upward
                speedX = (Random.nextFloat() - 0.5f) * 0.4f,
                alpha = Random.nextFloat() * 0.7f + 0.2f,
                alphaSpeed = (Random.nextFloat() * 0.015f + 0.005f),
                shape = currentShapeMode,
                color = color,
                rotation = Random.nextFloat() * 360f,
                rotSpeed = (Random.nextFloat() - 0.5f) * 1.5f
            )
        }
    }

    private fun startAnimation() {
        animator?.cancel()
        animator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 16L
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                updateParticles()
                invalidate()
            }
            start()
        }
    }

    private fun updateParticles() {
        val w = width.toFloat()
        val h = height.toFloat()
        for (p in particles) {
            p.y += p.speedY
            p.x += p.speedX
            p.rotation += p.rotSpeed

            p.alpha += p.alphaSpeed
            if (p.alpha > 0.95f || p.alpha < 0.15f) {
                // reverse pulsing
                p.alpha = p.alpha.coerceIn(0.15f, 0.95f)
            }

            // Wrap around top to bottom
            if (p.y < -p.size * 2) {
                p.y = h + p.size
                p.x = Random.nextFloat() * w
            }
            if (p.x < -p.size) p.x = w + p.size
            if (p.x > w + p.size) p.x = -p.size
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (p in particles) {
            paint.color = p.color
            paint.alpha = (p.alpha.coerceIn(0f, 1f) * 255).toInt()
            paint.style = Paint.Style.FILL

            canvas.save()
            canvas.translate(p.x, p.y)
            canvas.rotate(p.rotation)

            when (p.shape) {
                GenreShape.HEART -> drawHeart(canvas, p.size)
                GenreShape.WARRIOR -> {
                    // alternate between Shield, Sword, and Crown
                    val mod = (p.x + p.y).toInt() % 3
                    when (mod) {
                        0 -> drawShield(canvas, p.size)
                        1 -> drawSword(canvas, p.size)
                        else -> drawCrown(canvas, p.size)
                    }
                }
                GenreShape.MAGIC_STAR -> drawMagicStar(canvas, p.size)
                GenreShape.CYBER_RING -> drawCyberHex(canvas, p.size)
                GenreShape.MYSTERY_ORB -> drawMysteryOrb(canvas, p.size)
                GenreShape.SUNBURST -> drawSunburst(canvas, p.size)
                GenreShape.DEFAULT_DOT -> canvas.drawCircle(0f, 0f, p.size, paint)
            }

            canvas.restore()
        }
    }

    // ─── Custom Canvas Shape Helpers ─────────────────────────────────────────

    private fun drawHeart(canvas: Canvas, size: Float) {
        heartPath.reset()
        val s = size / 2f
        heartPath.moveTo(0f, s * 0.5f)
        heartPath.cubicTo(-s, -s * 0.5f, -s * 0.5f, -s * 1.5f, 0f, -s * 0.6f)
        heartPath.cubicTo(s * 0.5f, -s * 1.5f, s, -s * 0.5f, 0f, s * 0.5f)
        heartPath.close()
        canvas.drawPath(heartPath, paint)
    }

    private fun drawShield(canvas: Canvas, size: Float) {
        shieldPath.reset()
        val s = size / 2f
        shieldPath.moveTo(-s, -s)
        shieldPath.lineTo(s, -s)
        shieldPath.lineTo(s, 0f)
        shieldPath.cubicTo(s, s * 1.2f, 0f, s * 1.6f, 0f, s * 1.6f)
        shieldPath.cubicTo(0f, s * 1.6f, -s, s * 1.2f, -s, 0f)
        shieldPath.close()

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2.5f
        canvas.drawPath(shieldPath, paint)
        paint.style = Paint.Style.FILL
    }

    private fun drawSword(canvas: Canvas, size: Float) {
        swordPath.reset()
        val s = size / 2f
        // Blade
        swordPath.moveTo(0f, -s * 1.4f)
        swordPath.lineTo(s * 0.2f, s * 0.4f)
        swordPath.lineTo(-s * 0.2f, s * 0.4f)
        swordPath.close()

        // Guard
        swordPath.addRect(-s * 0.6f, s * 0.4f, s * 0.6f, s * 0.6f, Path.Direction.CW)
        // Hilt
        swordPath.addRect(-s * 0.15f, s * 0.6f, s * 0.15f, s * 1.2f, Path.Direction.CW)

        canvas.drawPath(swordPath, paint)
    }

    private fun drawCrown(canvas: Canvas, size: Float) {
        crownPath.reset()
        val s = size / 2f
        crownPath.moveTo(-s, s * 0.6f)
        crownPath.lineTo(-s * 0.8f, -s * 0.6f)
        crownPath.lineTo(-s * 0.3f, 0f)
        crownPath.lineTo(0f, -s)
        crownPath.lineTo(s * 0.3f, 0f)
        crownPath.lineTo(s * 0.8f, -s * 0.6f)
        crownPath.lineTo(s, s * 0.6f)
        crownPath.close()

        canvas.drawPath(crownPath, paint)
    }

    private fun drawMagicStar(canvas: Canvas, size: Float) {
        starPath.reset()
        val s = size / 2f
        starPath.moveTo(0f, -s)
        starPath.quadTo(0f, 0f, s, 0f)
        starPath.quadTo(0f, 0f, 0f, s)
        starPath.quadTo(0f, 0f, -s, 0f)
        starPath.quadTo(0f, 0f, 0f, -s)
        starPath.close()

        canvas.drawPath(starPath, paint)
    }

    private fun drawCyberHex(canvas: Canvas, size: Float) {
        hexPath.reset()
        val r = size / 2f
        for (i in 0 until 6) {
            val angle = Math.PI / 3 * i
            val x = (r * cos(angle)).toFloat()
            val y = (r * sin(angle)).toFloat()
            if (i == 0) hexPath.moveTo(x, y) else hexPath.lineTo(x, y)
        }
        hexPath.close()

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        canvas.drawPath(hexPath, paint)
        paint.style = Paint.Style.FILL
    }

    private fun drawMysteryOrb(canvas: Canvas, size: Float) {
        val r = size / 2f
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f
        canvas.drawCircle(0f, 0f, r, paint)
        paint.style = Paint.Style.FILL
        canvas.drawCircle(0f, 0f, r * 0.4f, paint)
    }

    private fun drawSunburst(canvas: Canvas, size: Float) {
        val r = size / 2f
        canvas.drawCircle(0f, 0f, r * 0.5f, paint)
        paint.strokeWidth = 2f
        for (i in 0 until 8) {
            val angle = Math.PI / 4 * i
            val x1 = (r * 0.7f * cos(angle)).toFloat()
            val y1 = (r * 0.7f * sin(angle)).toFloat()
            val x2 = (r * 1.2f * cos(angle)).toFloat()
            val y2 = (r * 1.2f * sin(angle)).toFloat()
            canvas.drawLine(x1, y1, x2, y2, paint)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }
}
