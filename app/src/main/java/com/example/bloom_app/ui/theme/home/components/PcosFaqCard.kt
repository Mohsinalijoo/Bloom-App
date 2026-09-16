package com.example.bloom_app.ui.theme.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.bloom_app.ui.theme.BloomCardBackground
import com.example.bloom_app.ui.theme.BloomDivider
import com.example.bloom_app.ui.theme.BloomIndicatorActive
import com.example.bloom_app.ui.theme.BloomIndicatorInactive
import com.example.bloom_app.ui.theme.BloomPrimary
import com.example.bloom_app.ui.theme.BloomTextDark
import com.example.bloom_app.ui.theme.BloomTextLight
import com.example.bloom_app.ui.theme.DMSerifDisplay

@Composable
fun PcosFaqCard(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "PCOS FAQ",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = DMSerifDisplay,
                fontStyle = FontStyle.Italic
            ),
            color = BloomTextDark
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = BloomCardBackground
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "What exactly is PCOS?",
                            style = MaterialTheme.typography.titleSmall,
                            color = BloomTextDark
                        )
                        Text(
                            text = "PCOS stands for polycystic ovary syndrome...",
                            style = MaterialTheme.typography.bodySmall,
                            color = BloomTextLight,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = "Read more",
                        tint = BloomPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Page indicators (dots)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(5) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == 0) BloomIndicatorActive
                            else BloomIndicatorInactive
                        )
                )
            }
        }
    }
}