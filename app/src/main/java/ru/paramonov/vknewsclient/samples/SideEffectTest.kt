package ru.paramonov.vknewsclient.samples

import android.os.Handler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SideEffectTest(number: MyNumber) {
    Column {
        LazyColumn {
            repeat(5) {
                item {
                    Text(text = "${number.value}")
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Handler().postDelayed({
            number.value = 5
        }, 3000)
        LazyColumn {
            repeat(5) {
                item {
                    Text(text = "${number.value}")
                }
            }
        }
    }
}

data class MyNumber(var value: Int)