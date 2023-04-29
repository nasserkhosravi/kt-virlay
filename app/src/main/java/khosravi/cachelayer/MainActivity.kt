package khosravi.cachelayer

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.google.gson.Gson
import khosravi.cachelayer.databinding.ActivityMainBinding
import khosravi.persist.cache.ModelCacheStore
import khosravi.persist.cache.id.IdOwnerString
import khosravi.persist.cache.sharedpreference.SharedPreferenceStore
import kotlinx.coroutines.flow.collect
import org.dizitart.no2.Nitrite

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    private fun nitriteSimpleExample() {
        val cacheStore = NitriteCacheStore(
            Nitrite.builder().openOrCreate("user", "password")
                .getCollection("userCache")
        )
        val modelStore = ModelCacheStore(cacheStore, GsonUserModelConverter(Gson()))
        modelStoreExample(modelStore)
    }

    private fun sharedPreferenceExample() {
        val prefCacheStore = SharedPreferenceStore("user", getSharedPreferences("user", Context.MODE_PRIVATE))
        val modelStore = ModelCacheStore(prefCacheStore, GsonUserModelConverter(Gson()))
        modelStoreExample(modelStore)
    }

    private fun rxBuilderExample() {
        val prefCacheStore = SharedPreferenceStore("user", getSharedPreferences("user", Context.MODE_PRIVATE))
        val wrapper = RxCacheStoreWrapper(prefCacheStore)
        wrapper.getAll().subscribe {
        }
    }

    private suspend fun flowBuilderExample() {
        val prefCacheStore = SharedPreferenceStore("user", getSharedPreferences("user", Context.MODE_PRIVATE))
        val wrapper = FlowCacheStoreWrapper(prefCacheStore)
        wrapper.getAll().collect {

        }
    }

    private fun modelStoreExample(modelStore: ModelCacheStore<User>) {
        val userId = "someId"
        val userOrigin = User(userId)

        //create or update mode
        modelStore.put(userOrigin)

        //retrieving mode
        val retriedUser = modelStore.get(id = userId)
        assert(retriedUser == userOrigin)

        //removing mode
        modelStore.remove(userId)
        assert(modelStore.getOrNull(userId) == null)
        assert(modelStore.has(userId) == false)

        //create or updating by list
        val userList = listOf(User("1"), User("2"))
        modelStore.putAll(userList)
        assert(modelStore.getAll() == userList)

        modelStore.clear()
        assert(modelStore.getAll().isEmpty())
    }
}

data class User(
    val userId: String
) : IdOwnerString {
    override fun getIdValue(): String = userId

}

