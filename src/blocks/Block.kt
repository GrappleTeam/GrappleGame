package blocks

open class Block(
        override var x: Int = 0,
        override var y: Int = 0,
        override var width: Int = 0,
        override var height: Int = 0,
        override val type: Type = Type.PLAIN
) : IBlock {
    override val imageString: String
        get() {
            return when (type) {
                Type.PLAIN -> "graphics/BrickBlock01.png"
                Type.DEATH -> "graphics/DeathBlock01.png"
                Type.PLATFORM -> "graphics/PlatformBlock01.png"
                Type.PORTAL -> "graphics/Portal01.png"
                Type.SLOW -> "graphics/SlowBlock01.png"
                else -> throw Exception()
            }
        }

    override val isPlatform: Boolean
        get() {
            return type == Type.PLATFORM
        }

    internal constructor(x: Int, y: Int, r: Block):
            this(x, y, r.width, r.height, r.type)

    override fun withinBounds(testX: Int, testY: Int, testWidth: Int, testHeight: Int): Boolean {
        return if (x < testX + testWidth && testX < x + width && y < testY + testHeight && testY < y + height) {
            true
        } else false
    }

    enum class Type {
        DEATH, PLAIN, PLATFORM, PORTAL, SLOW
    }
}
