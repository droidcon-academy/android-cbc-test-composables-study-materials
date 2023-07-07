package com.droidcon.testingcomposables

import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotFocused
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * @author saurabh
 */

const val tagUserName = "user_name"
const val tagAge = "age"
const val tagYear = "year_of_birth"
const val tagDisplayName = "display_name"
const val tagMaleContainer = "male_container"
const val tagMaleImage = "male_image"
const val tagFemaleContainer = "female_container"
const val tagFemaleImage = "female_image"
const val tagSave = "save"

@RunWith(AndroidJUnit4::class)
class ProfileEditKtTest {

    @get: Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        composeTestRule.setContent {
            ProfileEdit()
        }
    }

    @Test
    fun initial_components_visibility() {
        composeTestRule.onNodeWithTag(tagUserName).assertExists()
        composeTestRule.onNodeWithTag(tagDisplayName).assertExists()
        composeTestRule.onNodeWithTag(tagYear).assertExists()
        composeTestRule.onNodeWithTag(tagAge).assertDoesNotExist()
        composeTestRule.onNodeWithTag(tagMaleImage).assertDoesNotExist()
        composeTestRule.onNodeWithTag(tagFemaleImage).assertExists()
        composeTestRule.onNodeWithTag(tagSave).assertExists()
        composeTestRule.onNodeWithTag(tagSave).assertIsNotEnabled()
    }

    @Test
    fun when_name_is_correct_and_year_is_incorrect_then_age_is_invisible_and_save_is_disabled() {
        composeTestRule.onNodeWithTag(tagDisplayName).performTextInput("Saurabh")
        composeTestRule.onNodeWithTag(tagYear).performTextInput("199")
        composeTestRule.onNodeWithTag(tagAge).assertDoesNotExist()
        composeTestRule.onNodeWithTag(tagSave).assertIsNotEnabled()
    }

    @Test
    fun when_name_is_correct_and_year_is_correct_then_age_is_visible_and_save_is_enabled() {
        composeTestRule.onNodeWithTag(tagDisplayName).performTextInput("Saurabh")
        composeTestRule.onNodeWithTag(tagYear).performTextInput("2000")
        composeTestRule.onNodeWithTag(tagAge).assertExists()
        composeTestRule.onNodeWithTag(tagAge).assertTextEquals("Age: 23")
        composeTestRule.onNodeWithTag(tagSave).assertIsEnabled()
    }

    @Test
    fun when_name_is_incorrect_and_year_is_correct_then_age_is_visible_and_save_is_disabled() {
        composeTestRule.onNodeWithTag(tagDisplayName).performTextInput("")
        composeTestRule.onNodeWithTag(tagYear).performTextInput("2000")
        composeTestRule.onNodeWithTag(tagAge).assertExists()
        composeTestRule.onNodeWithTag(tagAge).assertTextEquals("Age: 23")
        composeTestRule.onNodeWithTag(tagSave).assertIsNotEnabled()
    }

    @Test
    fun when_female_gender_selected_then_female_image_visible() {
        composeTestRule.onNodeWithTag(tagFemaleContainer).performClick()
        composeTestRule.onNodeWithTag(tagFemaleImage).assertExists()
        composeTestRule.onNodeWithTag(tagMaleImage).assertDoesNotExist()
    }

    @Test
    fun when_male_gender_selected_then_male_image_visible() {
        composeTestRule.onNodeWithTag(tagMaleContainer).performClick()
        composeTestRule.onNodeWithTag(tagMaleImage).assertExists()
        composeTestRule.onNodeWithTag(tagFemaleImage).assertDoesNotExist()
    }

    @Test
    fun user_name_is_not_clickable() {
        composeTestRule.onNodeWithTag(tagUserName).assertExists()
        composeTestRule.onNodeWithTag(tagUserName).assertHasNoClickAction()
    }

    @Test
    fun when_clicked_display_name_ime_action_next_then_year_become_focusable() {
        composeTestRule.onNodeWithTag(tagDisplayName).performImeAction()
        composeTestRule.onNodeWithTag(tagYear).assertIsFocused()
        composeTestRule.onNodeWithTag(tagDisplayName).assertIsNotFocused()
    }

    @Test
    fun when_clicked_year_ime_action_done_then_year_become_focusable() {
        composeTestRule.onNodeWithTag(tagDisplayName).performImeAction()
        composeTestRule.onNodeWithTag(tagYear).assertIsFocused()
        composeTestRule.onNodeWithTag(tagDisplayName).assertIsNotFocused()
    }

    @Test
    fun when_all_inputs_correct_then_save_is_enabled() {
        composeTestRule.onNodeWithTag(tagDisplayName).performTextInput("Saurabh")
        composeTestRule.onNodeWithTag(tagYear).performTextInput("2000")
        composeTestRule.onNodeWithTag(tagMaleContainer).performClick()
        composeTestRule.onNodeWithTag(tagAge).assertExists()
        composeTestRule.onNodeWithTag(tagMaleImage).assertExists()
        composeTestRule.onNodeWithTag(tagSave).assertIsEnabled()
    }
}