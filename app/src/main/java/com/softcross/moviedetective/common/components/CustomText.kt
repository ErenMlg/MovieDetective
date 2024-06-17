package com.softcross.moviedetective.core.common.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.softcross.moviedetective.R

@Composable
fun CustomText(
    text: String,
    fontSize: TextUnit = 12.sp,
    fontFamilyID: Int = R.font.poppins_regular,
    color: Color = Color.DarkGray,
    textAlign: TextAlign = TextAlign.Start,
    line: Int = 1,
    modifier: Modifier
) {
    Text(
        text = text,
        fontSize = fontSize,
        fontFamily = FontFamily(Font(fontFamilyID)),
        textAlign = textAlign,
        maxLines = line,
        color = color,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier,
    )
}