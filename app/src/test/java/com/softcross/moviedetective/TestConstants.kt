package com.softcross.moviedetective

import androidx.annotation.VisibleForTesting
import com.softcross.moviedetective.data.dto.MovieDetailDto
import com.softcross.moviedetective.data.dto.genre.GenreDto
import com.softcross.moviedetective.data.dto.movies.MovieDto
import com.softcross.moviedetective.data.dto.movies.MoviesResponse
import com.softcross.moviedetective.domain.model.Movie

/**
 * FOR API TESTING
 */
@VisibleForTesting
const val SERVER_PORT = 8000

const val TOP_20_MOVIE_FILE_NAME = "Top20MovieResponse.json"
const val TREND_MOVIE_FILE_NAME = "TrendMoviesResponse.json"
const val COMING_SOON_FILE_NAME = "ComingSoonMovies.json"
const val SINGLE_MOVIE_FILE_NAME = "SingleMovieResponse.json"

const val SINGLE_MOVIE_ID = 653346

@VisibleForTesting
val firstItemTopMovies = MovieDto(
    id = 278,
    title = "The Shawshank Redemption",
    voteAverage = 8.706f,
    genresID = listOf(18, 80),
    image = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg",
    releaseDate = "1994-09-23",
    overview = "Imprisoned in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
    runtime = null
)

@VisibleForTesting
val firstItemTrendMovies = MovieDto(
    id = 653346,
    title = "Kingdom of the Planet of the Apes",
    voteAverage = 7f,
    genresID = listOf(878, 12, 28),
    image = "/gKkl37BQuKTanygYQG1pyYgLVgf.jpg",
    releaseDate = "2024-05-08",
    overview = "Several generations in the future following Caesar's reign, apes are now the dominant species and live harmoniously while humans have been reduced to living in the shadows. As a new tyrannical ape leader builds his empire, one young ape undertakes a harrowing journey that will cause him to question all that he has known about the past and to make choices that will define a future for apes and humans alike.",
    runtime = null
)

@VisibleForTesting
val firstItemComingMovies = MovieDto(
    id = 1022789,
    title = "Inside Out 2",
    voteAverage = 0f,
    genresID = listOf(16, 10751, 18, 12, 14),
    image = "/gMB8vgHu2lcyzv1fyptD6drHmJd.jpg",
    releaseDate = "2024-06-14",
    overview = "Teenager Riley's mind headquarters is undergoing a sudden demolition to make room for something entirely unexpected: new Emotions! Joy, Sadness, Anger, Fear and Disgust, who’ve long been running a successful operation by all accounts, aren’t sure how to feel when Anxiety shows up. And it looks like she’s not alone.",
    runtime = null
)

@VisibleForTesting
val singleMovie = MovieDetailDto(
    id = 653346,
    title = "Kingdom of the Planet of the Apes",
    voteAverage = 7f,
    genres = listOf(
        GenreDto(878, "Science Fiction"),
        GenreDto(12, "Adventure"),
        GenreDto(28, "Action"),
    ),
    image = "/gKkl37BQuKTanygYQG1pyYgLVgf.jpg",
    releaseDate = "2024-05-08",
    overview = "Several generations in the future following Caesar's reign, apes are now the dominant species and live harmoniously while humans have been reduced to living in the shadows. As a new tyrannical ape leader builds his empire, one young ape undertakes a harrowing journey that will cause him to question all that he has known about the past and to make choices that will define a future for apes and humans alike.",
    runtime = 145
)

/**
 * FOR DATA SOURCE TESTING
 */
val moviesResult = MoviesResponse(
    page = 5875,
    data = listOf()
)

val singleMovieResult = MovieDetailDto(
    id = 9470,
    title = "nisi",
    voteAverage = 2.3f,
    genres = listOf(),
    image = null,
    releaseDate = "ornare",
    overview = "eleifend",
    runtime = null
)

/**
 * FOR REPOSITORY TESTING
 */
@VisibleForTesting
val mappedTopMovieListFirstItem = Movie(
    movieID = 278,
    movieName = "The Shawshank Redemption",
    description = "Imprisoned in the 1940s for the double murder of his wife and her lover, upstanding banker Andy Dufresne begins a new life at the Shawshank prison, where he puts his accounting skills to work for an amoral warden. During his long stretch in prison, Dufresne comes to be admired by the other inmates -- including an older prisoner named Red -- for his integrity and unquenchable sense of hope.",
    genres = listOf(18, 80),
    imdb = 8.706f,
    releaseDate = "1994-09-23",
    movieImage = "/9cqNxx0GxF0bflZmeSMuL5tnGzr.jpg"
)

@VisibleForTesting
val mappedTrendMovieListFirstItem = Movie(
    movieID = 653346,
    movieName = "Kingdom of the Planet of the Apes",
    description = "Several generations in the future following Caesar's reign, apes are now the dominant species and live harmoniously while humans have been reduced to living in the shadows. As a new tyrannical ape leader builds his empire, one young ape undertakes a harrowing journey that will cause him to question all that he has known about the past and to make choices that will define a future for apes and humans alike.",
    genres = listOf(878, 12, 28),
    imdb = 7f,
    releaseDate = "2024-05-08",
    movieImage = "/gKkl37BQuKTanygYQG1pyYgLVgf.jpg"
)

@VisibleForTesting
val mappedComingMovieListFirstItem = Movie(
    movieID = 1022789,
    movieName = "Inside Out 2",
    description = "Teenager Riley's mind headquarters is undergoing a sudden demolition to make room for something entirely unexpected: new Emotions! Joy, Sadness, Anger, Fear and Disgust, who’ve long been running a successful operation by all accounts, aren’t sure how to feel when Anxiety shows up. And it looks like she’s not alone.",
    genres = listOf(16, 10751, 18, 12, 14),
    imdb = 0f,
    releaseDate = "2024-06-14",
    movieImage = "/gMB8vgHu2lcyzv1fyptD6drHmJd.jpg"
)

@VisibleForTesting
val mappedSingleMovie = Movie(
    movieID = 653346,
    movieName = "Kingdom of the Planet of the Apes",
    description = "Several generations in the future following Caesar's reign, apes are now the dominant species and live harmoniously while humans have been reduced to living in the shadows. As a new tyrannical ape leader builds his empire, one young ape undertakes a harrowing journey that will cause him to question all that he has known about the past and to make choices that will define a future for apes and humans alike.",
    genres = listOf(878,12,28),
    imdb = 7f,
    releaseDate = "2024-05-08",
    movieImage = "/gKkl37BQuKTanygYQG1pyYgLVgf.jpg"
)
