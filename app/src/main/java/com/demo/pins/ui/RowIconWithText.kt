package com.demo.pins.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.demo.pins.R
import com.demo.pins.ui.common.Spacing


@Composable
fun RowIconWithText(
    imageVector: ImageVector,
    iconContentDescription: String,
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = iconContentDescription
        )
        Text(
            style = MaterialTheme.typography.bodySmall,
            text = text,
            modifier = Modifier.padding(start = Spacing.DEFAULT.value)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    RowIconWithText(
        imageVector = ImageVector.vectorResource(R.drawable.pin),
        iconContentDescription = "Description test",
        text = "This is an attached text"
    )
}