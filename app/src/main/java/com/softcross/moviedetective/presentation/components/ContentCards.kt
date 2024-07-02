package com.softcross.moviedetective.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.moviedetective.R
import com.softcross.moviedetective.common.extensions.calculateRemainingDays
import com.softcross.moviedetective.common.extensions.clickableWithoutIndicator
import com.softcross.moviedetective.common.extensions.convertToFormattedDate
import com.softcross.moviedetective.common.extensions.convertToFormattedYear
import com.softcross.moviedetective.domain.model.Actor
import com.softcross.moviedetective.domain.model.Content
import com.softcross.moviedetective.presentation.theme.MovieDetectiveTheme

@Composable
fun PagerContentItem(
    content: Content,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit
) {
    Box(
        modifier = modifier
            .padding(bottom = 24.dp)
            .width(400.dp)
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier
                .clickableWithoutIndicator { onClick(content.id) }
                .align(alignment = Alignment.BottomEnd)
                .height(125.dp)
                .width(300.dp)
                .padding(end = 16.dp)
                .shadow(8.dp, MaterialTheme.shapes.small)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(start = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomText(
                    text = content.title,
                    fontFamilyID = R.font.poppins_semi_bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f)
                        .padding(end = 4.dp, start = 16.dp, top = 8.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_star),
                    contentDescription = "",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(24.dp)
                        .align(alignment = Alignment.CenterVertically)
                        .padding(top = 8.dp)
                )
                CustomText(
                    text = "%.2f".format(content.imdb),
                    fontFamilyID = R.font.poppins_semi_bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(end = 8.dp, top = 8.dp)
                )
            }
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(content.genres) {
                    GenreItem(it)
                }
            }
            CustomText(
                text = content.description,
                fontFamilyID = R.font.poppins_medium,
                fontSize = 10.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(end = 8.dp, start = 16.dp)
            )
            CustomText(
                text = content.releaseDate.convertToFormattedDate(),
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }
        CustomAsyncImage(
            model = content.image,
            contentDescription = "",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(256.dp)
                .width(145.dp)
                .padding(16.dp)
                .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
    }
}

