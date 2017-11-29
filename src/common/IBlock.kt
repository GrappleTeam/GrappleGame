package common

import common.Block

interface IBlock {
    val type: Block.Type
    var x: Int
    var y: Int
    var width: Int
    var height: Int
    val imageString: String
    val isPlatform: Boolean
    fun withinBounds(
            testX: Int,
            testY: Int,
            testWidth: Int,
            testHeight: Int
    ): Boolean

}
