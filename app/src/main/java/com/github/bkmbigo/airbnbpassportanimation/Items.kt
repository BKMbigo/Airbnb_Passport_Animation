package com.github.bkmbigo.airbnbpassportanimation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.WorkOutline

val infoItems = listOf(
    InfoItem(
        icon = Icons.Filled.Lightbulb,
        text = "Fun fact: I lived in 6 different countries"
    ),
    InfoItem(
        icon = Icons.Default.Fastfood,
        text = "Favorite breakfast: burgers",
    ),
    InfoItem(
        icon = Icons.Default.Pets,
        text = "Pets: two dogs",
    ),
    InfoItem(
        icon = Icons.Default.Language,
        text = "Speaks: English, Arabic, French",
    ),
    InfoItem(
        icon = Icons.Default.FavoriteBorder,
        text = "I'm obsessed with: Travelling & tennis",
    ),
    InfoItem(
        icon = Icons.Default.Cake,
        text = "Born: 1980",
    ),
    InfoItem(
        icon = Icons.Default.Book,
        text = "Bio: Loves to travel",
    ),
    InfoItem(
        icon = Icons.Default.WorkOutline,
        text = "Occupation: Graphic Designer",
    )
)

val listings = listOf(
    Listing(
        id = 1,
        coverUrl = R.drawable.listing_1,
        landlordAvatar = R.drawable.person_1,
        landlordName = "Jane",
        title = "Bright apartment in vibrant neighborhood",
        address = "Berlin, Germany",
        availability = "May 9 - 14",
        rating = 4.79f,
        reviewsCount = 200,
        price = 98.00f,
        infoItems = infoItems,
        landlordDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas odio velit, gravida in tincidunt eget, suscipit et libero. Suspendisse potenti. Curabitur quis quam sodales ante faucibus luctus. Mauris sit amet est eu mauris tristique porttitor."
    ),
    Listing(
        id = 2,
        coverUrl = R.drawable.listing_2,
        landlordAvatar = R.drawable.person_3,
        landlordName = "Jane",
        title = "Bright apartment in vibrant neighborhood",
        address = "Berlin, Germany",
        availability = "May 9 - 14",
        rating = 4.79f,
        reviewsCount = 200,
        price = 98.00f,
        infoItems = infoItems,
        landlordDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas odio velit, gravida in tincidunt eget, suscipit et libero. Suspendisse potenti. Curabitur quis quam sodales ante faucibus luctus. Mauris sit amet est eu mauris tristique porttitor."
    ),
    Listing(
        id = 3,
        coverUrl = R.drawable.listing_3,
        landlordAvatar = R.drawable.person_1,
        landlordName = "Jane",
        title = "Bright apartment in vibrant neighborhood",
        address = "Berlin, Germany",
        availability = "May 9 - 14",
        rating = 4.79f,
        reviewsCount = 200,
        price = 98.00f,
        infoItems = infoItems,
        landlordDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas odio velit, gravida in tincidunt eget, suscipit et libero. Suspendisse potenti. Curabitur quis quam sodales ante faucibus luctus. Mauris sit amet est eu mauris tristique porttitor."
    ),
    Listing(
        id = 4,
        coverUrl = R.drawable.listing_4,
        landlordAvatar = R.drawable.person_2,
        landlordName = "Jane",
        title = "Bright apartment in vibrant neighborhood",
        address = "Berlin, Germany",
        availability = "May 9 - 14",
        rating = 4.79f,
        reviewsCount = 200,
        price = 98.00f,
        infoItems = infoItems,
        landlordDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas odio velit, gravida in tincidunt eget, suscipit et libero. Suspendisse potenti. Curabitur quis quam sodales ante faucibus luctus. Mauris sit amet est eu mauris tristique porttitor."
    ),
    Listing(
        id = 5,
        coverUrl = R.drawable.listing_5,
        landlordAvatar = R.drawable.person_3,
        landlordName = "Jane",
        title = "Bright apartment in vibrant neighborhood",
        address = "Berlin, Germany",
        availability = "May 9 - 14",
        rating = 4.79f,
        reviewsCount = 200,
        price = 98.00f,
        infoItems = infoItems,
        landlordDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas odio velit, gravida in tincidunt eget, suscipit et libero. Suspendisse potenti. Curabitur quis quam sodales ante faucibus luctus. Mauris sit amet est eu mauris tristique porttitor."
    ),
)