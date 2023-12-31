package ru.paramonov.vknewsclient.presentation.screens.profile

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import ru.paramonov.vknewsclient.R
import ru.paramonov.vknewsclient.domain.entity.Profile
import ru.paramonov.vknewsclient.domain.entity.WallPost
import ru.paramonov.vknewsclient.presentation.application.getApplicationComponent
import ru.paramonov.vknewsclient.presentation.ui.theme.VkDefault

@Composable
fun ProfileScreen(
    paddingValues: PaddingValues,
) {
    val component = getApplicationComponent()
    val viewModel: ProfileViewModel = viewModel(factory = component.getViewModelFactory())
    val viewState = viewModel.viewState.collectAsState(initial = ProfileViewState.Initial)

    ProfileScreenContent(
        paddingValues = paddingValues,
        viewState = viewState,
        viewModel = viewModel
    )
}

@Composable
fun ProfileScreenContent(
    paddingValues: PaddingValues,
    viewState: State<ProfileViewState>,
    viewModel: ProfileViewModel
) {
    when (val currentState = viewState.value) {
        is ProfileViewState.ProfileContent -> {
            ProfileListItem(
                paddingValues = paddingValues,
                profile = currentState.profile,
                posts = currentState.wallPosts,
                viewModel = viewModel
            )
        }

        is ProfileViewState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = VkDefault)
            }
        }

        is ProfileViewState.Initial -> {}
    }
}

@Composable
private fun ProfileListItem(
    paddingValues: PaddingValues,
    profile: Profile,
    posts: List<WallPost>,
    viewModel: ProfileViewModel
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp, bottom = 8.dp,
            start = 4.dp, end = 4.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = stringResource(id = R.string.navigation_item_profile),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }
        item {
            ProfileCard(profile = profile)
        }
        item {
            CardFullInformation(profile = profile)
        }
        item {
            Text(
                text = stringResource(R.string.all_posts),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp)
            )
        }
        items(
            items = posts,
            key = { it.id }
        ) { wallPost ->
            ProfilePostCard(
                wallPost = wallPost,
                onCommentsClickListener = {},
                onLikesClickListener = {
                    viewModel.changeLikeStatus(wallPost = wallPost)
                }
            )
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 16.dp, bottom = 8.dp, end = 16.dp, start = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = numberOfPosts(posts.size),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    }
}

private fun numberOfPosts(number: Int): String {
    return when (number) {
        1 -> {
            "$number запись"
        }

        in 2..4 -> {
            "$number записи"
        }

        else -> {
            "$number записей"
        }
    }
}

@Composable
private fun ProfileCard(
    profile: Profile
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(size = 20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            AsyncImageAnimation(url = profile.profilePhoto)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = profile.fullName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
private fun AsyncImageAnimation(
    url: String
) {
    var isNotCircle by remember {
        mutableStateOf(false)
    }

    val percent by animateIntAsState(
        targetValue = if (!isNotCircle) 50 else 4,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        ),
        label = "Circle"
    )

    val size by animateDpAsState(
        targetValue = if (!isNotCircle) 150.dp else 300.dp,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        ),
        label = "Size"
    )

    AsyncImage(
        model = url,
        contentDescription = null,
        modifier = Modifier
            .size(size = size)
            .clip(shape = RoundedCornerShape(percent = percent))
            .clickable { isNotCircle = !isNotCircle }
    )
}

@Composable
private fun CardFullInformation(
    profile: Profile
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(size = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = stringResource(R.string.full_information),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(16.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Information(
                icon = Icons.Outlined.AccountCircle,
                label = stringResource(R.string.name_profile),
                value = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        append(text = profile.screenName)
                    }
                }
            )
            Information(
                icon = Icons.Outlined.DateRange,
                label = stringResource(R.string.b_date),
                value = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = VkDefault
                        )
                    ) {
                        append(text = profile.bDate)
                    }
                }
            )
            Information(
                icon = Icons.Outlined.LocationOn,
                label = stringResource(id = R.string.main_town),
                value = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        append(text = profile.homeTown)
                    }
                },
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Composable
private fun Information(
    icon: ImageVector,
    label: String,
    value: AnnotatedString,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = modifier
        )
    }
}
