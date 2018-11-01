package common

interface IBlock : ITile {
    val type: Block.Type
    var width: Int
    var height: Int
    val isPlatform: Boolean
    fun withinBounds(
            testX: Int,
            testY: Int,
            testWidth: Int,
            testHeight: Int
    ): Boolean
}
