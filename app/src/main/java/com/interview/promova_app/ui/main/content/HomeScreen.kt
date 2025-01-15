package com.interview.promova_app.ui.main.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.interview.promova_app.R
import com.interview.promova_app.ui.main.viewModel.HomeViewModel
import com.interview.promova_app.ui.theme.PromovaTheme
import com.interview.promova_app.ui.theme.PromovaTypography

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val res = viewModel.res.value
    if (res.isLoading) {
        LoadingContent()
    }

    if (res.error.isNotEmpty()) {
        ErrorContent()
    }

    if (res.data.isNotEmpty()) {
        MainContent()
    }
}

@Preview
@Composable
private fun LoadingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.loading),
            style = PromovaTypography.labelLarge
        )
    }
}

@Preview
@Composable
fun ErrorContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.ic_error),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.loading),
            style = PromovaTypography.labelLarge
        )
    }
}

@Composable
private fun MainContent() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            tabs = {
                TabsContent(
                    selectedTabIndex = selectedTabIndex,
                    onTabClicked = { selectedTabIndex = it }
                )
            }
        )
    }
}

@Composable
private fun TabsContent(selectedTabIndex: Int, onTabClicked: (Int) -> Unit) {
    TabsInfo.entries.forEachIndexed { index, tab ->
        val isSelected = selectedTabIndex == index
        Tab(
            selected = isSelected,
            onClick = { onTabClicked(index) },
            text = {
                Text(text = stringResource(id = tab.strRes))
            },
            icon = {
                Icon(
                    modifier = Modifier.size(26.dp),
                    painter = painterResource(id = if (isSelected) tab.iconResFilled else tab.iconRes),
                    contentDescription = null
                )
            }
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    PromovaTheme {
        HomeScreen()
    }
}