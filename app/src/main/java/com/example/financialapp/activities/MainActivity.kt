package com.example.financialapp.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.financialapp.R
import com.example.financialapp.activities.models.BalanceResponse
import com.example.financialapp.activities.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var balanceTextView: TextView
    private lateinit var amountEditText: EditText
    private lateinit var addButton: Button
    private lateinit var subtractButton: Button

    private val apiService = ApiClient.apiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        getCurrentBalance()

        addButton.setOnClickListener(this)
        subtractButton.setOnClickListener(this)


    }

    private fun updateBalance(transactionalAmount : Int) {
        apiService.updateBalance(transactionalAmount)!!.enqueue(object : Callback<BalanceResponse?> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<BalanceResponse?>, response: Response<BalanceResponse?>) {
                if (response.isSuccessful) {
                    // If the update is successful, display the updated balance amount
                    val updatedBalance = response.body()?.balance ?: 0.0
                    balanceTextView.text = "Balance: $updatedBalance"
                } else {
                    // If there's an error, display a toast message
                    Toast.makeText(this@MainActivity, "Error updating balance", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BalanceResponse?>, t: Throwable) {
                // If there's a network error, display a toast message
                Toast.makeText(this@MainActivity, "Network error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getCurrentBalance() {
        apiService.getBalance()!!.enqueue(
            object : Callback<BalanceResponse?> {
                @SuppressLint("SetTextI18n")
                override fun onResponse(
                    call: Call<BalanceResponse?>,
                    response: Response<BalanceResponse?>
                ) {
                    if (response.isSuccessful) {
                        val balance = response.body()?.balance ?: 0.0
                        balanceTextView.text = "Balance: $balance"
                    } else {
                        Toast.makeText(this@MainActivity, "Error fetching balance", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BalanceResponse?>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error fetching balance", Toast.LENGTH_SHORT).show()
                }

            }
        )
    }

    private fun initViews() {
        balanceTextView = findViewById(R.id.balance)
        amountEditText = findViewById(R.id.et_amount)
        addButton = findViewById(R.id.btn_add)
        subtractButton = findViewById(R.id.btn_subtract)
    }

    override fun onClick(p0: View?) {
        if (p0!!.id == R.id.btn_add) {
            updateBalance(amountEditText.text.toString().toInt())
        }else if (p0.id == R.id.btn_subtract) {
            updateBalance(amountEditText.text.toString().toInt()*-1)
        }
    }

}