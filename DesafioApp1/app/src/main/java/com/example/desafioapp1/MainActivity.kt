package com.example.desafioapp1

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.desafioapp1.navigation.Desafio1Destinations
import com.example.desafioapp1.navigation.Desafio1Navigation
import com.example.desafioapp1.navigation.NavigationHandler
import com.example.desafioapp1.ui.theme.DesafioApp1Theme
import kotlinx.coroutines.launch

data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val items = listOf<NavigationItem>(
    NavigationItem(
        title = "Ejercicio 1",
        selectedIcon = Icons.Filled.Person,
        unselectedIcon = Icons.Outlined.Person,
    ),
    NavigationItem(
        title = "Ejercicio 2",
        selectedIcon = Icons.Filled.ShoppingCart,
        unselectedIcon = Icons.Outlined.ShoppingCart,
    ),
    NavigationItem(
        title = "Ejercicio 3",
        selectedIcon = Icons.Filled.Add,
        unselectedIcon = Icons.Outlined.Add,
    ),
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DesafioApp1Theme {
                navigationDrawer();
            }

        }
    }
}

@Composable
@Preview
fun navigationDrawer() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }
        val navController = rememberNavController();
        val navigationActions = remember(navController) {
            Desafio1Navigation(navController = navController);
        }

        val navBackStackEntry by navController.currentBackStackEntryAsState();
        val currentRoute = navBackStackEntry?.destination?.route ?: Desafio1Destinations.HOME_ROUTE;

        ModalNavigationDrawer(
            drawerContent = {
                ModalDrawerSheet {
                    Spacer(modifier = Modifier.height(16.dp))
                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            label = {
                                    Text(text = item.title,
                                        fontWeight = FontWeight.W400,
                                        fontSize = 17.sp)
                            },
                            selected = index == selectedItemIndex,
                            onClick = routeSelected(
                                route = item.title,
                                navigationActions = navigationActions
                            ) {
                                selectedItemIndex = index
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedItemIndex) {
                                        item.selectedIcon
                                    } else item.unselectedIcon,
                                    contentDescription = item.title
                                )
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                        )

                    }
                }
            },
            drawerState = drawerState
        ) {
            //screenContent(drawerState);
            NavigationHandler(
                navController = navController,
                drawerState = drawerState,
                navigateToHome = navigationActions.navigateToHome
            );
        }
    }
}

fun routeSelected(route: String,
                  navigationActions: Desafio1Navigation,
                  additionalFunction: () -> Unit):  () -> Unit {
    val navigationFunction: () -> Unit = when(route.replace(" ", "")) {
        "Ejercicio1" -> navigationActions.navigateToEjercicio1
        "Ejercicio2" -> navigationActions.navigateToEjercicio2
        "Ejercicio3" -> navigationActions.navigateToEjercicio3
        else -> {
            navigationActions.navigateToHome
        }
    }
    return {
        navigationFunction.invoke()
        additionalFunction.invoke()
    }
}




