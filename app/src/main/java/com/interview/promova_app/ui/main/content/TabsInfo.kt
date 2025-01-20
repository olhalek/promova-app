package com.interview.promova_app.ui.main.content

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

enum class FilterSegmentedButtons(@StringRes val strRes: Int,) {
    NONE(R.string.none_button),
    VOTE7(R.string.vote_7_button),
    COUNT100(R.string.vote_count_button)
}