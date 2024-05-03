package me.nasiri.coinmaster.di

import me.nasiri.coinmaster.data.local.LocalRepoImpl
import me.nasiri.coinmaster.data.remote.RemoteRepoImpl
import me.nasiri.coinmaster.data.remote.api.ApiService
import me.nasiri.coinmaster.domain.repository.MainRepo
import me.nasiri.coinmaster.ui.coin.CoinViewModel
import me.nasiri.coinmaster.ui.market.MarketViewModel
import me.nasiri.coinmaster.util.CoilImageLoader
import me.nasiri.coinmaster.util.ConnectionTester
import me.nasiri.coinmaster.util.Constans.BASE_URL
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val CenterModule = module {

    single<Services.NetworkConnectionStatus> { ConnectionTester(get()) }
    single<Services.ImageLoader> { CoilImageLoader() }

    single<ApiService> {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    single<Services.CenterRepo> { MainRepo(LocalRepoImpl(), RemoteRepoImpl(get())) }


    viewModel { MarketViewModel(get()) }
    viewModel { CoinViewModel() }

}