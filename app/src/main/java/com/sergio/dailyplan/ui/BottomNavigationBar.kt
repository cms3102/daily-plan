package com.sergio.dailyplan.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.sergio.common.base.BaseDestination


@Composable
fun BottomNavigationBar(
    destinations: List<BaseDestination>,
    currentDestination: NavDestination?,
    onMenuClick: (BaseDestination) -> Unit,
) {
    NavigationBar(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        destinations.forEach { destination ->
            BottomNavigationItem(
                isSelected = currentDestination.isSelectedTab(destination),
                destination = destination
            ) {
                onMenuClick(destination)
            }
        }
    }
}

@Composable
fun RowScope.BottomNavigationItem(
    isSelected: Boolean,
    destination: BaseDestination,
    onMenuClick: () -> Unit
) {
    NavigationBarItem(
        selected = isSelected,
        onClick = onMenuClick,
        icon = {
            Icon(
                imageVector = if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                contentDescription = null
            )
        },
        label = {
            Text(text = stringResource(id = destination.menuTitle))
        }
    )
}

private fun NavDestination?.isSelectedTab(destination: BaseDestination): Boolean {
    return this?.hierarchy?.any {
        it.route == destination.route
    } ?: false
}