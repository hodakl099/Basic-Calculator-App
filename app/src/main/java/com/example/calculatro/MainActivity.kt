package com.example.calculatro


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.calculatro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var lastNumeric = false
    var lastDot = false

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

    }
    /**
     * Appends the numeric Button.text to the TextView
     */
    fun onDigit(view: View) {
        // text of button is appended to textView
        binding.tvInput.append((view as Button).text)

        // Set the flag
        lastNumeric = true
    }

    /**
     * Append . to the TextView
     */
    fun onDecimalPoint(view: View) {

        // If the last appeded value is numeric then appen(".") or don't.
        if (lastNumeric && !lastDot) {
            binding.tvInput.append(".")
            lastNumeric = false // Update the flag
            lastDot = true // Update the flag
        }
    }

    /**
     * Append +,-,*,/ operators to the TextView as per the Button.Text
     */
    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded( binding.tvInput.text.toString())) {
            binding.tvInput.append((view as Button).text)
            lastNumeric = false // Update the flag
            lastDot = false    // Reset the DOT flag
        }
    }

    /**
     * Clear the TextView
     */
    fun onClear(view: View) {
        binding.tvInput.text = ""
        lastNumeric = false // Reset the flag
        lastDot = false // Reset the flag
    }

    /**
     * Calculate the output
     */
    fun onEqual(view: View) {
        // If the last input is a number only, solution can be found.
        if (lastNumeric) {
            // Read the textView value
            var value =  binding.tvInput.text.toString()
            var prefix = ""
            try {

                // Here if the value starts with '-' then we will separate it and perform the calculation with value.
                if (value.startsWith("-")) {
                    prefix = "-"
                    value = value.substring(1);
                }

                // If the inputValue contains the Division operator
                if (value.contains("/")) {
                    // Will split the inputValue using Division operator
                    val splitedValue = value.split("/")

                    var one = splitedValue[0] // Value One
                    val two = splitedValue[1] // Value Two

                    if (!prefix.isEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                        one = prefix + one
                    }

                    /*Here as the value one and two will be calculated based on the operator and
                    if the result contains the zero after decimal point will remove it.
                    And display the result to TextView*/
                    binding.tvInput.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                } else if (value.contains("*")) {
                    // If the inputValue contains the Multiplication operator
                    // Will split the inputValue using Multiplication operator
                    val splitedValue = value.split("*")

                    var one = splitedValue[0] // Value One
                    val two = splitedValue[1] // Value Two

                    if (!prefix.isEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                        one = prefix + one
                    }

                    /*Here as the value one and two will be calculated based on the operator and
                    if the result contains the zero after decimal point will remove it.
                    And display the result to TextView*/
                    binding.tvInput.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                } else if (value.contains("-")) {

                    // If the inputValue contains the Subtraction operator
                    // Will split the inputValue using Subtraction operator
                    val splitedValue = value.split("-")

                    var one = splitedValue[0] // Value One
                    val two = splitedValue[1] // Value Two

                    if (!prefix.isEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                        one = prefix + one
                    }

                    /*Here as the value one and two will be calculated based on the operator and
                    if the result contains the zero after decimal point will remove it.
                    And display the result to TextView*/
                    binding.tvInput.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                } else if (value.contains("+")) {
                    // If the inputValue contains the Addition operator
                    // Will split the inputValue using Addition operator
                    val splitedValue = value.split("+")

                    var one = splitedValue[0] // Value One
                    val two = splitedValue[1] // Value Two

                    if (!prefix.isEmpty()) { // If the prefix is not empty then we will append it with first value i.e one.
                        one = prefix + one
                    }

                    /*Here as the value one and two will be calculated based on the operator and
                    if the result contains the zero after decimal point will remove it.
                    And display the result to TextView*/
                    binding.tvInput.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * It is used to check whether any of the operator is used or not.
     */
    private fun isOperatorAdded(value: String): Boolean {

        /**
         * Here first we will check that if the value starts with "-" then will ignore it.
         * As it is the result value and perform further calculation.
         */

        return if (value.startsWith("-")) {
            false
        } else {
            (value.contains("/")
                    || value.contains("*")
                    || value.contains("-")
                    || value.contains("+"))
        }
    }

    /**
     * Remove the zero after decimal point
     */
    private fun removeZeroAfterDot(result: String): String {

        var value = result

        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }

        return value
    }
}