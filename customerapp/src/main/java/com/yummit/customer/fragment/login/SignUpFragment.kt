package com.yummit.customer.fragment.login


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.yummit.customer.MainActivity
import com.yummit.customer.R
import com.yummit.customer.helper.primaryDisable
import com.yummit.customer.helper.primaryEnable
import com.yummit.customer.viewmodel.SignUpViewModel
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment(), View.OnClickListener {
    private lateinit var viewModel: SignUpViewModel
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var phone: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)

        btn_signup_signup.setOnClickListener(this)
        tv_signup_login.setOnClickListener(this)

        if (edt_signup_email.text.isNullOrEmpty())
            btn_signup_signup.primaryDisable()

        edt_signup_email.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_signup_signup.primaryEnable()
            }
        })

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.signUpStatus.observe(this, Observer {
            it?.let {
                if (it){
                    startActivity(Intent(activity, MainActivity::class.java))
                    Toast.makeText(context, "User created", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.loading.observe(this, Observer {
            it?.let {
                pb_signup.visibility = if (it) View.VISIBLE else View.GONE
                btn_signup_signup.visibility = if (it) View.INVISIBLE else View.VISIBLE
            }
        })

        viewModel.error.observe(this, Observer {
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_signup_signup -> {
                if (checkData()){
                    if (samePassword()){
                        name = edt_signup_name.text.toString()
                        email = edt_signup_email.text.toString()
                        password = edt_signup_password.text.toString()
                        phone = edt_signup_phone.text.toString()

                        viewModel.signUp(name, email, phone, password)
                    } else {
                        Toast.makeText(activity, "Password not same", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(activity, "Please fill the blanks", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.tv_signup_login -> {
                fragmentManager?.beginTransaction()?.replace(R.id.fl_login_container, LoginFragment())?.commit()
            }
        }
    }

    private fun checkData(): Boolean{
        if (edt_signup_name.text.isNullOrBlank()) return false
        if (edt_signup_email.text.isNullOrBlank()) return false
        if (edt_signup_password.text.isNullOrBlank()) return false
        if (edt_signup_password2.text.isNullOrBlank()) return false
        return true
    }

    private fun samePassword(): Boolean{
        if (edt_signup_password.text.toString().equals(edt_signup_password2.text.toString())) return true
        return false
    }
}
