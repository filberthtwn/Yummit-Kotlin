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
import com.yummit.customer.helper.SharedPreferencesHelper
import com.yummit.customer.helper.primaryDisable
import com.yummit.customer.helper.primaryEnable
import com.yummit.customer.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

/**
 * A simple [Fragment] subclass.
 */
class LoginFragment : Fragment(), View.OnClickListener {
    private lateinit var viewModel: LoginViewModel
    private lateinit var email: String
    private lateinit var password: String

    private lateinit var prefHelper: SharedPreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        prefHelper = SharedPreferencesHelper(requireContext())
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        btn_login_login.setOnClickListener(this)
        tv_login_signup.setOnClickListener(this)

        if (edt_login_email.text.isNullOrEmpty())
            btn_login_login.primaryDisable()

        edt_login_email.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                btn_login_login.primaryEnable()
            }
        })

        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                pb_login.visibility = if (it) View.VISIBLE else View.GONE
                btn_login_login.visibility = if (it) View.INVISIBLE else View.VISIBLE
            }
        })

        viewModel.loginStatus.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    startActivity(Intent(context, MainActivity::class.java))
                    activity?.finish()
                }
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View) {
        when (v.id){
            R.id.btn_login_login -> {
                if (checkData()){
                    submitData()
                } else {
                    Toast.makeText(
                        context,
                        "Please input your email and password",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            R.id.tv_login_forgotpass -> {

            }
            R.id.tv_login_signup -> {
                fragmentManager?.beginTransaction()?.replace(R.id.fl_login_container, SignUpFragment())?.commit()
            }
        }
    }

    private fun checkData(): Boolean {
        return (edt_login_email.text.isNotEmpty() && edt_login_password.text.isNotEmpty())
    }

    private fun submitData() {
        email = edt_login_email.text.toString()
        password = edt_login_password.text.toString()

        viewModel.login(email, password)
    }

    private fun autoLogin(){
        val em = prefHelper.getSavedEmail()
        val pass = prefHelper.getSavedPassword()

        viewModel.login(em, pass)
    }
}
