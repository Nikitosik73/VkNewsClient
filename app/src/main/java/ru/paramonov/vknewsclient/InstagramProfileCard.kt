package ru.paramonov.vknewsclient

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.paramonov.vknewsclient.ui.theme.VkNewsClientTheme

@Composable
fun InstagramProfileCard(
    model: InstagramModel,
    onFollowButtonClickListener: (InstagramModel) -> Unit
) {
    Card(
        border = BorderStroke(
            width = 2.dp, MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(
            topEnd = 8.dp, topStart = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = R.drawable.insta),
                    contentDescription = null,
                    modifier = Modifier
                        .size(56.dp)
                        .clip(shape = CircleShape)
                        .background(Color.White)
                        .padding(8.dp),
                    contentScale = ContentScale.FillBounds
                )
                UserStatistics(value = "5,663", title = "Posts")
                UserStatistics(value = "600M", title = "Followers")
                UserStatistics(value = "24", title = "Following")
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Instagram ${model.id}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Cursive
            )
            Text(text = "#${model.title}")
            Text(text = "@Nikitosik_123")

            FollowButton(isFollowed = model.isFollowed) {
                onFollowButtonClickListener(model)
            }
        }
    }
}

@Composable
private fun FollowButton(
    isFollowed: Boolean,
    onClickListener: () -> Unit
) {
    Button(
        onClick = { onClickListener() },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isFollowed) {
                MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
            } else {
                MaterialTheme.colorScheme.onPrimary
            },
            contentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        val text = if (isFollowed) "Unfollow" else "Follow"
        Text(text = text)
    }
}

@Composable
fun UserStatistics(
    value: String,
    title: String
) {
    Column(
        modifier = Modifier.height(80.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            fontSize = 20.sp
        )
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}