package domain

import presentation.model.OutputModel

sealed class Result

data object Success : Result()
class Error(val outputModel: OutputModel) : Result()