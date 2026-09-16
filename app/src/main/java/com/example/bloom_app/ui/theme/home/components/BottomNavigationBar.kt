package com.example.bloom_app.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.bloom_app.ui.theme.BloomDivider
import com.example.bloom_app.ui.theme.BloomPrimary
import com.example.bloom_app.ui.theme.BloomSurface
import com.example.bloom_app.ui.theme.BloomTextLight

data class BottomNavItem(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun BottomNavigationBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem("Dashboard", Icons.Filled.Home, Icons.Outlined.Home),
        BottomNavItem("Insights", Icons.Filled.BarChart, Icons.Outlined.BarChart),
        BottomNavItem("Learn", Icons.AutoMirrored.Filled.MenuBook, Icons.AutoMirrored.Outlined.MenuBook),
        BottomNavItem("Settings", Icons.Filled.Settings, Icons.Outlined.Settings)
    )

    Column {
        Divider(color = BloomDivider, thickness = 0.5.dp)

        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(BloomSurface)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onItemSelected(index) }
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = if (selectedIndex == index) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                        tint = if (selectedIndex == index) BloomPrimary else BloomTextLight,
                        modifier = Modifier.size(24.dp)
                    )

                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (selectedIndex == index) BloomPrimary else BloomTextLight
                    )
                }
            }
        }
    }
}