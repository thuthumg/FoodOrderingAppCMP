package org.ttm.foodorderingappcmp.features.profile.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import foodorderingappcmp.composeapp.generated.resources.Res
import foodorderingappcmp.composeapp.generated.resources.about
import foodorderingappcmp.composeapp.generated.resources.business_inquiries
import foodorderingappcmp.composeapp.generated.resources.company_info
import foodorderingappcmp.composeapp.generated.resources.company_name
import foodorderingappcmp.composeapp.generated.resources.contact_as
import foodorderingappcmp.composeapp.generated.resources.customer_support
import foodorderingappcmp.composeapp.generated.resources.founded
import foodorderingappcmp.composeapp.generated.resources.headquarters
import foodorderingappcmp.composeapp.generated.resources.our_mission
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.ttm.foodorderingappcmp.common.ui.FoodOrderingAppTopAppBar
import org.ttm.foodorderingappcmp.core.MARGIN_CARD_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM
import org.ttm.foodorderingappcmp.core.MARGIN_MEDIUM_2
import org.ttm.foodorderingappcmp.core.MARGIN_XLARGE
import org.ttm.foodorderingappcmp.core.OUTLINE_TXT_FIELD_TXT_COLOR
import org.ttm.foodorderingappcmp.core.SCREEN_BG_COLOR
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_2X
import org.ttm.foodorderingappcmp.core.TEXT_REGULAR_3X
import org.ttm.foodorderingappcmp.core.TITLE_BLACK_COLOR

@Composable
fun AboutScreen(onTapBack:()-> Unit) {
    Scaffold(
        containerColor = SCREEN_BG_COLOR,
        topBar = {
            FoodOrderingAppTopAppBar(
                stringResource(Res.string.about),
                onTapBack = {
                    onTapBack()
                })
        }
    ) { innerPadding ->

            LazyColumn(modifier = Modifier
                .padding(innerPadding).fillMaxSize(),
                contentPadding = PaddingValues(bottom = 88.dp)) {

                item {
                    Column(
                        modifier = Modifier.padding(horizontal = MARGIN_MEDIUM_2),
                        verticalArrangement = Arrangement.spacedBy(MARGIN_MEDIUM_2)
                    ){
                        //Our Mission
                        Text(
                            stringResource(Res.string.our_mission),
                            fontSize = TEXT_REGULAR_3X,
                            color = TITLE_BLACK_COLOR,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = MARGIN_MEDIUM_2)
                        )

                        //Description
                        Text(
                            "At FlavorDash, we're dedicated to connecting food lovers with their favorite local restaurants. Our mission is to make ordering food online simple, fast, and reliable, while supporting the growth of local businesses.",
                            fontSize = TEXT_REGULAR_2X,
                            color = TITLE_BLACK_COLOR,
                            modifier = Modifier
                        )

                        //Spacer
                        Spacer(modifier = Modifier.height(MARGIN_CARD_MEDIUM_2))

                        //Company Information
                        Text(
                            stringResource(Res.string.company_info),
                            fontSize = TEXT_REGULAR_3X,
                            color = TITLE_BLACK_COLOR,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )

                        //Spacer
                        Spacer(modifier = Modifier.height(MARGIN_MEDIUM))

                        //Company information
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MARGIN_XLARGE)
                        )
                        {
                            //company name
                            Column(modifier= Modifier.weight(1f)) {
                                HorizontalDivider(modifier = Modifier.height(2.dp))

                                Text(
                                    stringResource(Res.string.company_name),
                                    fontSize = TEXT_REGULAR_2X,
                                    color = OUTLINE_TXT_FIELD_TXT_COLOR,
                                    modifier = Modifier.padding(top = MARGIN_MEDIUM_2)
                                )

                                Text(
                                    "FlavorDash Inc.",
                                    fontSize = TEXT_REGULAR_2X,
                                    color = TITLE_BLACK_COLOR,
                                    modifier = Modifier
                                )
                            }
                            //founded
                            Column(modifier = Modifier.weight(1f)) {
                                HorizontalDivider(modifier = Modifier.height(2.dp))

                                Text(
                                    stringResource(Res.string.founded),
                                    fontSize = TEXT_REGULAR_2X,
                                    color = OUTLINE_TXT_FIELD_TXT_COLOR,
                                    modifier = Modifier.padding(top = MARGIN_MEDIUM_2)
                                )

                                Text(
                                    "2018",
                                    fontSize = TEXT_REGULAR_2X,
                                    color = TITLE_BLACK_COLOR,
                                    modifier = Modifier
                                )
                            }
                        }

                        //Spacer
                        Spacer(modifier = Modifier.height(MARGIN_MEDIUM))

                        //Headquarters
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MARGIN_XLARGE)
                        )
                        {

                            Column(modifier= Modifier.weight(1f)) {
                                HorizontalDivider(modifier = Modifier.height(2.dp))


                                Text(
                                    stringResource(Res.string.headquarters),
                                    fontSize = TEXT_REGULAR_2X,
                                    color = OUTLINE_TXT_FIELD_TXT_COLOR,
                                    modifier = Modifier.padding(top = MARGIN_MEDIUM_2)
                                )

                                Text(
                                    "San Francisco, CA",
                                    fontSize = TEXT_REGULAR_2X,
                                    color = TITLE_BLACK_COLOR,
                                    modifier = Modifier
                                )
                            }

                            Column(modifier= Modifier.weight(1f)) {
                             }

                        }


                        //Spacer
                        Spacer(modifier = Modifier.height(MARGIN_CARD_MEDIUM_2))


                        //Contact As
                        Text(
                            stringResource(Res.string.contact_as),
                            fontSize = TEXT_REGULAR_3X,
                            color = TITLE_BLACK_COLOR,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )

                        Spacer(modifier = Modifier.height(MARGIN_MEDIUM))

                        //Customer support
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MARGIN_XLARGE)
                        )
                        {

                            Column(modifier= Modifier.weight(1f)) {
                                HorizontalDivider(modifier = Modifier.height(2.dp))


                                Text(
                                    stringResource(Res.string.customer_support),
                                    fontSize = TEXT_REGULAR_2X,
                                    color = OUTLINE_TXT_FIELD_TXT_COLOR,
                                    modifier = Modifier.padding(top = MARGIN_MEDIUM_2)
                                )

                                Text(
                                    "support@flavordash.com",
                                    fontSize = TEXT_REGULAR_2X,
                                    color = TITLE_BLACK_COLOR,
                                    modifier = Modifier
                                )
                            }

                            Column(modifier= Modifier.weight(1f)) {
                            }

                        }


                        Spacer(modifier = Modifier.height(MARGIN_MEDIUM))

                        //business inquiries
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(MARGIN_XLARGE)
                        )
                        {
                            Column(modifier= Modifier.weight(1f)) {
                                HorizontalDivider(modifier = Modifier.height(2.dp))


                                Text(
                                    stringResource(Res.string.business_inquiries),
                                    fontSize = TEXT_REGULAR_2X,
                                    color = OUTLINE_TXT_FIELD_TXT_COLOR,
                                    modifier = Modifier.padding(top = MARGIN_MEDIUM_2)
                                )

                                Text(
                                    "business@flavordash.com",
                                    fontSize = TEXT_REGULAR_2X,
                                    color = TITLE_BLACK_COLOR,
                                    modifier = Modifier
                                )
                            }

                            Column(modifier= Modifier.weight(0.5f)) {
                            }

                        }


                    }
                }

            }
    }
}

@Preview
@Composable
fun AboutScreenPreview(modifier: Modifier = Modifier) {
    AboutScreen(onTapBack = {})
}