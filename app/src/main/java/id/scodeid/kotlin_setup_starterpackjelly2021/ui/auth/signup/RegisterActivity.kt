package id.scodeid.kotlin_setup_starterpackjelly2021.ui.auth.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import id.scodeid.kotlin_setup_starterpackjelly2021.MainActivity
import id.scodeid.kotlin_setup_starterpackjelly2021.data.network.response.auth.signup.User
import id.scodeid.kotlin_setup_starterpackjelly2021.databinding.ActivityRegisterBinding
import id.scodeid.kotlin_setup_starterpackjelly2021.ui.auth.signin.LoginActivity
import id.scodeid.kotlin_setup_starterpackjelly2021.ui.auth.signup.view.RegisterView
import id.scodeid.kotlin_setup_starterpackjelly2021.ui.auth.signup.viewmodel.RegisterViewModel
import id.scodeid.kotlin_setup_starterpackjelly2021.ui.auth.signup.viewmodel.RegisterViewModelFactory
import id.scodeid.kotlin_setup_starterpackjelly2021.utils.gone
import id.scodeid.kotlin_setup_starterpackjelly2021.utils.visible
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class RegisterActivity : AppCompatActivity(), RegisterView {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReg.setOnClickListener {

            val email = binding.ipEdtMail.text.toString().trim { it <= ' ' }
            val pass = binding.ipEdtPass.text.toString()

            Log.d(TAG_LOG, "email : $email")

            try {
                val viewModel: RegisterViewModel by viewModels { RegisterViewModelFactory(this) }
                viewModel.showRegister(applicationContext, email, pass)
            } catch (e: Exception) {
                Log.d(TAG_LOG, "exception : $e")
            }
        }

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun showLoading() {
        binding.pgRegister.visible()
    }

    override fun hideLoading() {
        binding.pgRegister.gone()
    }

    override fun showRegisterView(data: User?) {
        GlobalScope.launch {
            mutableList = null
            val assign = async {
                data?.let {
                    mutableList = it
                }
            }
            assign.await()
            if (mutableList?.email != null)
                startActivity(Intent(applicationContext, MainActivity::class.java))
        }
    }

    companion object {
        val TAG_LOG: String = RegisterActivity::class.java.simpleName
        private var mutableList: User? = null
    }
}