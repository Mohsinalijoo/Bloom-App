package com.example.bloom_app.ui.theme.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bloom_app.ui.theme.BloomCenter
import com.example.bloom_app.ui.theme.BloomPetalDark
import com.example.bloom_app.ui.theme.BloomPetalLight
import com.example.bloom_app.ui.theme.BloomPetalMedium

@Composable
fun BloomFlower(
    modifier: Modifier = Modifier,
    size: Dp = 200.dp,
    progress: Float = 1f // 0f to 1f for petal fill
) {
    Canvas(modifier = modifier.size(size)) {
        val centerX = this.size.width / 2
        val centerY = this.size.height / 2
        val petalLength = this.size.width * 0.38f
        val petalWidth = this.size.width * 0.25f

        val petalColors = listOf(
            BloomPetalDark,
            BloomPetalMedium,
            BloomPetalLight,
            BloomPetalDark,
            BloomPetalMedium
        )

        // Draw 5 petals
        for (i in 0 until 5) {
            val angle = i * 72f - 90f
            val alpha = if (progress >= (i + 1) / 5f) 1f else if (progress > i / 5f) {
                ((progress - i / 5f) * 5f).coerceIn(0f, 1f)
            } else 0.15f

            rotate(angle, pivot = Offset(centerX, centerY)) {
                drawPetal(
                    centerX = centerX,
                    centerY = centerY,
                    petalLength = petalLength,
                    petalWidth = petalWidth,
                    color = petalColors[i % petalColors.size],
                    alpha = alpha
                )
            }
        }

        // Draw center circle
        drawCircle(
            color = BloomCenter,
            radius = this.size.width * 0.1f,
            center = Offset(centerX, centerY)
        )
    }
}

private fun DrawScope.drawPetal(
    centerX: Float,
    centerY: Float,
    petalLength: Float,
    petalWidth: Float,
    color: Color,
    alpha: Float
) {
    val path = Path().apply {
        moveTo(centerX, centerY)
        cubicTo(
            centerX - petalWidth / 2,
            centerY - petalLength * 0.4f,
            centerX - petalWidth / 3,
            centerY - petalLength * 0.9f,
            centerX,
            centerY - petalLength
        )
        cubicTo(
            centerX + petalWidth / 3,
            centerY - petalLength * 0.9f,
            centerX + petalWidth / 2,
            centerY - petalLength * 0.4f,
            centerX,
            centerY
        )
        close()
    }
    drawPath(path, color = color.copy(alpha = alpha))
}

@Composable
fun SmallDecorativeStar(
    modifier: Modifier = Modifier,
    size: Dp = 8.dp,
    color: Color = BloomPetalMedium
) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2, this.size.height / 2)
        val armLength = this.size.width / 2

        // Draw + shape
        drawLine(
            color = color,
            start = Offset(center.x, center.y - armLength),
            end = Offset(center.x, center.y + armLength),
            strokeWidth = 1.5f
        )
        drawLine(
            color = color,
            start = Offset(center.x - armLength, center.y),
            end = Offset(center.x + armLength, center.y),
            strokeWidth = 1.5f
        )
    }
}