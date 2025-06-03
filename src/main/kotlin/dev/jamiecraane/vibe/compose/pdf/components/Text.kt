package dev.jamiecraane.vibe.compose.pdf.components

import dev.jamiecraane.vibe.compose.pdf.layout.Constraints
import dev.jamiecraane.vibe.compose.pdf.layout.Measurable
import dev.jamiecraane.vibe.compose.pdf.layout.Size
import dev.jamiecraane.vibe.compose.pdf.units.Point

/**
 * A component that displays text in a PDF document.
 *
 * @param text The text to display
 * @param fontSize The font size in points
 * @param fontFamily The font family to use
 * @param fontWeight The font weight to use
 * @param color The text color
 */
class TextImpl(
    val text: String,
    val fontSize: Float = 12f,
    val fontFamily: String = "Helvetica",
    val fontWeight: FontWeight = FontWeight.Normal,
    val color: Color = Color.Black
) : Measurable {
    override fun measure(constraints: Constraints): Size {
        // In a real implementation, this would measure the text based on the font and content
        // For now, we'll use a simple approximation
        val width = text.length * fontSize * 0.6f
        val height = fontSize * 1.2f

        return Size.fromPoints(
            constraints.constrainWidth(width),
            constraints.constrainHeight(height)
        )
    }
}

/**
 * Represents a font weight.
 */
enum class FontWeight {
    Normal,
    Bold,
    Light,
    Medium
}

/**
 * Represents a color.
 *
 * @param red The red component (0-255)
 * @param green The green component (0-255)
 * @param blue The blue component (0-255)
 * @param alpha The alpha component (0-255)
 */
data class Color(
    val red: Int,
    val green: Int,
    val blue: Int,
    val alpha: Int = 255
) {
    init {
        require(red in 0..255) { "Red component must be in range 0..255" }
        require(green in 0..255) { "Green component must be in range 0..255" }
        require(blue in 0..255) { "Blue component must be in range 0..255" }
        require(alpha in 0..255) { "Alpha component must be in range 0..255" }
    }

    companion object {
        val Black = Color(0, 0, 0)
        val White = Color(255, 255, 255)
        val Red = Color(255, 0, 0)
        val Green = Color(0, 255, 0)
        val Blue = Color(0, 0, 255)
    }
}

/**
 * Creates a Text component with the given parameters.
 *
 * @param text The text to display
 * @param fontSize The font size in points
 * @param fontFamily The font family to use
 * @param fontWeight The font weight to use
 * @param color The text color
 * @return A new TextImpl component
 */
fun text(
    text: String,
    fontSize: Float = 12f,
    fontFamily: String = "Helvetica",
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black
): TextImpl = TextImpl(text, fontSize, fontFamily, fontWeight, color)

/**
 * Creates a Text composable with the given parameters.
 *
 * @param text The text to display
 * @param fontSize The font size in points
 * @param fontFamily The font family to use
 * @param fontWeight The font weight to use
 * @param color The text color
 * @return A new TextImpl component
 */
@Composable
fun Text(
    text: String,
    fontSize: Float = 12f,
    fontFamily: String = "Helvetica",
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.Black
): TextImpl = TextImpl(text, fontSize, fontFamily, fontWeight, color)
