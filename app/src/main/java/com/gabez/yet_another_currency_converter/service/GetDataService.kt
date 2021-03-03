package com.gabez.yet_another_currency_converter.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.gabez.data_access.apiFacade.ApiFacade
import com.gabez.data_access.common.ResponseStatus
import com.gabez.data_access.entities.CurrencyUniversal
import com.gabez.data_access.localDbFacade.LocalDbFacade
import com.gabez.yet_another_currency_converter.R
import com.gabez.yet_another_currency_converter.MainActivity
import com.gabez.nbp_api.apiService.network.NetworkClient
import com.gabez.local_database.CurrencyDatabase
import com.gabez.local_database.CurrencyEntity
import com.gabez.yet_another_currency_converter.internetConnection.InternetConnectionMonitor
import kotlinx.coroutines.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetDataService : Service(), KoinComponent {
    private val CHANNEL_ID = "com.gabez.yet_another_currency_converter.service.CHANNEL_ID"

    private val localDatabase: CurrencyDatabase by inject()
    private val hasInternet: InternetConnectionMonitor by inject()

    private val apiFacade: ApiFacade by inject()
    private val localDbFacade: LocalDbFacade by inject()

    private var repeatFunJob: Job? = null

    companion object {
        fun startService(context: Context) {
            val startIntent = Intent(context, GetDataService::class.java)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, GetDataService::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        GlobalScope.launch(Dispatchers.IO) {
            repeatFunJob = repeatFun()
            repeatFunJob!!.start()
        }

        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Currency Calculator")
            .setContentText("Listening for data updates...")
            .setSmallIcon(R.drawable.ic_star_empty)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1, notification)
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        GlobalScope.launch { repeatFunJob?.cancel() }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    private suspend fun loadRemoteData() {
        Log.v("SERVICE", "SERVICE")
        val getRemoteData = GlobalScope.async { apiFacade.getCurrencies() }
        val getFavCurrenciesLocal = GlobalScope.async { localDbFacade.getFavCurrencies() }

        val apiResponse = getRemoteData.await()

        if (apiResponse.flag != ResponseStatus.FAILED) {

            val currencies = apiResponse.data!!

            currencies.map { currency ->
                getFavCurrenciesLocal.await().map { favCurrency ->
                    if (favCurrency.currencyName == currency.currencyName) currency.isFavourite =
                        true
                }

            }

            localDatabase.dao().updateData(currencies.toEntityList())
        }
    }

    private suspend fun repeatFun(): Job {
        while (true) {
            if (hasInternet.value == true) {
                loadRemoteData()
                delay(1000 * 10)
            }
        }
    }

    private fun List<CurrencyUniversal>.toEntityList(): List<CurrencyEntity> {
        return this.map { item ->
            CurrencyEntity(
                currencyName = item.currencyName,
                code = item.code,
                mid = item.mid,
                isFavourite = item.isFavourite
            )
        }
    }
}