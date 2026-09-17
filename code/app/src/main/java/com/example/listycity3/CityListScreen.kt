package com.example.listycity3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onUpdateCity: (City, City) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember { mutableStateOf("") }
    var newProvinceName by remember { mutableStateOf("") }
    var updateCityName by remember { mutableStateOf("") }
    var updateProvinceName by remember { mutableStateOf("") }
    var showAddCityFields by remember { mutableStateOf(false) }
    var showUpdateCityFields by remember { mutableStateOf( false )}
    var selectedCity by remember { mutableStateOf<City?>(null) }


    Column(modifier = modifier) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            FloatingActionButton(
                modifier = Modifier.padding(16.dp),
                onClick = { showAddCityFields = !showAddCityFields }
            ) {
                Text("+")
            }
        }
        if(showAddCityFields) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
            {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = { newProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            onAddCity(
                                City(
                                    name = newCityName,
                                    province = newProvinceName
                                )
                            )

                        }
                        newCityName = ""
                        newProvinceName = ""
                        showAddCityFields = false
                    }
                ) {
                    Text("Add City")
                }
            }
        }

        if(showUpdateCityFields) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            )
            {
                OutlinedTextField(
                    value = updateCityName,
                    onValueChange = { updateCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedTextField(
                    value = updateProvinceName,
                    onValueChange = { updateProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        val oldCity = selectedCity
                        if (oldCity != null && updateCityName.isNotBlank() && updateProvinceName.isNotBlank()) {
                            val updatedCity = City(updateCityName, updateProvinceName)
                            onUpdateCity(oldCity, updatedCity)
                        }
                        updateCityName = ""
                        updateProvinceName = ""
                        showAddCityFields = false
                        println("Update City Clicked")
                    }
                ) {
                    Text("Update City")
                }
            }
        }

        LazyColumn(modifier = modifier
        ) {
            itemsIndexed(cities) { index, city ->
                CityRow(city, onClick = (
                        {
                            selectedCity = city
                            updateCityName = city.name
                            updateProvinceName = city.province
                            showUpdateCityFields = true
                }
                        )
                )
    
                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(cities: City, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = cities.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = cities.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onAddCity = {},
            onUpdateCity = { _, _ ->  },
        )
    }
}