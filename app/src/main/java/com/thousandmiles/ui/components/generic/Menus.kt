package com.thousandmiles.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.thousandmiles.ui.auth.TextFieldWithErrorOption

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> MDropDownMenu(
    label: String,
    value: T,
    isError: Boolean,
    onError: @Composable () -> Unit,
    options: List<T>,
    onValueChange: (T) -> Unit,
    menuItem: @Composable (item: T, onItemSelected:() -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val toggleExpansion = {
        expanded = !expanded
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { toggleExpansion() },
        modifier = modifier
    ) {

        TextFieldWithErrorOption(
            value = value.toString(),
            labelText = label,
            isError = isError,
            onError = onError,
            enabled = false,
            lighterBackground = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = toggleExpansion
        ) {
            options.forEach { selectionOption ->
                menuItem(
                    item = selectionOption,
                    onItemSelected = {
                        onValueChange(selectionOption)
                        toggleExpansion()
                    })
            }
        }
    }
}