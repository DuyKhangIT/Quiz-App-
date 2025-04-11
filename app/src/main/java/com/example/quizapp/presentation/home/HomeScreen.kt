package com.example.quizapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.quizapp.presentation.home.application.EventHomeScreen
import com.example.quizapp.presentation.home.application.StateHomeScreen
import com.example.quizapp.presentation.home.components.AppDropDownMenu
import com.example.quizapp.presentation.home.components.ButtonBox
import com.example.quizapp.presentation.home.components.HomeHeader
import com.example.quizapp.presentation.nav_graph.Routes
import com.example.quizapp.presentation.util.Constants
import com.example.quizapp.presentation.util.Constants.difficulty
import com.example.quizapp.presentation.util.Constants.type
import com.example.quizapp.presentation.util.Dimens.MediumPadding
import com.example.quizapp.presentation.util.Dimens.MediumSpacerHeight
import com.example.quizapp.presentation.util.Dimens.SmallSpacerHeight

//@Preview
//@Composable
//fun PrevHome() {
//    HomeScreen(
//        state = StateHomeScreen(),
//        event = {},
//    )
//}


@Composable
fun HomeScreen(
    state: StateHomeScreen,
    event: (EventHomeScreen) -> Unit,
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        HomeHeader()

        Spacer(modifier = Modifier.height(MediumSpacerHeight))
        AppDropDownMenu(
            menuName = "Number of Questions:",
            menuList = Constants.numbersAsString,
            text = state.numberOfQuizzes.toString(),
            onDropDownClick = { event(EventHomeScreen.SetNumberOfQuizzes(it.toInt())) },
        )

        Spacer(modifier = Modifier.height(SmallSpacerHeight))
        AppDropDownMenu(
            menuName = "Select Category:",
            menuList = Constants.category,
            text = state.category,
            onDropDownClick = {
                event(EventHomeScreen.SetQuizCategory(it))
            },
        )

        Spacer(modifier = Modifier.height(SmallSpacerHeight))
        AppDropDownMenu(
            menuName = "Select Difficulty:",
            menuList = difficulty,
            text = state.difficulty,
            onDropDownClick = {
                event(EventHomeScreen.SetQuizDifficulty(it))
            },
        )

        Spacer(modifier = Modifier.height(SmallSpacerHeight))
        AppDropDownMenu(
            menuName = "Select Type:",
            menuList = type,
            text = state.type,
            onDropDownClick = {
                event(EventHomeScreen.SetQuizType(it))
            },
        )

        Spacer(modifier = Modifier.height(MediumSpacerHeight))

        ButtonBox(
            text = "Generate Quiz",
            padding = MediumPadding,
            onButtonClick = {
                navController.navigate(
                    route = Routes.QuizScreen.passQuizArguments(
                        state.numberOfQuizzes,
                        state.category,
                        state.difficulty,
                        state.type
                    )
                )
            },
        )
    }
}