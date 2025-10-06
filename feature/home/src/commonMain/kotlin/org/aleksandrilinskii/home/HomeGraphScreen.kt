package org.aleksandrilinskii.home

import ContentWithMessageBar
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aleksandrilinskii.nutrisport.shared.Alpha
import com.aleksandrilinskii.nutrisport.shared.BebasNeueFont
import com.aleksandrilinskii.nutrisport.shared.FontSize
import com.aleksandrilinskii.nutrisport.shared.IconPrimary
import com.aleksandrilinskii.nutrisport.shared.Resources
import com.aleksandrilinskii.nutrisport.shared.Surface
import com.aleksandrilinskii.nutrisport.shared.SurfaceLighter
import com.aleksandrilinskii.nutrisport.shared.TextPrimary
import com.aleksandrilinskii.nutrisport.shared.navigation.Screen
import com.aleksandrilinskii.nutrisport.shared.util.getScreenWidth
import org.aleksandrilinskii.home.component.BottomBar
import org.aleksandrilinskii.home.component.CustomDrawer
import org.aleksandrilinskii.home.domain.BottomBarDestination
import org.aleksandrilinskii.home.domain.CustomDrawerState
import org.aleksandrilinskii.home.domain.isClosed
import org.aleksandrilinskii.home.domain.isOpened
import org.aleksandrilinskii.home.domain.opposite
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import rememberMessageBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeGraphScreen(
    navigateToAuth: () -> Unit,
    navigateToProfile: () -> Unit
) {
    val viewModel = koinViewModel<HomeGraphViewModel>()
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState()
    val selectedDestination by remember {
        derivedStateOf {
            val route = currentRoute.value?.destination?.route.toString()
            when {
                route.contains(BottomBarDestination.ProductsOverview.screen.toString()) ->
                    BottomBarDestination.ProductsOverview

                route.contains(BottomBarDestination.Cart.screen.toString()) ->
                    BottomBarDestination.Cart

                route.contains(BottomBarDestination.Categories.screen.toString()) ->
                    BottomBarDestination.Categories

                else -> BottomBarDestination.ProductsOverview
            }
        }
    }

    val screenWidth = remember { getScreenWidth() }
    var drawerState by remember { mutableStateOf(CustomDrawerState.CLOSED) }
    val offsetValue by remember { derivedStateOf { (screenWidth / 1.5).dp } }
    val animatedOffset by animateDpAsState(
        targetValue = if (drawerState.isOpened()) {
            offsetValue
        } else 0.dp
    )

    val animateScale by animateFloatAsState(
        targetValue = if (drawerState.isOpened()) {
            0.9f
        } else 1f
    )

    val animateRadius by animateDpAsState(
        targetValue = if (drawerState.isOpened()) {
            20.dp
        } else 0.dp
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (drawerState.isOpened()) {
            SurfaceLighter
        } else Surface
    )

    val messageBarState = rememberMessageBarState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .systemBarsPadding()
    ) {
        CustomDrawer(
            onProfileClick = navigateToProfile,
            onContactsClick = {},
            onSignOutClick = {
                viewModel.signOut(
                    onSuccess = navigateToAuth,
                    onError = messageBarState::addError
                )
            },
            onAdminPanelClick = {}
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(animateRadius))
                .offset(x = animatedOffset)
                .scale(scale = animateScale)
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(animateRadius),
                    ambientColor = Color.Black.copy(alpha = Alpha.TEN_PERCENT),
                    spotColor = Color.Black.copy(alpha = Alpha.TEN_PERCENT)
                )
        ) {
            Scaffold(
                containerColor = Surface,
                topBar = {
                    CenterAlignedTopAppBar(
                        title = {
                            AnimatedContent(targetState = selectedDestination) { destination ->
                                Text(
                                    text = destination.title,
                                    fontFamily = BebasNeueFont(),
                                    fontSize = FontSize.LARGE,
                                    color = TextPrimary
                                )
                            }
                        },
                        navigationIcon = {
                            AnimatedContent(
                                targetState = drawerState
                            ) { drawer ->
                                if (drawer.isClosed()) {
                                    IconButton(
                                        onClick = { drawerState = drawerState.opposite() },
                                        content = {
                                            Icon(
                                                painter = painterResource(Resources.Icon.Menu),
                                                contentDescription = null,
                                                tint = IconPrimary
                                            )
                                        }
                                    )
                                } else {
                                    IconButton(
                                        onClick = { drawerState = drawerState.opposite() },
                                        content = {
                                            Icon(
                                                painter = painterResource(Resources.Icon.Close),
                                                contentDescription = null,
                                                tint = IconPrimary
                                            )
                                        }
                                    )
                                }
                            }

                        },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Surface,
                            scrolledContainerColor = Surface,
                            navigationIconContentColor = IconPrimary,
                            titleContentColor = TextPrimary,
                            actionIconContentColor = IconPrimary
                        )
                    )
                }
            ) { paddingValues ->
                ContentWithMessageBar(
                    modifier = Modifier.fillMaxSize()
                        .padding(
                            top = paddingValues.calculateTopPadding(),
                            bottom = paddingValues.calculateBottomPadding()
                        ),
                    messageBarState = messageBarState,
                    errorMaxLines = 2,
                    contentBackgroundColor = Surface
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        NavHost(
                            modifier = Modifier.weight(1f),
                            navController = navController,
                            startDestination = Screen.ProductsOverview
                        ) {
                            composable<Screen.ProductsOverview> {}
                            composable<Screen.Cart> {}
                            composable<Screen.Categories> {}
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(modifier = Modifier.padding(12.dp)) {
                            BottomBar(
                                selected = selectedDestination,
                                onClick = { destination ->
                                    navController.navigate(destination.screen) {
                                        launchSingleTop = true
                                        popUpTo(Screen.ProductsOverview) {
                                            saveState = true
                                            inclusive = false
                                        }
                                        restoreState = true
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}