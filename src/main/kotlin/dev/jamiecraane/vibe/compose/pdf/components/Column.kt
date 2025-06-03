package dev.jamiecraane.vibe.compose.pdf.components

import dev.jamiecraane.vibe.compose.pdf.layout.Constraints
import dev.jamiecraane.vibe.compose.pdf.layout.Measurable
import dev.jamiecraane.vibe.compose.pdf.layout.Position
import dev.jamiecraane.vibe.compose.pdf.layout.Size

/**
 * A layout component that places its children in a vertical sequence.
 *
 * @param modifier The modifier to be applied to the layout
 * @param content The content of the column
 */
@Composable
class Column(
    val modifier: Modifier = Modifier.None,
    val children: MutableList<Measurable> = mutableListOf()
) : Measurable {
    override fun measure(constraints: Constraints): Size {
        var totalHeight = 0f
        var maxWidth = 0f

        // Measure each child
        val childSizes = children.map { child ->
            val childSize = child.measure(constraints)
            totalHeight += childSize.heightPt
            maxWidth = maxOf(maxWidth, childSize.widthPt)
            childSize
        }

        // Apply any height constraint from the modifier
        val finalHeight = modifier.height ?: totalHeight
        val finalWidth = modifier.width ?: maxWidth

        return Size.fromPoints(
            constraints.constrainWidth(finalWidth),
            constraints.constrainHeight(finalHeight)
        )
    }

    /**
     * Places the children of the column at the appropriate positions.
     *
     * @param position The position of the column
     * @return A list of pairs of measurables and their positions
     */
    fun placeChildren(position: Position): List<Pair<Measurable, Position>> {
        var currentY = position.yPt
        val result = mutableListOf<Pair<Measurable, Position>>()

        // Apply padding if specified
        val paddingLeft = modifier.padding?.left ?: 0f
        val paddingTop = modifier.padding?.top ?: 0f

        currentY += paddingTop

        // Place each child
        children.forEach { child ->
            val childSize = child.measure(Constraints.DEFAULT)
            val childPosition = Position.fromPoints(position.xPt + paddingLeft, currentY)
            result.add(child to childPosition)
            currentY += childSize.heightPt
        }

        return result
    }
}

/**
 * Creates a Column composable.
 *
 * @param modifier The modifier to be applied to the layout
 * @param content The content of the column
 */
@Composable
fun Column(
    modifier: Modifier = Modifier.None,
    content: ColumnScope.() -> Unit
): Column {
    val column = Column(modifier)
    val scope = ColumnScope(column)
    scope.content()
    return column
}

/**
 * Scope for adding children to a Column.
 */
class ColumnScope(private val column: Column) {
    /**
     * Adds a child to the column.
     *
     * @param child The child to add
     */
    fun add(child: Measurable) {
        column.children.add(child)
    }

    /**
     * Adds a Text component to the column.
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
     * Adds a Spacer component to the column.
     */
    @Composable
    fun Spacer(modifier: Modifier = Modifier.None) {
        add(dev.jamiecraane.vibe.compose.pdf.components.Spacer(modifier))
    }

    /**
     * Adds a Row component to the column.
     */
    @Composable
    fun Row(
        modifier: Modifier = Modifier.None,
        content: RowScope.() -> Unit
    ) {
        add(dev.jamiecraane.vibe.compose.pdf.components.Row(modifier, content))
    }
}
