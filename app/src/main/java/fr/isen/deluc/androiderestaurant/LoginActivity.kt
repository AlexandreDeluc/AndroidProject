package fr.isen.deluc.androiderestaurant

import android.app.Activity
import android.content.Context
import android.media.audiofx.AcousticEchoCanceler.create
import android.media.audiofx.AutomaticGainControl.create
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.google.gson.internal.UnsafeAllocator.create
import fr.isen.deluc.androiderestaurant.Basket.Basket.Companion.USER_PREFERENCE_NAME
import fr.isen.deluc.androiderestaurant.Network.NetworkConstant
import fr.isen.deluc.androiderestaurant.Network.User
import fr.isen.deluc.androiderestaurant.Network.UserResult
import fr.isen.deluc.androiderestaurant.User.LoginFragment
import fr.isen.deluc.androiderestaurant.User.RegisterFragment
import fr.isen.deluc.androiderestaurant.databinding.ActivityLoginBinding
import org.json.JSONObject
import java.net.URI.create

interface LoginActivityFragmentInteraction{


    fun showLogin()
    fun showRegister()
    fun makeRequest(email:String?, password: String?, firstName: String?, lastName: String?, isFromLogin: Boolean)
}



class LoginActivity : AppCompatActivity(), LoginActivityFragmentInteraction {

    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = RegisterFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, fragment).commit()
    }

    override fun showLogin() {
        val loginFragment = LoginFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, loginFragment)
            .commit()
    }

    override fun showRegister() {
        val registerFragment = RegisterFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, registerFragment)
            .commit()
    }

    override fun makeRequest(
        email: String?,
        password: String?,
        firstName: String?,
        lastName: String?,
        isFromLogin: Boolean
    ) {
        if (verifyInformation(email, password, firstName, lastName, isFromLogin)) {
            launchRequest(email, password, firstName, lastName, isFromLogin)
        } else {
            Toast.makeText(this, getString(R.string.invalidForm), Toast.LENGTH_LONG).show()

        }
    }

    private fun launchRequest(
        email: String?,
        password: String?,
        firstName: String?,
        lastName: String?,
        isFromLogin: Boolean
    ) {
        val queue = Volley.newRequestQueue(this)
        var requestPath = NetworkConstant.BASE_URL
        if (isFromLogin) {
            requestPath += NetworkConstant.LOGIN
        } else {
            requestPath += NetworkConstant.REGISTER
        }

        val parameters = JSONObject()
        parameters.put(NetworkConstant.KEY_SHOP, NetworkConstant.SHOP)
        parameters.put(NetworkConstant.KEY_EMAIL, email)
        parameters.put(NetworkConstant.KEY_PASSWORD, password)

        if (!isFromLogin) {
            parameters.put(NetworkConstant.KEY_FIRSTNAME, firstName)
            parameters.put(NetworkConstant.KEY_LASTNAME, lastName)
        }

        val request = JsonObjectRequest(
            Request.Method.POST,
            requestPath,
            parameters,
            {
                val userResult = GsonBuilder().create().fromJson(it.toString(), UserResult::class.java)
                saveUser(userResult.data)
            },
            {
                Log.d("request", it.message ?: "erreur")
            }
        )
        queue.add(request)
    }

    private fun saveUser(user: User) {
        val sharedPreferences = getSharedPreferences(USER_PREFERENCE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ID_USER, user.id)
        editor.apply()
        setResult(Activity.RESULT_OK)
        finish()
    }


    private fun verifyInformation(
        email: String?,
        password: String?,
        firstName: String?,
        lastName: String?,
        isFromLogin: Boolean
    ): Boolean {
        var verified = (email?.isNotEmpty() == true && password?.count() ?: 0 >= 6)
        if (!isFromLogin) {
            verified =
                verified && (firstName?.isNotEmpty() == true && lastName?.isNotEmpty() == true)
        }
        return verified
    }

    companion object {
        const val USER_PREFERENCES_NAME="USER_PREFERENCES_NAME"
        const val ID_USER="ID_USER"
    }
}
