package ru.paramonov.vknewsclient.presentation.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.paramonov.vknewsclient.R
import ru.paramonov.vknewsclient.ui.theme.LightGray
import ru.paramonov.vknewsclient.ui.theme.VkDefault

@Composable
fun LoginScreen(
    onLoginClickListener: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LightGray),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.vk_logo),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(50.dp))
                Button(
                    onClick = { onLoginClickListener() },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = VkDefault
                    )
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.button_auth
                        )
                    )
                }
            }
        }
    }
}
