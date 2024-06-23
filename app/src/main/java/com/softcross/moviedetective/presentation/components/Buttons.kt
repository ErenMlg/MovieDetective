package com.softcross.moviedetective.presentation.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.moviedetective.R

@Composable
fun LoadingTextButton(
    isLoading: Boolean,
    isEnable: Boolean,
    onClick: () -> Unit,
    @StringRes buttonText: Int
) {
    Button(
        enabled = isEnable,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(50.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(4.dp)
                    .aspectRatio(1f)
            )
        } else {
            Text(
                text = stringResource(id = buttonText),
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                fontSize = 14.sp
            )
        }
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ButtonsPreview() {
    MaterialTheme {
        LoadingTextButton(
            isLoading = false,
            isEnable = true,
            onClick = { /*TODO*/ },
            buttonText = R.string.sing_up
        )
    }
}