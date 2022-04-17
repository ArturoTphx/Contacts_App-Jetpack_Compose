package com.example.contacts.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.contacts.domain.contacts.model.Contact
import com.example.contacts.ui.components.MyErrorDialog
import com.example.contacts.ui.components.MyInfoDialog
import com.example.contacts.ui.main.interaction.HomeEvent
import com.example.contacts.ui.main.view.Adding
import com.example.contacts.ui.main.view.Consulting
import com.example.contacts.ui.main.view.Updating
import com.example.contacts.ui.main.viewModel.MainViewModel
import com.example.contacts.ui.theme.ContactsTheme
import com.example.contacts.utilities.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel = hiltViewModel()
            ContactsTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    when (viewModel.state.value.screen) {
                        Constants.consulting -> {
                            Consulting(
                                viewModel = viewModel,
                                onUpdate = { sId, sName, sLastOne, sLastTwo, sNumber ->
                                    viewModel.onEvent(
                                        HomeEvent.ToUpdate(
                                            Contact(sId, sName, sLastOne, sLastTwo, sNumber)
                                        )
                                    )
                                }
                            )
                        }
                        Constants.adding -> {
                            Adding(viewModel = viewModel)
                        }
                        Constants.updating -> {
                            Updating(viewModel = viewModel)
                        }
                    }
                    if (viewModel.state.value.info != null) {
                        MyInfoDialog(
                            message = viewModel.state.value.info!!,
                            onDismiss = { viewModel.onEvent(HomeEvent.ToHideInfo) })
                    }
                    if (viewModel.state.value.error != null) {
                        MyErrorDialog(
                            message = viewModel.state.value.error!!,
                            onDismiss = { viewModel.onEvent(HomeEvent.ToHideError) })
                    }

                }
            }
        }
    }

}