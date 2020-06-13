package com.easy.example.base


interface ICustomView<S : BaseCustomViewModel> {

    fun setData(data: S)


}