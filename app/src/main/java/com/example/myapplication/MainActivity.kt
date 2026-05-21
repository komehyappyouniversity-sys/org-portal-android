package jp.komehyappyo.member
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = FirebaseFirestore.getInstance()

        db.collection("organizations")
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                Log.d("FirestoreTest", "✅ organizations count: ${documents.size()}")
                for (document in documents) {
                    Log.d("FirestoreTest", "✅ organizationId: ${document.id}")
                }
            }
            .addOnFailureListener { e ->
                Log.e("FirestoreTest", "❌ Firestore read failed", e)
            }
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}