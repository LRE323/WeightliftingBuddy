package com.example.weightliftingbuddy.views.dialogfragments

import android.content.Context
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.data.models.Exercise
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class AddNewExerciseDialog(context: Context, private val callBack: AddNewExerciseCallBack) :
    AlertDialog(context) {
    private var dialogBuilder: MaterialAlertDialogBuilder? = null
    private var dialog: AlertDialog? = null
    private var positiveButton: Button? = null
    private var textInputExerciseName: TextInputEditText? = null
    private var textInputLayoutExerciseName: TextInputLayout? = null

    init {
        dialogBuilder = MaterialAlertDialogBuilder(context)
        dialogBuilder?.apply {
            prepareDialogContent(this)
        }
        dialog = dialogBuilder?.create()
    }

    override fun show() {
        dialog?.apply {
            show()
            bindViewVariables(this)
        }
        positiveButton?.apply {
            setOnClickListener { validateInput() }
        }
        textInputExerciseName?.addTextChangedListener {
            val errorState = textInputLayoutExerciseName?.error
            if (errorState != null) {
                textInputLayoutExerciseName?.error = null
            }
        }
    }

    private fun bindViewVariables(dialog: AlertDialog) {
        dialog.apply {
            positiveButton = getButton(android.app.AlertDialog.BUTTON_POSITIVE)
            textInputExerciseName = findViewById(R.id.exerciseInput)
            textInputLayoutExerciseName = findViewById(R.id.exerciseInputLayout)
        }
    }

    private fun validateInput() {
        val allInputValid = validateExerciseNameInput()
        if (allInputValid) {
            dialog?.apply {
                callBack.onValidateNewExerciseSuccess(
                    this,
                    Exercise(textInputExerciseName?.text.toString().trim())
                )
            }
        }
    }

    /**
     * Adds validation messages if needed. Returns boolean based on if input is valid.
     */
    private fun validateExerciseNameInput(): Boolean {
        val input = textInputExerciseName?.text
        val validInput = input?.isBlank() == false && input.isNotEmpty()
        return if (validInput) {
            true
        } else {
            textInputLayoutExerciseName?.error =
                context.getString(R.string.add_new_exercise_dialog_name_input_error)
            false
        }
    }

    private fun prepareDialogContent(dialogBuilder: MaterialAlertDialogBuilder) {
        dialogBuilder.apply {
            context.apply {
                setTitle(getString(R.string.title_add_new_exercise_dialog))
                setPositiveButton(getString(R.string.positive_button_add_new_exercise_dialog), null)
                setNegativeButton(getString(R.string.negative_button_add_new_exercise_dialog), null)
            }
            val view = layoutInflater.inflate(R.layout.dialog_add_new_exercise, null)
            setView(view)
        }
    }

    interface AddNewExerciseCallBack {
        fun onValidateNewExerciseSuccess(addNewExerciseDialog: AlertDialog, exerciseCreated: Exercise)
    }

}