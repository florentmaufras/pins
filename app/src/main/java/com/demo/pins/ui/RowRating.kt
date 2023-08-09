package com.demo.pins.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Grade
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun RowRating(
    rating: Int,
    horizontalArrangement: Arrangement.Horizontal,
    modifier: Modifier = Modifier,
    maxStar: Int = 5
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement
    ) {
        for (i in 1..maxStar) {
            if (rating >= i) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Star"
                )
            } else {
                Icon(
                    imageVector = Icons.Outlined.Grade,
                    contentDescription = ""
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    RowRating(
        3,
        Arrangement.Center
    )
}