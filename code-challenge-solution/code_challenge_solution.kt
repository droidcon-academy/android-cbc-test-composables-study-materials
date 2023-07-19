@Test
fun when_all_inputs_correct_then_save_is_enabled() {
    composeTestRule.onNodeWithTag(tagDisplayName).performTextInput("Saurabh")
    composeTestRule.onNodeWithTag(tagYear).performTextInput("1990")
    composeTestRule.onNodeWithTag(tagMaleContainer).performClick()
    composeTestRule.onNodeWithTag(tagAge).assertExists()
    composeTestRule.onNodeWithTag(tagMaleImage).assertExists()
    composeTestRule.onNodeWithTag(tagSave).assertIsNotEnabled()
}