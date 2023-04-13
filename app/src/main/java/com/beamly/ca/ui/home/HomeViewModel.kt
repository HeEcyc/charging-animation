package com.beamly.ca.ui.home

import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.beamly.ca.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel : BaseViewModel() {

    val date = ObservableField<String>()
    val time = ObservableField<String>()

    private val locale = Locale.getDefault()

    init {
        viewModelScope.launch {
            while (true) {
                val cal = Calendar.getInstance()
                val dayOfWeek = cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, locale)
                    ?.mapIndexed { i, c -> if (i > 0) "$c" else c.uppercase() }
                    ?.joinToString("")
                val month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, locale)
                    ?.mapIndexed { i, c -> if (i > 0) "$c" else c.uppercase() }
                    ?.joinToString("")
                val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)
                val year = cal.get(Calendar.YEAR)
                val hour = cal.get(Calendar.HOUR_OF_DAY).let { "$it".takeIf { it.length == 2 } ?: "0$it" }
                val minute = cal.get(Calendar.MINUTE).let { "$it".takeIf { it.length == 2 } ?: "0$it" }
                date.set("$dayOfWeek, $month $dayOfMonth, $year")
                time.set("$hour:$minute")
                delay(100)
            }
        }
    }

}