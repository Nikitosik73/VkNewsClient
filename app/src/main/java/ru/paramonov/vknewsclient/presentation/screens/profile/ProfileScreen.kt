package ru.paramonov.vknewsclient.presentation.screens.profile

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
        viewState = viewState
    )
}

@Composable
fun ProfileScreenContent(
    paddingValues: PaddingValues,
    viewState: State<ProfileViewState>
) {
    when(val currentState = viewState.value) {
        is ProfileViewState.ProfileContent -> {
            ProfileListItem(
                paddingValues = paddingValues,
                profile = currentState.profile
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
    profile: Profile
) {
    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        contentPadding = PaddingValues(
            top = 16.dp, bottom = 16.dp,
            start = 2.dp, end = 2.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ProfileCard(profile = profile)
        }
        item {
            CardFullInformation(profile = profile)
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
            AsyncImage(
                model = profile.profilePhoto,
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(shape = CircleShape)
            )
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
                }
            )
        }
    }
}

@Composable
private fun Information(
    icon: ImageVector,
    label: String,
    value: AnnotatedString
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
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
