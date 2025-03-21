package mx.edu.itesca.practica12

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        auth=Firebase.auth
        val email: EditText =findViewById(R.id.etEmail)
        val password: EditText =findViewById(R.id.etPassword)
        val error: TextView =findViewById(R.id.tvError)
        val btnEntrar: Button =findViewById(R.id.btnLogin)
        val btnIrRegistrar: Button =findViewById(R.id.btnGoRegister)
        error.visibility= View.INVISIBLE

        btnIrRegistrar.setOnClickListener {
            val intent=Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
        btnEntrar.setOnClickListener {
            login(email.text.toString(),password.text.toString())
        }
    }
    fun goToMain(user:FirebaseUser){
        val intent=Intent(this,MainActivity::class.java)
        intent.putExtra("user",user.email)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
    fun showError(text:String="",visible:Boolean){
        val error: TextView =findViewById(R.id.tvError)
        error.text=text
        error.visibility = if(visible) View.VISIBLE else View.INVISIBLE
    }

    /*public override fun onStart() {
        super.onStart()
        val currentUser=auth.currentUser
        if (currentUser!=null){
            goToMain(currentUser)
        }
    }*/
    fun login(email:String,password:String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
            task->
            if (task.isSuccessful){
                val user=auth.currentUser
                showError(visible = false)
                goToMain(user!!)
            }else{
                showError("Usuario y/o contraseña equivocados",true)
            }
        }
    }
}