@Composable
fun ComingContentItem(content: Content, onClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .clickableWithoutIndicator { onClick(content.id) }
            .padding(8.dp)
            .shadow(8.dp, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
            .width(160.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box {
            CustomAsyncImage(
                model = content.image,
                contentDescription = content.title,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                    .clip(MaterialTheme.shapes.small)
                    .height(212.dp)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            )
            Card(modifier = Modifier.padding(16.dp)) {
                val remainingDays = calculateRemainingDays(content.releaseDate).toInt()
                CustomText(
                    text = if (remainingDays == 0) "Released" else if (remainingDays == -1) "N/A" else "$remainingDays days",
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
            }
        }
        CustomText(
            text = content.title,
            modifier = Modifier.padding(start = 12.dp, end = 8.dp),
            textAlign = TextAlign.Center,
            fontFamilyID = R.font.poppins_medium,
            fontSize = 14.sp,
        )
        Spacer(modifier = Modifier.size(16.dp))
        CustomText(
            text = content.releaseDate.convertToFormattedDate(),
            modifier = Modifier.padding(bottom = 8.dp, start = 12.dp),
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun ContentItem(content: Content, onClick: (Int) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickableWithoutIndicator { onClick(content.id) }
            .padding(8.dp)
            .shadow(8.dp, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
            .width(160.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        CardHeader(image = content.image, title = content.title)
        Spacer(modifier = Modifier.size(16.dp))
        CardBottom(point = content.imdb, releaseDate = content.releaseDate)
    }
}

@Composable
fun ContentItemWithGenres(content: Content, onClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .clickableWithoutIndicator { onClick(content.id) }
            .padding(8.dp)
            .shadow(8.dp, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
            .width(160.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        CardHeader(image = content.image, title = content.title)
        LazyRow(modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)) {
            items(
                content.genres,
            ) {
                GenreItem(genreID = it)
            }
        }
        CardBottom(point = content.imdb, releaseDate = content.releaseDate)
    }
}

@Composable
fun ActorItem(people: Actor, onClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .clickableWithoutIndicator { onClick(people.id) }
            .padding(8.dp)
            .shadow(8.dp, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
            .width(160.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        CardHeader(image = people.image, title = people.name)
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Composable
fun ActorItemWithCharacter(people: Actor, onClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .clickableWithoutIndicator { onClick(people.id) }
            .padding(8.dp)
            .shadow(8.dp, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
            .width(160.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        CardHeader(image = people.image, title = people.name)
        Spacer(modifier = Modifier.size(16.dp))
        CustomText(
            text = people.character,
            modifier = Modifier.padding(start = 12.dp, bottom = 8.dp, end = 12.dp),
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun CardHeader(image: String, title: String) {
    CustomAsyncImage(
        model = image,
        contentDescription = "",
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
            .fillMaxWidth()
            .height(212.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    )
    CustomText(
        text = title,
        fontFamilyID = R.font.poppins_medium,
        textAlign = TextAlign.Start,
        fontSize = 14.sp,
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp)
            .fillMaxWidth()
    )
}

@Composable
fun CardBottom(point: Float, releaseDate: String) {
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
                .padding(start = 12.dp)
        )
        CustomText(
            text = "%.2f".format(point),
            fontFamilyID = R.font.poppins_semi_bold,
            modifier = Modifier
                .weight(0.3f)
                .padding(start = 4.dp)
        )
        CustomText(
            text = releaseDate.convertToFormattedYear(),
            textAlign = TextAlign.End,
            color = Color.Gray,
            modifier = Modifier
                .weight(0.6f)
                .padding(end = 12.dp)
        )
    }
}

@Composable
fun TitleRow(title: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomText(
            text = title,
            fontSize = 22.sp,
            color = MaterialTheme.colorScheme.primary,
            fontFamilyID = R.font.poppins_semi_bold,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .weight(0.8f)
        )
        CustomText(
            text = "View more",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 8.dp)
                .weight(0.3f)
        )
        Image(
            painter = painterResource(id = R.drawable.icon_left_arrow),
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            modifier = Modifier
                .padding(top = 16.dp, end = 16.dp)
                .size(12.dp)
                .weight(0.1f)
        )
    }
}

@Composable
fun TitleRowWithSubtitle(title: String, subTitle: String, onClick: () -> Unit) {
    CustomText(
        text = title,
        fontFamilyID = R.font.poppins_semi_bold,
        fontSize = 24.sp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 16.dp)
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        CustomText(
            text = subTitle,
            line = 2,
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp)
                .weight(0.6f)
        )
        CustomText(
            text = "View more",
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 8.dp)
                .weight(0.3f)
                .clickableWithoutIndicator { onClick() }
        )
        Image(
            painter = painterResource(id = R.drawable.icon_left_arrow),
            contentDescription = "",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.secondary),
            modifier = Modifier
                .padding(top = 8.dp, end = 16.dp)
                .size(12.dp)
                .weight(0.1f)
                .clickableWithoutIndicator { onClick() }
        )
    }
}

@Preview
@Composable
fun CardsPreview() {
    val content = Content(
        id = 5539,
        title = "Royce Rosa",
        description = "commune",
        genres = listOf(12, 23, 4),
        imdb = 2.3f,
        releaseDate = "2024-12-02",
        image = "equidem"
    )
    val actor = Actor(
        id = 4413,
        name = "Abigail Hahn",
        gender = 1512,
        image = "tamquam",
        character = "iriure"
    )
    MovieDetectiveTheme {
        Column {
            TitleRowWithSubtitle(title = "pellentesque", subTitle = "consectetur", onClick = {})
            LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
                item {
                    ComingContentItem(content = content, onClick = {})
                }
                item {
                    ContentItem(content = content, onClick = {})
                }
                item {
                    ContentItemWithGenres(content = content, onClick = {})
                }
                item {
                    ActorItem(people = actor, {})
                }
                item {
                    ActorItemWithCharacter(people = actor, onClick = {})
                }

            }
        }
    }
}