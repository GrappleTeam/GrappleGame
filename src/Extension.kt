fun <T> List<T>.unzipSplit(predicate: (T) -> Boolean): Pair<List<T>, List<T>> {
    val pos = mutableListOf<T>()
    val neg = mutableListOf<T>()
    this.forEach {
        if (predicate(it)) {
            pos.add(it)
        } else {
            neg.add(it)
        }
    }
    return Pair(pos.toList(), neg.toList())
}

fun <T, R> List<T>.filterIsInstanceUnzip(klass: Class<R>): Pair<List<R>, List<T>> {
    val pos = mutableListOf<R>()
    val neg = mutableListOf<T>()
    this.forEach { element ->
        if (klass.isInstance(element)) {
            pos.add(element as R)
        } else {
            neg.add(element)
        }
    }
    return Pair(pos.toList(), neg.toList())
}