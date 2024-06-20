package com.softcross.moviedetective.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.softcross.moviedetective.domain.model.Actor


@Composable
fun PopularPeopleItem(people: Actor) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .shadow(8.dp, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
            .width(160.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        CustomAsyncImage(
            model = people.image,
            contentDescription = people.name,
            alignment = Alignment.CenterStart,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                .clip(MaterialTheme.shapes.small)
                .height(212.dp)
        )
        CustomText(
            text = people.name,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, bottom = 8.dp, end = 8.dp),
            fontSize = 14.sp,
            fontFamilyID = R.font.poppins_medium,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun PopularPeopleItemPreview() {
    MaterialTheme {
        PopularPeopleItem(Actor(id = 6815, name = "Perry Jones", gender = 1, image = "iisque"))
    }
}