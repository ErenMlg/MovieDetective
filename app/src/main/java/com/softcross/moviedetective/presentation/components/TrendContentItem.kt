package com.softcross.moviedetective.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.moviedetective.R
import com.softcross.moviedetective.core.common.components.CustomAsyncImage
import com.softcross.moviedetective.core.common.components.CustomText
import com.softcross.moviedetective.core.common.extensions.convertToFormattedYear
import com.softcross.moviedetective.domain.model.Movie


@Composable
fun TrendContentItem(movie: Movie) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .shadow(8.dp, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
            .width(160.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        CustomAsyncImage(
            model = movie.movieImage,
            contentDescription = "",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                .clip(MaterialTheme.shapes.small)
                .height(212.dp)
        )
        CustomText(
            text = movie.movieName,
            fontFamilyID = R.font.poppins_medium,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 8.dp, start = 8.dp, end = 8.dp).fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.icon_star),
                contentDescription = "Star",
                contentScale = ContentScale.Inside,
                modifier = Modifier
                    .size(24.dp)
                    .weight(0.2f)
                    .padding(start = 8.dp)
            )
            CustomText(
                text = "%.2f".format(movie.imdb),
                fontFamilyID = R.font.poppins_semi_bold,
                modifier = Modifier
                    .weight(0.3f)
                    .padding(start = 2.dp)
            )
            CustomText(
                text = movie.releaseDate.convertToFormattedYear(),
                textAlign = TextAlign.End,
                color = Color.Gray,
                modifier = Modifier
                    .weight(0.6f)
                    .padding(end = 8.dp)
            )
        }
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun TrendContentItemPreview() {
    MaterialTheme {
        TrendContentItem(
            Movie(
                movieID = 8778,
                movieName = "Adrian Parker",
                description = "urna",
                genres = listOf(),
                imdb = 2.3f,
                releaseDate = "2024-12-12",
                movieImage = "utroque"
            )
        )
    }
}