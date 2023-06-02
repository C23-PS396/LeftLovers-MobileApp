package com.example.LeftLoversApp.local

import java.util.concurrent.Executor
import java.util.concurrent.Executors


class MainExecutor  {
    val diskIO: Executor = Executors.newSingleThreadExecutor()
}