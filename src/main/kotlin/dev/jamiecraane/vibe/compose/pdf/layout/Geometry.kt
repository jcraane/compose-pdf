package dev.jamiecraane.vibe.compose.pdf.layout

import dev.jamiecraane.vibe.compose.pdf.units.PdfUnit
import dev.jamiecraane.vibe.compose.pdf.units.Point

/**
 * Represents a size with width and height in PDF units.
 */
data class Size(
    val width: PdfUnit,
    val height: PdfUnit
) {
    /**
     * Width in points.
     */
    val widthPt: Float
        get() = width.toPoints()

    /**
     * Height in points.
     */
    val heightPt: Float
        get() = height.toPoints()

    companion object {
        /**
         * Creates a Size with the specified width and height in points.
         */
        fun fromPoints(width: Float, height: Float): Size =
            Size(Point(width), Point(height))

        /**
         * Creates a Size with the specified width and height in points.
         */
        fun fromPoints(width: Int, height: Int): Size =
            Size(Point(width.toFloat()), Point(height.toFloat()))

        /**
         * A zero size.
         */
        val ZERO = Size(Point(0f), Point(0f))
    }
}

/**
 * Represents a position with x and y coordinates in PDF units.
 */
data class Position(
    val x: PdfUnit,
    val y: PdfUnit
) {
    /**
     * X coordinate in points.
     */
    val xPt: Float
        get() = x.toPoints()

    /**
     * Y coordinate in points.
     */
    val yPt: Float
        get() = y.toPoints()

    companion object {
        /**
         * Creates a Position with the specified x and y coordinates in points.
         */
        fun fromPoints(x: Float, y: Float): Position =
            Position(Point(x), Point(y))

        /**
         * Creates a Position with the specified x and y coordinates in points.
         */
        fun fromPoints(x: Int, y: Int): Position =
            Position(Point(x.toFloat()), Point(y.toFloat()))

        /**
         * The origin position (0, 0).
         */
        val ZERO = Position(Point(0f), Point(0f))
    }
}

/**
 * Represents a rectangle with position and size.
 */
data class Rect(
    val position: Position,
    val size: Size
) {
    /**
     * Left edge X coordinate in points.
     */
    val left: Float
        get() = position.xPt

    /**
     * Top edge Y coordinate in points.
     */
    val top: Float
        get() = position.yPt

    /**
     * Right edge X coordinate in points.
     */
    val right: Float
        get() = position.xPt + size.widthPt

    /**
     * Bottom edge Y coordinate in points.
     */
    val bottom: Float
        get() = position.yPt + size.heightPt

    /**
     * Width in points.
     */
    val width: Float
        get() = size.widthPt

    /**
     * Height in points.
     */
    val height: Float
        get() = size.heightPt

    companion object {
        /**
         * Creates a Rect with the specified left, top, right, and bottom coordinates in points.
         */
        fun fromLTRB(left: Float, top: Float, right: Float, bottom: Float): Rect {
            val position = Position.fromPoints(left, top)
            val size = Size.fromPoints(right - left, bottom - top)
            return Rect(position, size)
        }

        /**
         * An empty rectangle at the origin.
         */
        val ZERO = Rect(Position.ZERO, Size.ZERO)
    }
}
