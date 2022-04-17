package com.example.contacts.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.contacts.R

@Composable
fun MyErrorDialog(
    @StringRes title: Int = R.string.error,
    @StringRes message: Int,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .width(300.dp)
                .height(200.dp),
            shape = RoundedCornerShape(5)
        ) {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (titleView, messageView, buttons) = createRefs()
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 20.dp)
                        .constrainAs(titleView) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        },
                    text = stringResource(title),
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.error
                )
                Text(
                    modifier = Modifier
                        .padding(start = 20.dp, top = 10.dp)
                        .constrainAs(messageView) {
                            top.linkTo(titleView.bottom)
                            start.linkTo(parent.start)
                        },
                    text = stringResource(message),
                    style = MaterialTheme.typography.subtitle2,
                    color = MaterialTheme.colors.onSurface
                )
                Row(
                    modifier = Modifier
                        .padding(bottom = 15.dp, end = 15.dp)
                        .constrainAs(buttons) {
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                ) {
                    Column{
                        OutlinedButton(
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colors.error),
                            onClick = onDismiss
                        ) {
                            Text(stringResource(R.string.event_dialog_accept_option))
                        }
                    }
                }
            }
        }
    }
}