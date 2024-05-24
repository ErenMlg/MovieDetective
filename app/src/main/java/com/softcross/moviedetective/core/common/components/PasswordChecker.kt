package com.softcross.moviedetective.core.common.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.moviedetective.R
import com.softcross.moviedetective.presentation.theme.MovieDetectiveTheme

@Composable
fun PasswordChecker(
    password: String
) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.password_error_title),
            fontFamily = FontFamily(Font(R.font.poppins_regular)),
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.error
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (password.length >= 8) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.correct),
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(12.dp)
                )
            } else {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.wrong),
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.password_error_one),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if ("([0-9])".toRegex().containsMatchIn(password)) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.correct),
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(12.dp)
                )
            } else {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.wrong),
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.password_error_two),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if ("([a-z])".toRegex().containsMatchIn(password)) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.correct),
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(12.dp)
                )
            } else {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.wrong),
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.password_error_three),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if ("([A-Z])".toRegex().containsMatchIn(password)) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = stringResource(id = R.string.correct),
                    tint = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.size(12.dp)
                )
            } else {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = stringResource(id = R.string.wrong),
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(12.dp)
                )
            }
            Text(
                text = stringResource(id = R.string.password_error_four),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordCheckerPreview() {
    MovieDetectiveTheme {
        PasswordChecker(password = "SKsgfkdhgj")
    }
}