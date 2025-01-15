package com.interview.promova_app.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.interview.promova_app.ui.theme.PromovaTheme

@Composable
fun HomeScreen() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            tabs = {
                Tabs(
                    selectedTabIndex = selectedTabIndex,
                    onTabClicked = { selectedTabIndex = it}
                )
            }
        )
    }
}

@Composable
private fun Tabs(selectedTabIndex: Int, onTabClicked:(Int) -> Unit,) {
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