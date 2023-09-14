package com.guilhermemagro.myplayground.ui.components.circularnavbar.view

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.guilhermemagro.myplayground.ui.components.circularnavbar.model.Tab
import com.guilhermemagro.myplayground.ui.theme.MyPlaygroundTheme

// Stopped at https://youtu.be/4zyuGXTyZ80?si=gZZV3FjtVlp2Zoyt&t=251
@Composable
fun CustomTabBar(
    activeTab: Tab,
    modifier: Modifier = Modifier,
) {

    fun offset(tab: Tab): Dp {
        val totalIndices = Tab.values().size
        val currentIndex = tab.ordinal
        val progress = currentIndex / totalIndices.toFloat()
        return if (progress < 0.5) {
            (currentIndex * -10).dp
        } else {
            ((totalIndices - currentIndex - 1) * -10).dp
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.surface)
            .padding(bottom = 30.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ) {
        Tab.values().forEach {
            Icon(
                imageVector = it.icon,
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .size(30.dp)
                    .offset(y = offset(it)),
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun CustomTabBarPreview() {
    MyPlaygroundTheme {
        CustomTabBar(activeTab = Tab.LIBRARY)
    }
}