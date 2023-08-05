package ru.paramonov.vknewsclient.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TestButton() {

    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            Text(
                text = "Yes",
                modifier = Modifier.padding(8.dp)
            )
        },
        dismissButton = {
            Text(
                text = "No",
                modifier = Modifier.padding(8.dp)
            )
        },
        title = {
            Text(text = "Are you sure???")
        },
        text = {
            Text(text = "Do you want to delete this file???")
        }
    )
}
