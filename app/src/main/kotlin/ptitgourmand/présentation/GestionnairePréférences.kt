package ptitgourmand.présentation


import android.content.Context
import android.widget.Toast
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class GestionnairePréférences(context: Context) {

    private val dataStore: DataStore<Preferences> = context.createDataStore(
        name = "index_preferences"
    )

    private object PreferencesKeys{
        val INDEX_RECETTE_KEY = preferencesKey<Int>("index")
    }

    suspend fun updateDataStoreId(index: Int) {
        dataStore.edit {
            it[PreferencesKeys.INDEX_RECETTE_KEY] = index
        }
    }
    val preferencesFlow = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            }
        }
        .map {
            val index = it[PreferencesKeys.INDEX_RECETTE_KEY] ?: -1
            index
        }


}