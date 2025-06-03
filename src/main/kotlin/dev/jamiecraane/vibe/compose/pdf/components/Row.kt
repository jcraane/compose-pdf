package dev.jamiecraane.vibe.compose.pdf.components

import dev.jamiecraane.vibe.compose.pdf.layout.Constraints
import dev.jamiecraane.vibe.compose.pdf.layout.Measurable
import dev.jamiecraane.vibe.compose.pdf.layout.Position
import dev.jamiecraane.vibe.compose.pdf.layout.Size

/**
 * A layout component that places its children in a horizontal sequence.
 *
 * @param modifier The modifier to be applied to the layout
 * @param children The children of the row
 */
@Composable
class Row(
    val modifier: Modifier = Modifier.None,
    val children: MutableList<Measurable> = mutableListOf()
) : Measurable {
    override fun measure(constraints: Constraints): Size {
        var totalWidth = 0f
        var maxHeight = 0f

        // Measure each child
        val childSizes = children.map { child ->
            val childSize = child.measure(constraints)
            totalWidth += childSize.widthPt
            maxHeight = maxOf(maxHeight, childSize.heightPt)
            childSize
        }

        // Apply any width constraint from the modifier
        val finalWidth = modifier.width ?: totalWidth
        val finalHeight = modifier.height ?: maxHeight

        return Size.fromPoints(
            constraints.constrainWidth(finalWidth),
            constraints.constrainHeight(finalHeight)
        )
    }

    /**
     * Places the children of the row at the appropriate positions.
     *
     * @param position The position of the row
     * @return A list of pairs of measurables and their positions
     */
    fun placeChildren(position: Position): List<Pair<Measurable, Position>> {
        var currentX = position.xPt
        val result = mutableListOf<Pair<Measurable, Position>>()

        // Apply padding if specified
        val paddingLeft = modifier.padding?.left ?: 0f
        val paddingTop = modifier.padding?.top ?: 0f

        currentX += paddingLeft

        // Place each child
        children.forEach { child ->
            val childSize = child.measure(Constraints.DEFAULT)
            val childPosition = Position.fromPoints(currentX, position.yPt + paddingTop)
            result.add(child to childPosition)
            currentX += childSize.widthPt
        }

        return result
    }
}

/**
 * Creates a Row composable.
 *
 * @param modifier The modifier to be applied to the layout
 * @param content The content of the row
 */
@Composable
fun Row(
    modifier: Modifier = Modifier.None,
    content: RowScope.() -> Unit
): Row {
    val row = Row(modifier)
    val scope = RowScope(row)
    scope.content()
    return row
}

/**
 * Scope for adding children to a Row.
 */
class RowScope(private val row: Row) {
    /**
     * Adds a child to the row.
     *
     * @param child The child to add
     */
    fun add(child: Measurable) {
        row.children.add(child)
    }

    /**
     * Adds a Text component to the row.
     */
    @Composable
    fun Text(
        text: String,
        fontSize: Float = 12f,
        fontFamily: String = "Helvetica",
        fontWeight: FontWeight = FontWeight.Normal,
        color: Color = Color.Black
    ) {
        add(dev.jamiecraane.vibe.compose.pdf.components.Text(text, fontSize, fontFamily, fontWeight, color))
    }

    /**
     * Adds a Spacer component to the row.
     */
    @Composable
    fun Spacer(modifier: Modifier = Modifier.None) {
        add(dev.jamiecraane.vibe.compose.pdf.components.Spacer(modifier))
    }

    /**
     * Adds a Column component to the row.
     */
    @Composable
    fun Column(
        modifier: Modifier = Modifier.None,
        content: ColumnScope.() -> Unit
    ) {
        add(dev.jamiecraane.vibe.compose.pdf.components.Column(modifier, content))
    }
}
