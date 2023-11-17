package com.sergio.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sergio.common.theme.PaddingRules
import com.sergio.home.R
import com.sergio.home.state.HomeViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    ModalBottomSheetLayout(
        sheetContent = {

        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingRules.all),
            shape = ShapeDefaults.Small,
            color = MaterialTheme.colorScheme.onPrimary
        ) {
            Column {
                Text(text = stringResource(id = R.string.pending_tasks))
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = stringResource(id = R.string.pending_tasks))
                Text(text = stringResource(id = R.string.pending_tasks))
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}