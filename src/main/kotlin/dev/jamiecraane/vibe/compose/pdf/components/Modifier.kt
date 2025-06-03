package dev.jamiecraane.vibe.compose.pdf.components

import dev.jamiecraane.vibe.compose.pdf.units.Point

/**
 * A modifier that can be used to modify the appearance and layout of a composable.
 */
class Modifier private constructor(
    val height: Float? = null,
    val width: Float? = null,
    val padding: Padding? = null
) {
    companion object {
        /**
         * The default modifier with no modifications.
         */
        val None = Modifier()
    }

    /**
     * Sets the height of the composable.
     *
     * @param height The height in points
     * @return A new modifier with the height set
     */
    fun height(height: Float): Modifier {
        return Modifier(height = height, width = this.width, padding = this.padding)
    }

    /**
     * Sets the width of the composable.
     *
     * @param width The width in points
     * @return A new modifier with the width set
     */
    fun width(width: Float): Modifier {
        return Modifier(height = this.height, width = width, padding = this.padding)
    }

    /**
     * Sets the padding of the composable.
     *
     * @param padding The padding to apply
     * @return A new modifier with the padding set
     */
    fun padding(padding: Padding): Modifier {
        return Modifier(height = this.height, width = this.width, padding = padding)
    }
}

/**
 * Represents padding for a composable.
 */
data class Padding(
    val left: Float = 0f,
    val top: Float = 0f,
    val right: Float = 0f,
    val bottom: Float = 0f
) {
    companion object {
        /**
         * Creates padding with the same value on all sides.
         *
         * @param all The padding value for all sides
         * @return A new padding with the same value on all sides
         */
        fun all(all: Float): Padding {
            return Padding(all, all, all, all)
        }
    }
}

/**
 * Density-independent pixel (dp) unit for use with modifiers.
 */
val Float.dp: Float
    get() = this * 1.5f // Simple conversion factor for demonstration

/**
 * Scaled pixel (sp) unit for use with text sizes.
 */
val Float.sp: Float
    get() = this // For simplicity, we'll use the same value as points
