package com.interview.promova_app.ui.main.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.interview.promova_app.R

enum class TabsInfo(
    @StringRes val strRes: Int,
    @DrawableRes val iconRes: Int,
    @DrawableRes val iconResFilled: Int
) {
    ALL(
        strRes = R.string.all_movies,
        iconRes = R.drawable.ic_all_movies,
        iconResFilled = R.drawable.ic_all_movies_filled
    ),
    FAVOURITE(
        strRes = R.string.favourite_movies,
        iconRes = R.drawable.ic_heart,
        iconResFilled = R.drawable.ic_heart_filled
    )
}