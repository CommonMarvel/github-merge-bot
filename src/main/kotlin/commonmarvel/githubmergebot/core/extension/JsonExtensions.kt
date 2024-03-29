package commonmarvel.githubmergebot.core.extension

import commonmarvel.githubmergebot.core.global.Global
import kotlin.reflect.KClass

fun Any?.toJsonString(): String = Global.objectMapper.writeValueAsString(this)

fun <T : Any> String.jsonStringToObject(clazz: KClass<T>): T = Global.objectMapper.readValue(this, clazz.java)

fun String.jsonStringToList(): List<String> = Global.objectMapper.let {
  it.readValue(this, it.typeFactory.constructCollectionType(List::class.java, String::class.java))
}

fun <T> String.jsonStringToList(clazz: Class<T>): List<T> = Global.objectMapper.let {
  it.readValue(this, it.typeFactory.constructCollectionType(List::class.java, clazz))
}

fun String.jsonStringToMap(): Map<String, String?> = Global.objectMapper.let {
  it.readValue(this, it.typeFactory.constructMapType(Map::class.java, String::class.java, String::class.java))
}

fun <K, V> String.jsonStringToMap(kClass: Class<K>, vClass: Class<V>): Map<K, V?> = Global.objectMapper.let {
  it.readValue(this, it.typeFactory.constructMapType(Map::class.java, kClass, vClass))
}
