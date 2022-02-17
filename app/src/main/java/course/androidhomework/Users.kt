package course.androidhomework

import android.util.Log
import java.util.*

enum class Type { DEMO, FULL }

data class User(val id: Int, val name: String, val age: Int, val type: Type) {
    val startTime: Date by lazy {
        Calendar.getInstance().time
    }
}

fun workWithUsers() {
    val user = User(1, "Eugene", 23, Type.FULL).also {
        Log.i("startTime", "Time now: ${it.startTime}")
        Thread.sleep(1000)
        Log.i("startTime", "Time now: ${it.startTime}")
    }

    var listUsers = mutableListOf(user).apply {
        this.add(User(2, "George", 56, Type.DEMO))
        this.add(User(3, "Mary", 32, Type.DEMO))
        this.add(User(4, "Stacy", 12, Type.FULL))
    }

    getFullTypeUsers(listUsers)
    getFirstAndLastUserName(listUsers)

    doAction(Registration())
    doAction(Login(user))
    doAction(Logout())
}

fun getFullTypeUsers(listUsers: List<User>) {
    val filteredListUsers = listUsers.filter { it.type == Type.FULL }
    Log.i("getFullType", "Full type users: $filteredListUsers")
}

fun getFirstAndLastUserName(listUsers: List<User>) {
    listUsers.map { it.name }.also {
        Log.i("firstName", "First in list: ${it.first()}")
        Log.i("lastName", "Last in list: ${it.last()}")
    }
}

fun User.checkAge() {
    if (this.age > 18) {
        Log.i("age", "User ${this.name} is older 18")
    } else {
        throw Exception("Too small!")
    }
}

interface AuthCallback {
    fun authSuccess()
    fun authFailed()
}

object AuthCallbackImpl : AuthCallback {
    override fun authSuccess() {
        Log.i("auth", "Auth is successful")
    }

    override fun authFailed() {
        Log.i("auth", "Auth failed")
    }
}

fun updateCache() {
    Log.i("update", "Cache updated")
}

inline fun auth(user: User, updateCache: () -> Unit) {
    try {
        user.checkAge()
        AuthCallbackImpl.authSuccess()
        updateCache()
    } catch (e: Exception) {
        AuthCallbackImpl.authFailed()
    }
}

sealed class Action

class Registration : Action()

class Login(val user: User) : Action()

class Logout : Action()

fun doAction(action: Action) {
    if (action is Registration) {
        Log.i("doAction", "Registration")
    }
    if (action is Login) {
        Log.i("doAction", "Login")
        auth(action.user) { updateCache() }
    }
    if (action is Logout) {
        Log.i("doAction", "Logout")
    }
}