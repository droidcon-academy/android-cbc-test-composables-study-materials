package com.droidcon.testingcomposables

import android.annotation.SuppressLint
import android.nfc.Tag
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.NumberFormatException

/**
 * @author saurabh
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileEdit() {

    val yearCharLimit = 4
    val containerColor = Color.LightGray.copy(alpha = 0.2f)
    val context = LocalContext.current

    var displayName by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var yearOfBirth by remember {
        mutableStateOf(TextFieldValue(""))
    }

    val nameError by remember {
        derivedStateOf {
            displayName.text.isEmpty() || displayName.text.length > 20
        }
    }

    val ageError by remember {
        derivedStateOf {
            try {
                yearOfBirth.text.isEmpty() ||
                        (yearOfBirth.text.length != 4) ||
                        (yearOfBirth.text.toInt() !in (1950..2023))
            } catch (e: NumberFormatException){
                true
            }
        }
    }

    val age by remember {
        derivedStateOf {
            if (!ageError) {
                try {
                    2023 - yearOfBirth.text.toInt()
                } catch (e: NumberFormatException){
                    0
                }
            } else 0
        }
    }

    var isMale by remember {
        mutableStateOf(false)
    }

    val isFemale by remember {
        derivedStateOf {
            isMale.not()
        }
    }

    val saveEnabled by remember {
        derivedStateOf {
            !nameError && !ageError

        }
    }

    Scaffold(topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Profile Edit",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.ArrowBack, "backIcon")
                }
            },
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 24.dp,
                    end = 24.dp
                )
        ) {
            // User name
            TextHeader(title = "User name")
            Text(
                text = "@saurabhpant",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.DarkGray,
                ),
                modifier = Modifier.background(Color.LightGray.copy(alpha = 0.2f)).testTag
                    ("user_name"),
            )
            // Display name
            TextHeader(
                title = "Display name",
                modifier = Modifier.padding(top = 16.dp),
            )
            TextField(
                value = displayName,
                onValueChange = { name ->
                    displayName = name
                },
                modifier = Modifier.fillMaxWidth().testTag("display_name"),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                ),
                placeholder = {
                    Text(
                        text = "John Smith etc",
                        fontSize = 20.sp,
                        color = Color.Gray,
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Person,
                        contentDescription = ""
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                isError = nameError,
                supportingText = {
                    if (nameError)
                        Text(
                            text = "name is invalid",
                            fontSize = 14.sp,
                            color = Color.Red,
                        )
                }
            )

            // Year of birth
            TextHeader(
                title = "Year of birth",
                modifier = Modifier.padding(top = 16.dp),
            )
            TextField(
                value = yearOfBirth,
                onValueChange = { year ->
                    if (yearCharLimit <= 4) yearOfBirth = year
                },
                modifier = Modifier.fillMaxWidth().testTag("year_of_birth"),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                ),
                placeholder = {
                    Text(
                        text = "XXXX",
                        fontSize = 20.sp,
                        color = Color.Gray,
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Filled.DateRange,
                        contentDescription = ""
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                isError = ageError,
                supportingText = {
                    if (ageError)
                        Text(
                            text = "year is invalid",
                            fontSize = 14.sp,
                            color = Color.Red,
                        )
                }
            )
            // calculated age
            if (!ageError)
                TextHeader(
                    title = "Age: $age",
                    modifier = Modifier.padding(top = 4.dp),
                    tag = "age"
                )

            // gender
            TextHeader(
                title = "Select gender",
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Column {
                    RadioField(
                        title = "Male",
                        isSelected = isMale,
                        onClick = {
                            isMale = true
                        },
                        tag = "male_container"
                    )
                    if (isMale) {
                        Box(
                            modifier = Modifier.size(100.dp).testTag("male_image"),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_male),
                                contentScale = ContentScale.FillBounds,
                                contentDescription = ""
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    RadioField(
                        title = "Female",
                        isSelected = isFemale,
                        onClick = {
                            isMale = false
                        },
                        tag = "female_container"
                    )
                    if (!isMale) {
                        Box(
                            modifier = Modifier
                                .size(100.dp).testTag("female_image"),
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_female),
                                contentScale = ContentScale.FillBounds,
                                contentDescription = ""
                            )
                        }
                    }
                }
            }
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .background(Color.Cyan)
            )
            Button(
                onClick = {
                          Toast.makeText(context,"Profile saved",Toast.LENGTH_SHORT).show()
                },
                shape = RoundedCornerShape(corner = CornerSize(100.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Green.copy(alpha = 0.4f),
                    disabledContainerColor = Color.Gray.copy(alpha = 0.4f),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("save"),
                enabled = saveEnabled
            ) {
                Text(
                    text = "Save",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.DarkGray,
                    ),
                )
            }
        }
    }
}

@Composable
fun TextHeader(
    modifier: Modifier = Modifier,
    title: String,
    tag: String = ""
) {
    Text(
        text = title,
        modifier = modifier.testTag(tag),
        fontSize = 14.sp,
        color = Color.Black
    )
}

@Composable
fun RadioField(
    title: String,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    tag: String = ""
) {
    Row(
        modifier = Modifier.clickable { onClick() }.testTag(tag),
    ) {
        Icon(
            Icons.Filled.CheckCircle,
            contentDescription = "",
            tint = if (isSelected) Color.Green.copy(alpha = 0.4f) else Color.Gray.copy(alpha = 0.4f)
        )
        Text(
            text = title,
            modifier = Modifier.padding(start = 12.dp),
            fontSize = 20.sp,
            color = Color.Black
        )
    }
}