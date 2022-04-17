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
fun MyOptionDialog(
    @StringRes title: Int = R.string.option_dialog_title,
    @StringRes message: Int,
    @StringRes dismissLabel: Int = R.string.option_dialog_dismiss,
    @StringRes acceptLabel: Int = R.string.option_dialog_accept,
    onDismiss: () -> Unit,
    onAccept: () -> Unit
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
                    color = MaterialTheme.colors.primary
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
                            onClick = onDismiss
                        ) {
                            Text(stringResource(dismissLabel))
                        }
                    }
                    Column {
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                    Column{
                        Button(
                            onClick = onAccept
                        ) {
                            Text(stringResource(acceptLabel))
                        }
                    }
                }
            }

        }
    }
}