package dev.jamiecraane.vibe.compose.pdf.layout

import dev.jamiecraane.vibe.compose.pdf.units.Point

/**
 * Represents a component that can be measured and laid out.
 */
interface Measurable {
    /**
     * Measures the component with the given constraints.
     *
     * @param constraints The constraints to measure with
     * @return The size of the component after measurement
     */
    fun measure(constraints: Constraints): Size
}

/**
 * Represents constraints for measurement.
 * Similar to Compose's Constraints, this class specifies minimum and maximum dimensions.
 */
data class Constraints(
    val minWidth: Float,
    val maxWidth: Float,
    val minHeight: Float,
    val maxHeight: Float
) {
    init {
        require(minWidth >= 0) { "minWidth must be >= 0" }
        require(maxWidth >= minWidth) { "maxWidth must be >= minWidth" }
        require(minHeight >= 0) { "minHeight must be >= 0" }
        require(maxHeight >= minHeight) { "maxHeight must be >= minHeight" }
    }

    /**
     * Constrains the given width to be within the minimum and maximum width constraints.
     */
    fun constrainWidth(width: Float): Float = width.coerceIn(minWidth, maxWidth)

    /**
     * Constrains the given height to be within the minimum and maximum height constraints.
     */
    fun constrainHeight(height: Float): Float = height.coerceIn(minHeight, maxHeight)

    /**
     * Constrains the given size to be within the constraints.
     */
    fun constrain(size: Size): Size {
        val width = constrainWidth(size.widthPt)
        val height = constrainHeight(size.heightPt)
        return Size.fromPoints(width, height)
    }

    companion object {
        /**
         * Creates fixed constraints with the given width and height.
         */
        fun fixed(width: Float, height: Float): Constraints =
            Constraints(width, width, height, height)

        /**
         * Creates constraints with the given minimum and maximum dimensions.
         */
        fun create(
            minWidth: Float = 0f,
            maxWidth: Float = Float.POSITIVE_INFINITY,
            minHeight: Float = 0f,
            maxHeight: Float = Float.POSITIVE_INFINITY
        ): Constraints = Constraints(minWidth, maxWidth, minHeight, maxHeight)

        /**
         * Default constraints with no minimum dimensions and infinite maximum dimensions.
         */
        val DEFAULT = create()
    }
}

/**
 * Represents a scope for measuring components.
 */
interface MeasureScope {
    /**
     * Measures a component with the given constraints.
     *
     * @param measurable The component to measure
     * @param constraints The constraints to measure with
     * @return The size of the component after measurement
     */
    fun measure(measurable: Measurable, constraints: Constraints): Size
}

/**
 * Represents a scope for laying out components.
 */
interface LayoutScope {
    /**
     * Places a component at the given position.
     *
     * @param measurable The component to place
     * @param position The position to place the component at
     */
    fun place(measurable: Measurable, position: Position)
}

/**
 * Basic implementation of MeasureScope.
 */
class BasicMeasureScope : MeasureScope {
    override fun measure(measurable: Measurable, constraints: Constraints): Size {
        return measurable.measure(constraints)
    }
}

/**
 * Basic implementation of LayoutScope.
 */
class BasicLayoutScope : LayoutScope {
    override fun place(measurable: Measurable, position: Position) {
        // In a real implementation, this would add the component to a layout tree
        // For now, this is just a placeholder
    }
}
