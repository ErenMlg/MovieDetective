package com.softcross.moviedetective.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.moviedetective.R
import com.softcross.moviedetective.core.common.components.CustomAsyncImage
import com.softcross.moviedetective.core.common.components.CustomText
import com.softcross.moviedetective.core.common.extensions.calculateRemainingDays
import com.softcross.moviedetective.core.common.extensions.convertToFormattedDate
import com.softcross.moviedetective.domain.model.Movie

@Composable
fun ComingContentItem(movie: Movie) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .shadow(8.dp, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
            .width(160.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box {
            CustomAsyncImage(
                model = movie.movieImage,
                contentDescription = movie.movieName,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                    .clip(MaterialTheme.shapes.small)
                    .height(212.dp)
            )
            Card(modifier = Modifier.padding(16.dp)) {
                CustomText(
                    text = "${calculateRemainingDays(movie.releaseDate)} days",
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
            }
        }
        CustomText(
            text = movie.movieName,
            modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
            textAlign = TextAlign.Center,
            fontFamilyID = R.font.poppins_medium,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.size(16.dp))
        CustomText(
            text = movie.releaseDate.convertToFormattedDate(),
            modifier = Modifier.padding(bottom = 8.dp, start = 8.dp),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun ComingContentItemPreview() {
    MaterialTheme {
        ComingContentItem(
            Movie(
                movieID = 6593,
                movieName = "Simone Alexander",
                description = "nec",
                genres = listOf(),
                imdb = 2.3f,
                releaseDate = "2024-12-08",
                movieImage = "qui"
            )
        )
    }
}