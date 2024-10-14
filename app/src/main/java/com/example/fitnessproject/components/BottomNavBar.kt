package com.example.fitnessproject.components

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fitnessproject.navigation.Routes


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
)

@Composable
fun BottomNavigationBar(
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit,
    navController: NavController
) {
    // Define the colors from the theme
    val selectedColor = MaterialTheme.colorScheme.primary
    val unselectedColor = MaterialTheme.colorScheme.onSurface // Use the text color for unselected items
    val backgroundColor = MaterialTheme.colorScheme.surface // Background color for the navigation bar

    val items = listOf(
        BottomNavigationItem(
            title = "Activities",
            selectedIcon = Icons.Filled.DateRange,
            unselectedIcon = Icons.Outlined.DateRange,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            hasNews = false
        ),
    )

    NavigationBar(
        modifier = Modifier.height(110.dp),
        containerColor = backgroundColor, // Use the theme's surface color
        contentColor = unselectedColor // Use the text color for unselected items
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        if (index == selectedItemIndex) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.title,

                    )
                },
                label = { Text(item.title) },
                selected = index == selectedItemIndex,
                onClick = {
                    onItemSelected(index)

                    // Navigate to corresponding screen
                    when (index) {
                        0 -> {
                            navController.popBackStack()
                            navController.navigate(Routes.ACT_LIST)
                        }
                        1 -> {
                            navController.popBackStack()
                            navController.navigate(Routes.HOME_SCREEN)
                        }
                        2 -> {
                            navController.popBackStack()
                            navController.navigate(Routes.PROFILE_SCREEN)
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = selectedColor,
//                    unselectedIconColor = unselectedColor,
//                    selectedIconColor = selectedColor // Highlight selected icon
                )
            )
        }
    }
}
