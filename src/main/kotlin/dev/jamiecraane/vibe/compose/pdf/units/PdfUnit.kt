package dev.jamiecraane.vibe.compose.pdf.units

/**
 * Base class for PDF-specific units.
 * In PDF, the default unit is a point (1/72 inch).
 */
sealed class PdfUnit(val value: Float) {
    /**
     * Converts this unit to points, which is the base unit in PDF.
     */
    abstract fun toPoints(): Float
}

/**
 * Represents a point unit in PDF (1/72 inch).
 * This is the base unit in PDF documents.
 */
class Point(value: Float) : PdfUnit(value) {
    override fun toPoints(): Float = value

    companion object {
        /**
         * Creates a Point with the specified value.
         */
        fun of(value: Float): Point = Point(value)

        /**
         * Creates a Point with the specified value.
         */
        fun of(value: Int): Point = Point(value.toFloat())
    }
}

/**
 * Represents a millimeter unit in PDF.
 * 1 mm = 2.83465 points
 */
class Millimeter(value: Float) : PdfUnit(value) {
    override fun toPoints(): Float = value * 2.83465f

    companion object {
        /**
         * Creates a Millimeter with the specified value.
         */
        fun of(value: Float): Millimeter = Millimeter(value)

        /**
         * Creates a Millimeter with the specified value.
         */
        fun of(value: Int): Millimeter = Millimeter(value.toFloat())
    }
}

/**
 * Represents an inch unit in PDF.
 * 1 inch = 72 points
 */
class Inch(value: Float) : PdfUnit(value) {
    override fun toPoints(): Float = value * 72f

    companion object {
        /**
         * Creates an Inch with the specified value.
         */
        fun of(value: Float): Inch = Inch(value)

        /**
         * Creates an Inch with the specified value.
         */
        fun of(value: Int): Inch = Inch(value.toFloat())
    }
}
