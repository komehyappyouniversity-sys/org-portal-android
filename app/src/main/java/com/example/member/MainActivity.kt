package jp.komehyappyo.member

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.firebase.firestore.FirebaseFirestore
import com.example.member.ui.theme.MyApplicationTheme
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            MyApplicationTheme {

                val organizationId = remember {
                    mutableStateOf("読み込み中...")
                }

                val db = FirebaseFirestore.getInstance()

                db.collection("organizations")
                    .limit(1)
                    .get()
                    .addOnSuccessListener { documents ->

                        Log.d(
                            "FirestoreTest",
                            "✅ organizations count: ${documents.size()}"
                        )

                        for (document in documents) {

                            Log.d(
                                "FirestoreTest",
                                "✅ organizationId: ${document.id}"
                            )

                            organizationId.value = document.id
                        }
                    }
                    .addOnFailureListener { e ->

                        Log.e(
                            "FirestoreTest",
                            "❌ Firestore read failed",
                            e
                        )

                        organizationId.value = "Firestore読込エラー"
                    }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    OrganizationTestScreen(
                        organizationId = organizationId.value,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun OrganizationTestScreen(
    organizationId: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Android版 会員アプリ",
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            text = "organizationId: $organizationId",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}