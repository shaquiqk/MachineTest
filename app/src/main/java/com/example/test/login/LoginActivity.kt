package com.example.test.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.test.databinding.ActivityLoginBinding
import com.example.test.listActivity.ListActivity

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    val userName = "User"
    val password = "Pass"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnContinue.setOnClickListener {
                if (edtUserName.text.toString() == userName) {
                    if (edtPass.text.toString() == password) {
                        startActivity(Intent(this@LoginActivity, ListActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this@LoginActivity, "Wrong Password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Wrong Username", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}