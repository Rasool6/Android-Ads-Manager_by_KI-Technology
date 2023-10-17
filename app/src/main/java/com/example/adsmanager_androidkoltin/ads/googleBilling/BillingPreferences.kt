package com.example.adsmanager_androidkoltin.ads.googleBilling

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.example.adsmanager_androidkoltin.ads.Constants.IN_APP_PURCHASE_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class BillingPreferences(
    context: Context
) {
    private val applicationContext = context.applicationContext
    private val dataStore: DataStore<Preferences> = applicationContext.createDataStore(
        name = "billing_preferences"
    )

    val getAdsPurchaseBookmark: Flow<Boolean?>
        get() = dataStore.data.map { preferences ->
            preferences[KEY_BOOKMARK] ?: false
        }

    suspend fun saveAdsPurchaseBookmark(bookmark: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_BOOKMARK] = bookmark
        }
    }


    companion object {
        val KEY_BOOKMARK = preferencesKey<Boolean>(IN_APP_PURCHASE_KEY)
    }
}