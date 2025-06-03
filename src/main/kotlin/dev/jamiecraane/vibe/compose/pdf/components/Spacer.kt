package dev.jamiecraane.vibe.compose.pdf.components

import dev.jamiecraane.vibe.compose.pdf.layout.Constraints
import dev.jamiecraane.vibe.compose.pdf.layout.Measurable
import dev.jamiecraane.vibe.compose.pdf.layout.Size

/**
 * A layout component that creates empty space.
 *
 * @param modifier The modifier to be applied to the spacer
 */
@Composable
class SpacerImpl(
    val modifier: Modifier = Modifier.None
) : Measurable {
    override fun measure(constraints: Constraints): Size {
        // Use the width and height from the modifier, or default to 0
        val width = modifier.width ?: 0f
        val height = modifier.height ?: 0f

        return Size.fromPoints(
            constraints.constrainWidth(width),
            constraints.constrainHeight(height)
        )
    }
}

/**
 * Creates a Spacer composable.
 *
 * @param modifier The modifier to be applied to the spacer
 */
@Composable
fun Spacer(
    modifier: Modifier = Modifier.None
): SpacerImpl {
    return SpacerImpl(modifier)
}
