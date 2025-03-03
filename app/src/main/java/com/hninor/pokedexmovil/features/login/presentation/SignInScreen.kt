import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.hninor.pokedexmovil.R
import com.hninor.pokedexmovil.features.login.presentation.SignInViewModel

@Composable
fun SignInScreen(signInViewModel: SignInViewModel, onLoginSuccess: () -> Unit) {
    val context = LocalContext.current


    val signInLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = signInViewModel.handleSignInResult(task)
            if (account != null) {
                Toast.makeText(context, "Bienvenido, ${account.displayName}", Toast.LENGTH_SHORT)
                    .show()
                onLoginSuccess()

            } else {
                Toast.makeText(context, "Error en inicio de sesión", Toast.LENGTH_SHORT).show()
            }
        }

    Button(
        modifier = Modifier.wrapContentSize(),
        onClick = { signInViewModel.signIn(signInLauncher) },

        ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = "Google Sign-In",
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Iniciar sesión con Google")
    }
}