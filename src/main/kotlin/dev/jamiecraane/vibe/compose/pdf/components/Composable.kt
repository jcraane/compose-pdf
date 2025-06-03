package dev.jamiecraane.vibe.compose.pdf.components

/**
 * Marker interface for composable functions.
 * This is used to indicate that a function is a composable function that can be used in a Compose-style syntax.
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Composable
