package ru.paramonov.vknewsclient.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.paramonov.vknewsclient.R

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Drawer() {
    val items = listOf(
        DrawerItem(imageVector = Icons.Filled.AccountCircle, label = "Account"),
        DrawerItem(imageVector = Icons.Filled.Email, label = "Email"),
        DrawerItem(imageVector = Icons.Filled.AddCircle, label = "Add")
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItem = remember { mutableStateOf(items[0]) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Image(
                    painter = painterResource(id = R.drawable.img),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                items.forEach { item ->
                    NavigationDrawerItem(
                        label = { Text(text = item.label) },
                        selected = selectedItem.value == item,
                        onClick = {
                            selectedItem.value = item
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.imageVector,
                                contentDescription = item.label
                            )
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    ) {
        val names = listOf("Home", "Settings", "Info")
        val navigationState = remember { mutableStateOf(0) }
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(text = "Center TopAppBar")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = null
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null
                            )
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar {
                    names.forEachIndexed { index, name ->
                        NavigationBarItem(
                            selected = navigationState.value == index,
                            onClick = {
                                navigationState.value = index
                                scope.launch {
                                    drawerState.open()
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = Icons.Filled.FavoriteBorder,
                                    contentDescription = name
                                )
                            },
                            label = { Text(text = name) }
                        )
                    }
                }
            }
        ) {
            Text(
                text = "This is scaffold content",
                modifier = Modifier.padding(it)
            )
        }
    }
}

data class DrawerItem(
    val imageVector: ImageVector,
    val label: String
)