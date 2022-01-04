package com.yummit.ownerapp
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.yummit.ownerapp.`interface`.FoodCallbacks
import com.yummit.ownerapp.controller.FoodController
import com.yummit.ownerapp.global.Global
import com.yummit.ownerapp.model.Menu
import com.yummit.ownerapp.viewInterface.SuccessInterface
import kotlinx.android.synthetic.main.activity_add_menu.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.EasyImage.Callbacks
import java.io.File
import java.io.InputStream
import java.lang.Exception
import java.net.URI
import java.net.URLEncoder

class AddMenuActivity : AppCompatActivity(), SuccessInterface {

    var selectedMenu:Menu = Menu()
    var imageFile: File = File("")

    private var priceAmount:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_menu)
        setupViews()
//        EasyImage.b
    }

    private fun setupViews(){

        EasyImage.configuration(applicationContext)

        /* Hide Action Bar */
        this.supportActionBar?.hide()

        if (intent.getParcelableExtra<Menu>("selectedMenu") != null){
            @Suppress("UNCHECKED_CAST")
            selectedMenu = intent.getParcelableExtra("selectedMenu") as Menu

            tv_nav_title.text = "EDIT MENU"
            btn_addMenu_add.text = "EDIT MENU"

            et_addMenu_name.setText(selectedMenu.name)
            et_addMenu_price.setText(selectedMenu.price)
            et_addMenu_description.setText(selectedMenu.description)
        }

        /* Hide keyboard onTouch anywhere */
        constraintLayout_addMenu.setOnTouchListener(object: View.OnTouchListener{
            override fun onTouch(view: View?, event: MotionEvent?): Boolean {
                when (event?.action){
                    MotionEvent.ACTION_DOWN ->
                        hideKeyboard()
                }

                return view?.onTouchEvent(event) ?: true
            }
        })

        btn_addMenu_back.setOnClickListener {
            finish()
        }

        iv_add_food_image.setOnClickListener {

            if (VERSION.SDK_INT >= VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permissions, PERMISSION_CODE)
                }else{
                    EasyImage.openGallery(this, 0)
                }
            }
        }


        /* Listener for add menu button */
        btn_addMenu_add.setOnClickListener {

            /* Show Progress Bar */
            pb_add_menu.visibility = View.VISIBLE

            val token = getSharedPreferences("user", Context.MODE_PRIVATE)?.getString("token", "")

            if(selectedMenu.id == ""){
                et_addMenu_name.text
                val name = et_addMenu_name.text.toString()
                val price = et_addMenu_price.text.toString()
                val description = et_addMenu_description.text.toString()

                val menu = Menu("", name, price, "0", description, "1")

                val foodController = FoodController()
                foodController.delegate = this
                foodController.createFood(token!!, menu, imageFile, object: SuccessInterface{
                    override fun onSuccess() {
                        super.onSuccess()

                        /* Hide Progress Bar */
                        pb_add_menu.visibility = View.INVISIBLE
                    }
                })

            }else{
                val name = et_addMenu_name.text.toString()
                val price = et_addMenu_price.text.toString()
                val description = et_addMenu_description.text.toString()
                val food = Menu(selectedMenu.id, name, price, selectedMenu.category, description, selectedMenu.isAvailable)

                val foodController = FoodController()

                foodController.updateFood(token!!, food, object: FoodCallbacks{
                    override fun onSuccess(menus: ArrayList<Menu>) {
                        finish()
                    }

                })
            }
        }
        setupEditText()
    }

    var abc = false

    private fun setupEditText(){
        et_addMenu_price.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable) {



            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                et_addMenu_price.removeTextChangedListener(this)

                var priceInteger = p0.toString().replace("Rp", "").replace(".", "").trim()

                if((priceAmount != p0.toString()) ){
                    if(priceInteger != ""){
                        priceInteger = "Rp ${Global.decimalFormatter(priceInteger.toInt())}"
                    }
                    priceAmount = priceInteger
                    et_addMenu_price.text.clear()
                    et_addMenu_price.setText(priceInteger)
                    et_addMenu_price.setSelection(priceInteger.length)
                    et_addMenu_price.addTextChangedListener(this)
                }
            }
        })
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){

            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery()
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : Callbacks{
            override fun onImagesPicked(
                p0: MutableList<File>,
                p1: EasyImage.ImageSource?,
                p2: Int
            ) {
                imageFile = p0[0]
                iv_add_food_image.setImageURI(data?.data)
            }

            override fun onImagePickerError(p0: Exception?, p1: EasyImage.ImageSource?, p2: Int) {
                println("ERROR")
            }

            override fun onCanceled(p0: EasyImage.ImageSource?, p1: Int) {
                println("CANCELED")
            }
        })
    }


    override fun onSuccess() {
        finish()
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View){
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
